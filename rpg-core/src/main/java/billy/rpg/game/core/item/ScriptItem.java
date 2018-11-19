package billy.rpg.game.core.item;

import billy.rpg.game.core.GameData;
import billy.rpg.game.core.character.walkable.BoxWalkableCharacter;
import billy.rpg.game.core.character.walkable.HeroWalkableCharacter;
import billy.rpg.game.core.character.walkable.TransferWalkableCharacter;
import billy.rpg.game.core.character.walkable.npc.NPCWalkableCharacter;
import billy.rpg.game.core.command.*;
import billy.rpg.game.core.command.processor.CmdProcessor;
import billy.rpg.game.core.command.processor.support.DefaultCmdProcessor;
import billy.rpg.game.core.constants.GameConstant;
import billy.rpg.game.core.container.GameContainer;
import billy.rpg.game.core.screen.MapScreen;
import billy.rpg.game.core.screen.battle.BattleScreen;
import billy.rpg.game.core.script.LabelBean;
import billy.rpg.game.core.script.TriggerBean;
import billy.rpg.game.core.util.MP3Player;

import org.apache.commons.collections4.CollectionUtils;
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
    private HeroWalkableCharacter hero = new HeroWalkableCharacter();
    /** npc角色 */
    private List<NPCWalkableCharacter> npcs = new ArrayList<>();
    /** 传送门角色 */
    private List<TransferWalkableCharacter> transfers = new ArrayList<>();
    /** 宝箱角色 */
    private List<BoxWalkableCharacter> boxes = new ArrayList<>();
    /** 本地图中可遇到的妖怪ids */
    private List<Integer> predictedMonsterIds;
    /** bgm播放器 */
    private MP3Player bgmPlayer;
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

    public HeroWalkableCharacter getHero() {
        return hero;
    }

    public void setHero(HeroWalkableCharacter hero) {
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
            cmdProcessor = new DefaultCmdProcessor(label.getCmds());
        }
    }

    private boolean checkTriggerFlag = false;
    public void toCheckTrigger() {
        checkTriggerFlag = true;
    }

    public boolean checkTrigger(GameContainer gameContainer) {
//        if (!checkTriggerFlag) {
//            return false;
//        }
        boolean flag = checkTrigger0(gameContainer);
//        checkTriggerFlag = false;
        return flag;
    }


    public void checkMonster(GameContainer gameContainer) {
        gameContainer.getGameData().addSteps();
        // 为空时不发生战斗
        if (CollectionUtils.isEmpty(predictedMonsterIds)) {
            return;
        }
        if (!gameContainer.getGameData().randomFight()) {
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
        new BattleScreen(gameContainer, metMonsterIds);
    }

    /**
     * 检测地图上的对话，顺序为
     * <ol>
     *     <li>若地图上有npc，就执行npc事件</li>
     *     <li>若地图上有传送门，就执行传送门事件</li>
     *     <li>若地图上有宝箱，就执行宝箱事件</li>
     * </ol>
     * 返回true说明有事件，主角就不变动了
     */
    private boolean checkTrigger0(GameContainer gameContainer) {
        MapScreen mapScreen = gameContainer.getMapScreen();
        HeroWalkableCharacter mm = gameContainer.getActiveScriptItem().getHero();
        int heroNextPosXInFullMap = mm.getNextPosX(gameContainer) + mapScreen.getOffsetTileX();
        int heroNextPosYInFullMap = mm.getNextPosY(gameContainer) + mapScreen.getOffsetTileY();
        if (heroNextPosXInFullMap == -1 && heroNextPosYInFullMap == -1) { // a new map, not check talk
            return true;
        }
        TriggerBean triggerBean = null;
        ScriptItem activeScriptItem = gameContainer.getActiveScriptItem();
        // npc事件
        List<NPCWalkableCharacter> npcs = activeScriptItem.getNpcs();
        for (NPCWalkableCharacter npc : npcs) {
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
            return true;
        }
        // 传送门事件
        List<TransferWalkableCharacter> transfers = activeScriptItem.getTransfers();
        for (TransferWalkableCharacter transfer : transfers) {
            int npcPosX = transfer.getPosX();
            int npcPosY = transfer.getPosY();
            if (heroNextPosXInFullMap == npcPosX && heroNextPosYInFullMap == npcPosY) {
                int number = transfer.getNumber();
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
            return true;
        }

        List<BoxWalkableCharacter> boxes = activeScriptItem.getBoxes();
        for (BoxWalkableCharacter box : boxes) {
            int posX = box.getPosX();
            int posY = box.getPosY();
            if (heroNextPosXInFullMap == posX && heroNextPosYInFullMap == posY) {
                int number = box.getNumber();
                TriggerBean boxTrigger = getTriggerByNum(number);
                if (boxTrigger != null) {
                    triggerBean = boxTrigger;
                }
            }
        }
        if (triggerBean != null) {
            executeTrigger(triggerBean);
            return true;
        }
        return false;
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
        cmdProcessor = new DefaultCmdProcessor(primarySection);
        flagExecutePrimarySection = true;
    }

    public List<NPCWalkableCharacter> getNpcs() {
        return npcs;
    }

    public List<TransferWalkableCharacter> getTransfers() {
        return transfers;
    }

    public List<BoxWalkableCharacter> getBoxes() {
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
    public void delVariable(String var) {
        variables.remove(var);
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
