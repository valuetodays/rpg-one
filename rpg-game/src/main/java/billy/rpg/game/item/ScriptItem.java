package billy.rpg.game.item;

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
import billy.rpg.game.virtualtable.GlobalVirtualTables;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ScriptItem {
    private static final Logger LOG = Logger.getLogger(ScriptItem.class);
    public List<CmdBase> cmdList;
    private String fileId;
    private List<CmdBase> primarySection; // execute these when re-enter this map
    private boolean flagExecutePrimarySection;
    private List<LabelBean> labelList;
    private List<TriggerBean> triggers;
    private HeroCharacter hero = new HeroCharacter();
    private List<NPCCharacter> npcs = new ArrayList<>();
    private List<TransferCharacter> transfers = new ArrayList<>();
    private List<BoxCharacter> boxes = new ArrayList<>();
    private static final int STEP_MEET_MONSTER = 15;
    private int steps; // 当前地图下的移动步数，当达到STEP_MEET_MONSTER时会遇到怪物进行战斗
    private List<Integer> predictedMonsterIds; // 本地图中可遇到的妖怪ids


    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public List<CmdBase> getPrimarySection() {
        return primarySection;
    }

    public void setPrimarySection(List<CmdBase> primarySection) {
        this.primarySection = primarySection;
    }

    public List<LabelBean> getLabelList() {
        return labelList;
    }

    public void setLabelList(List<LabelBean> labelList) {
        this.labelList = labelList;
    }

    public List<TriggerBean> getTriggers() {
        return triggers;
    }

    public void setTriggers(List<TriggerBean> triggers) {
        this.triggers = triggers;
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
        for (int i = 0; i < triggers.size(); i++) {
            TriggerBean tmp = triggers.get(i);
            if (tmp.getNum() == flagNum) {
                return tmp;
            }
        }

        return null;
    }

    ////////////////////////////////////////////////////
    public void initLabelList(List<CmdBase> cmdList) {
        this.cmdList = cmdList;
        List<CmdBase> labelCaa = null;
        List<LabelBean> labelList = new ArrayList<>();
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
                labelList.add(label);
                GlobalVirtualTables.addLabel(label);
            }
        }

        this.labelList = labelList;
    }

    public void initPrimarySection(List<CmdBase> cmdList) {
        List<CmdBase> primarySection = new ArrayList<>();
        int i = 0;
        CmdBase caa = cmdList.get(i);

        while (!(caa instanceof ReturnCmd)) {
            if (caa instanceof AttrCmd) {
                AttrCmd ac = (AttrCmd) caa;
                LOG.debug(ac);
                this.fileId = ac.getM() + "-" + ac.getN();

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
        List<TriggerBean> triggerList = new ArrayList<>();

        for (int i = 0; i < cmdList.size(); i++) {
            CmdBase caa = cmdList.get(i);
            if (caa instanceof TriggerCmd) {
                TriggerCmd tc = (TriggerCmd) caa;
                TriggerBean t = new TriggerBean();
                t.setNum(tc.getNum());
                t.setLabelTitle(tc.getTriggerName());
                triggerList.add(t);
            }
        }

        this.triggers = Collections.unmodifiableList(triggerList);
    }


    public void executeTrigger(TriggerBean trigger) {
        if (trigger == null) {
            return ;
        }

        String triggername = trigger.getLabelTitle();
        LabelBean label = null;
        List<TriggerBean> triggerList = this.triggers;
        for (TriggerBean t : triggerList) {
            if (t.getLabelTitle().equals(triggername)) {
                label = getLabelByTitle(triggername);
                if (label == null) {
                    LOG.debug("label named \""+triggername+"\" not found, maybe it start with a uppercase letter.");
                }
                break;
            }
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
        steps++; // checkTrigger()方法的执行之前会的上下左右的移动
        checkMonster();
        checkTrigger0();
        checkTriggerFlag = false;
    }


    private void checkMonster() {
        // 为空时不发生战斗
        if (CollectionUtils.isEmpty(predictedMonsterIds)) {
            return;
        }
        if (steps < STEP_MEET_MONSTER) {
            return;
        }
        int monsterNumbers = GameConstant.random.nextInt(3) + 1;
        monsterNumbers = 3;
        int[] metMonsterIds = new int[monsterNumbers];
        for (int i = 0; i < monsterNumbers; i++) {
            int n = GameConstant.random.nextInt(predictedMonsterIds.size());
            metMonsterIds[i] = predictedMonsterIds.get(n);
        }

        steps = 0;
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
        HeroCharacter mm = GameContainer.getInstance().getActiveFileItem().getHero();
        int heroNextPosXInFullMap = mm.getNextPosX() + mapScreen.getOffsetTileX();
        int heroNextPosYInFullMap = mm.getNextPosY() + mapScreen.getOffsetTileY();
        if (heroNextPosXInFullMap == -1 && heroNextPosYInFullMap == -1) { // a new map, not check talk
            return;
        }
        TriggerBean triggerBean = null;
        List<NPCCharacter> npcs = GameFrame.getInstance().getGameContainer().getActiveFileItem().getNpcs();
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
        initPrimarySection(cmdList); // 第一行到第一个return之间的命令全部属于primarySection
        initLabelList(cmdList); // 初始化标签集合
        initTriggers(cmdList); // 初始化触发器
    }


    public LabelBean getLabelByTitle(String labelTitle) {
        for (int i = 0; i < labelList.size(); i++) {
            LabelBean tmpLabel = labelList.get(i);
            if (tmpLabel.getTitle().equals(labelTitle + ":")) {
                return tmpLabel;
            }
        }
        return null;
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
}
