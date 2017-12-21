package billy.rpg.game.resource.item;

import billy.rpg.game.GameData;
import billy.rpg.game.GameFrame;
import billy.rpg.game.character.BoxCharacter;
import billy.rpg.game.character.HeroCharacter;
import billy.rpg.game.character.NPCCharacter;
import billy.rpg.game.character.TransferCharacter;
import billy.rpg.game.cmd.*;
import billy.rpg.game.cmd.executor.CmdProcessor;
import billy.rpg.game.constants.GameConstant;
import billy.rpg.game.container.GameContainer;
import billy.rpg.game.screen.MapScreen;
import billy.rpg.game.screen.battle.BattleScreen;
import billy.rpg.game.script.LabelBean;
import billy.rpg.game.script.TriggerBean;
import com.rupeng.game.AsyncAudioPlayer;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;

import java.util.*;

/**
 * 对应一个script文件
 */
public class ScriptItem {
    private static final Logger LOG = Logger.getLogger(ScriptItem.class);

    /** 脚本id */
    private String scriptId;
    /** 所有命令列表 */
    private List<CmdBase> cmdList;
    /**
     * 初入地图命令[第一行到第一个return之间的命令全部属于此]
     * execute these when re-enter this map
     */
    private List<CmdBase> primarySection;
    /** 是否已执行了初入地图命令 */
    private boolean flagExecutePrimarySection;
    /** 脚本中的标签列表 */
    private Map<String, LabelBean> labelMap;
    /** 脚本中的触发器列表 */
    private Map<Integer, TriggerBean> triggerMap;
    /** 玩家角色 */
    private HeroCharacter hero = new HeroCharacter();
    /** npc角色 */
    private List<NPCCharacter> npcs = new ArrayList<>();
    /** 传送门角色 */
    private List<TransferCharacter> transfers = new ArrayList<>();
    /** 宝箱角色 */
    private List<BoxCharacter> boxes = new ArrayList<>();
    /** 本地图中可遇到的妖怪ids */
    private List<Integer> predictedMonsterIds;
    /** bgm播放器 */
    private AsyncAudioPlayer bgmPlayer;
    /** 本脚本中的所有变量 */
    private Set<String> variables = new HashSet<>();

    public String getScriptId() {
        return scriptId;
    }

    public void setScriptId(String scriptId) {
        this.scriptId = scriptId;
    }

    public List<CmdBase> getPrimarySection() {
        return primarySection;
    }

    public void setPrimarySection(List<CmdBase> primarySection) {
        this.primarySection = primarySection;
    }

    public HeroCharacter getHero() {
        return hero;
    }

    public void setHero(HeroCharacter hero) {
        this.hero = hero;
    }

    public void setPredictedMonsterIds(List<Integer> predictedMonsterIds) {
        this.predictedMonsterIds = predictedMonsterIds;
    }
////////////////////////////////////////////////////

    private TriggerBean getTriggerByNum(int flagNum) {
        return triggerMap.get(flagNum);
    }

    ////////////////////////////////////////////////////
    public void initLabelList(List<CmdBase> cmdList) {
        List<CmdBase> labelCaa = null;
        Map<String, LabelBean> labelMap = new HashMap<>();
        LabelBean label = null;

        for (int i = 0; i < cmdList.size(); i++) {
            CmdBase caa = cmdList.get(i);

            if (caa instanceof LabelCmd) {
                LabelCmd lc = (LabelCmd) caa;
                label = new LabelBean();
                label.setTitle(lc.getName());
                labelCaa = new ArrayList<>();
                i++;
                caa = cmdList.get(i);
                while (!(caa instanceof ReturnCmd)) {
                    labelCaa.add(caa);
                    i++;
                    caa = cmdList.get(i);
                }
                caa = cmdList.get(i);
                labelCaa.add(caa);
                label.setCmds(labelCaa);
                labelMap.put(label.getTitle(), label);
            }
        }

        this.labelMap = Collections.unmodifiableMap(labelMap);
    }

    public void initPrimarySection(List<CmdBase> cmdList) {
        List<CmdBase> primarySection = new ArrayList<>();
        int i = 0;
        CmdBase caa = cmdList.get(i);

        while (!(caa instanceof ReturnCmd)) {
            if (caa instanceof AttrCmd) {
                AttrCmd ac = (AttrCmd) caa;
                LOG.debug(ac);
                this.scriptId = ac.getM() + "-" + ac.getN();

            }
            primarySection.add(caa);
            i++;
            caa = cmdList.get(i);
        }

        caa = cmdList.get(i);
        primarySection.add(caa);
        this.primarySection = Collections.unmodifiableList(primarySection);
    }

    private void initTriggers(List<CmdBase> cmdList) {
        Map<Integer, TriggerBean> triggerMap0 = new HashMap<>();

        for (int i = 0; i < cmdList.size(); i++) {
            CmdBase caa = cmdList.get(i);
            if (caa instanceof TriggerCmd) {
                TriggerCmd tc = (TriggerCmd) caa;
                TriggerBean t = new TriggerBean();
                t.setNum(tc.getNum());
                t.setLabelTitle(tc.getTriggerName());
                triggerMap0.put(t.getNum(), t);
            }
        }

        this.triggerMap = Collections.unmodifiableMap(triggerMap0);
    }


