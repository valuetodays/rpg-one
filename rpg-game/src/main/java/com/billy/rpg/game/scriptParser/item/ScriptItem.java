package com.billy.rpg.game.scriptParser.item;

import com.billy.rpg.game.character.HeroCharacter;
import com.billy.rpg.game.character.NPCCharacter;
import com.billy.rpg.game.character.TransferCharacter;
import com.billy.rpg.game.scriptParser.bean.DataLoaderBean;
import com.billy.rpg.game.scriptParser.bean.script.LabelBean;
import com.billy.rpg.game.scriptParser.bean.script.TalkBean;
import com.billy.rpg.game.scriptParser.bean.script.TriggerBean;
import com.billy.rpg.game.scriptParser.cmd.*;
import com.billy.rpg.game.scriptParser.cmd.executor.CmdExecutor;
import com.billy.rpg.game.scriptParser.container.GameContainer;
import com.billy.rpg.game.scriptParser.virtualtable.GlobalVirtualTables;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// TODO move it to MapDataLoaderBean ?
public class ScriptItem extends DataLoaderBean implements IItem {
    private static final Logger LOG = Logger.getLogger(ScriptItem.class);
    public List<CmdBase> cmdList;
    private String fileId;
    private List<CmdBase> primarySection; // execute these when re-enter this map
    private boolean flagExecutePrimarySection;
    private List<LabelBean> labelList;
    private List<TriggerBean> triggers;
    private List<TalkBean> talks;
    private HeroCharacter hero = new HeroCharacter();
    private List<NPCCharacter> npcs = new ArrayList<>();
    private List<TransferCharacter> transfers = new ArrayList<>();


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
    ////////////////////////////////////////////////////


    public TriggerBean getTriggerByPos(String posX, String posY) {
        for (int i = 0; i < triggers.size(); i++) {
            TriggerBean tmp = triggers.get(i);
            if (tmp.getPosition().equals(posX + "-" + posY)) {
                return tmp;
            }
        }

        return null;
    }

    private TalkBean getTalkByNum(int flagNum) {
        for (int i = 0; i < talks.size(); i++) {
            TalkBean tmp = talks.get(i);
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

    private void initTalk(List<CmdBase> cmdList2) {
        List<TalkBean> talkList = new ArrayList<>();

        for (int i = 0; i < cmdList.size(); i++) {
            CmdBase caa = cmdList.get(i);
            if (caa instanceof TalkCmd) {
                TalkCmd tc = (TalkCmd) caa;
                TalkBean t = new TalkBean();
                t.setNum(tc.getNum());
                t.setLabelTitle(tc.getTriggerName());
                talkList.add(t);
            }
        }

        this.talks = Collections.unmodifiableList(talkList);
    }

    public void initTriggers(List<CmdBase> cmdList) {
        List<TriggerBean> triggerList = new ArrayList<>();

        for (int i = 0; i < cmdList.size(); i++) {
            CmdBase caa = cmdList.get(i);
            if (caa instanceof TriggerCmd) {
                TriggerCmd tc = (TriggerCmd) caa;
                TriggerBean t = new TriggerBean();
                t.setPosition(tc.getX() + "-" + tc.getY());
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
                break;
            }
        }

        if (label != null) {
            CmdExecutor.executeCmds(label.getCmds());
        }
    }

    public void executeTalk(TalkBean talk) {
        if (talk == null) {
            return ;
        }

        String triggername = talk.getLabelTitle();

        LabelBean label = null;
        for (TalkBean t : talks) {
            if (t.getLabelTitle().equals(triggername)) {
                label = getLabelByTitle(triggername);
                break;
            }
        }

        if (label != null) {
            CmdExecutor.executeCmds(label.getCmds());
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
        checkTrigger0();
        checkTalk0();
        checkMonster();
        checkTriggerFlag = false;
    }

    private void checkMonster() {

    }

    private void checkTrigger0() {
        HeroCharacter mm = GameContainer.getInstance().getActiveFileItem().getHero();
        String posX = mm.getNextPosX() + "";
        String posY = mm.getNextPosY() + "";

        executeTrigger(getTriggerByPos(posX, posY));
    }

    private void checkTalk0() {
        HeroCharacter mm = GameContainer.getInstance().getActiveFileItem().getHero();
        int posX = mm.getNextPosX();
        int posY = mm.getNextPosY();
        if (posX == -1 && posY == -1) { // a new map, not check talk
            return ;
        }
        int[][] event = GameContainer.getInstance().getActiveMap().getEvent();
        int eventNum = 0;
        try {
            eventNum = event[posX][posY];
            if (eventNum == -1) {
                return;
            }
        } catch(ArrayIndexOutOfBoundsException e) {
            return ;
        }

            executeTalk(getTalkByNum(eventNum));

        return;
    }

    public void init(List<CmdBase> cmdList) {
        initPrimarySection(cmdList); // 第一行到第一个return之间的命令全部属于primarySection
        initLabelList(cmdList); // 初始化标签集合
        initTriggers(cmdList); // 初始化触发器
        initTalk(cmdList);
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
        CmdExecutor.executeCmds(primarySection);
        flagExecutePrimarySection = true;
    }

    public List<NPCCharacter> getNpcs() {
        return npcs;
    }

    public List<TransferCharacter> getTransfers() {
        return transfers;
    }

    public void initNpcs() {
        this.npcs = new ArrayList<>();
    }

    public void initTransfers() {
        this.transfers = new ArrayList<>();
    }
}