    public void executeTrigger(TriggerBean trigger) {
        if (trigger == null) {
            return ;
        }

        LabelBean label = null;

        TriggerBean triggerBean = triggerMap.get(trigger.getNum());
        if (triggerBean != null) {
            label = getLabelByTitle(trigger.getLabelTitle());
        }

        if (label != null) {
            cmdProcessor = new CmdProcessor(label.getCmds());
        }
    }

    private boolean checkTriggerFlag = false;
    public void toCheckTrigger() {
        checkTriggerFlag = true;
    }

    public void checkTrigger() {
        if (!checkTriggerFlag) {
            return ;
        }
        GameData.addSteps();
        checkMonster();
        checkTrigger0();
        checkTriggerFlag = false;
    }


    private void checkMonster() {
        // 为空时不发生战斗
        if (CollectionUtils.isEmpty(predictedMonsterIds)) {
            return;
        }
        if (!GameData.randomFight()) {
            return;
        }

        int monsterNumbers = GameConstant.random.nextInt(3) + 1;
        monsterNumbers = 3;
        int[] metMonsterIds = new int[monsterNumbers];
        for (int i = 0; i < monsterNumbers; i++) {
            int n = GameConstant.random.nextInt(predictedMonsterIds.size());
            metMonsterIds[i] = predictedMonsterIds.get(n);
        }

        GameData.clearSteps();
        new BattleScreen(metMonsterIds);
    }

    /**
     * 检测地图上的对话，顺序为
     * <ol>
     *     <li>若地图上有npc，就执行npc事件</li>
     *     <li>若地图上有事件，就执行事件</li>
     * </ol>
     */
    private void checkTrigger0() {
        MapScreen mapScreen = GameFrame.getInstance().getGameContainer().getMapScreen();
        HeroCharacter mm = GameContainer.getInstance().getActiveScriptItem().getHero();
        int heroNextPosXInFullMap = mm.getNextPosX() + mapScreen.getOffsetTileX();
        int heroNextPosYInFullMap = mm.getNextPosY() + mapScreen.getOffsetTileY();
        if (heroNextPosXInFullMap == -1 && heroNextPosYInFullMap == -1) { // a new map, not check talk
            return;
        }
        TriggerBean triggerBean = null;
        List<NPCCharacter> npcs = GameFrame.getInstance().getGameContainer().getActiveScriptItem().getNpcs();
        for (NPCCharacter npc : npcs) {
            int npcPosX = npc.getPosX();
            int npcPosY = npc.getPosY();
            if (heroNextPosXInFullMap == npcPosX && heroNextPosYInFullMap == npcPosY) {
                int number = npc.getNumber();
                if (0 != number) {
                    TriggerBean talkByNum = getTriggerByNum(number);
                    if (talkByNum != null) {
                        triggerBean = talkByNum;
                        break;
                    }
                }
            }
        }
        if (triggerBean != null) {
            executeTrigger(triggerBean);
            return;
        }

        int[][] event = GameContainer.getInstance().getActiveMap().getEvent();
        int eventNum = event[heroNextPosXInFullMap][heroNextPosYInFullMap];
        if (eventNum == -1) {
            return;
        }
        triggerBean = getTriggerByNum(eventNum);
        if (triggerBean != null) {
            executeTrigger(triggerBean);
            return;
        }
    }


    public void init(List<CmdBase> cmdList) {
        this.cmdList = cmdList;
        initPrimarySection(cmdList); // 第一行到第一个return之间的命令全部属于primarySection
        initLabelList(cmdList); // 初始化标签集合
        initTriggers(cmdList); // 初始化触发器
    }

    public LabelBean getLabelByTitle(String labelTitle) {
        // magic string?
        return labelMap.get(labelTitle + ":");
    }

    public void reenter() {
        this.flagExecutePrimarySection = false;
    }

    public void executePrimarySection() {
        if (flagExecutePrimarySection) {
            return;
        }
        List<CmdBase> primarySection = getPrimarySection();
        cmdProcessor = new CmdProcessor(primarySection);
        flagExecutePrimarySection = true;
    }

    public List<NPCCharacter> getNpcs() {
        return npcs;
    }

    public List<TransferCharacter> getTransfers() {
        return transfers;
    }

    public List<BoxCharacter> getBoxes() {
        return boxes;
    }

    public void initNpcs() {
        this.npcs = new ArrayList<>();
    }

    public void initTransfers() {
        this.transfers = new ArrayList<>();
    }
    public void initBoxes() {
        this.boxes = new ArrayList<>();
    }

    private CmdProcessor cmdProcessor;

    public CmdProcessor getCmdProcessor() {
        return cmdProcessor;
    }

    public void setCmdProcessor(CmdProcessor cmdProcessor) {
        this.cmdProcessor = cmdProcessor;
    }
    public void addVariable(String var) {
        variables.add(var);
    }
    public boolean getVariable(String var) {
        return variables.contains(var);
    }

    /**
     * 打印局部变量
     */
    public void printVariable() {
        System.out.println("=== localVariables `"+scriptId+"` starts ===");
        for (String s : variables) {
            System.out.println("    " + s);
        }
        System.out.println("=== localVariables `"+scriptId+"` ends ===");
    }
}
