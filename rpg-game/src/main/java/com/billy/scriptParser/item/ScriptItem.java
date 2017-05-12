package com.billy.scriptParser.item;

import com.billy.map.MainMap;
import com.billy.scriptParser.bean.DataLoaderBean;
import com.billy.scriptParser.bean.script.LabelBean;
import com.billy.scriptParser.bean.script.TalkBean;
import com.billy.scriptParser.bean.script.TriggerBean;
import com.billy.scriptParser.cmd.*;
import com.billy.scriptParser.cmd.executor.CmdExecutor;
import com.billy.scriptParser.container.GameContainer;
import com.billy.scriptParser.virtualtable.GlobalVirtualTables;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// TODO move it to MapDataLoaderBean ?
public class ScriptItem extends DataLoaderBean implements IItem {

    private static final Logger LOG = Logger.getLogger(ScriptItem.class);
    public List<CmdBase> cmdList;
    private String fileId;
    private int width;
    private int height;
    private List<CmdBase> primarySection; // execute these when re-enter this map
    private boolean flagExecutePrimarySection;
    private List<LabelBean> labelList;
    private List<TriggerBean> triggers;
    private List<TalkBean> talks;
    private MainMap mm = new MainMap();

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

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

    public MainMap getMm() {
        return mm;
    }

    public void setMm(MainMap mm) {
        this.mm = mm;
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

    public void executeTrigger(String triggername) {
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

    public void executeTalk(String triggername) {
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

    public void checkTrigger() {
        checkTrigger0();
        checkTalk0();
    }

    private void checkTrigger0() {
        MainMap mm = GameContainer.getInstance().getActiveFileItem().getMm();
        String posX = mm.getPosX() + "";
        String posY = mm.getPosY() + "";

        TriggerBean t = getTriggerByPos(posX, posY);
        if (t != null) {
            executeTrigger(t.getLabelTitle());
        }
    }

    private void checkTalk0() {
        MainMap mm = GameContainer.getInstance().getActiveFileItem().getMm();
        int posX = mm.getNextPosX();
        int posY = mm.getNextPosY();
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

        TalkBean tb = getTalkByNum(eventNum);
        if (tb != null) {
            executeTalk(tb.getLabelTitle());
        }

        return;
    }

    public void init(List<CmdBase> cmdList) {
        initPrimarySection(cmdList); // 第一行到第一个return之间的命令全部属于primarySection
        initLabelList(cmdList); // 初始化标签集合
        initTriggers(cmdList); // 初始化触发器
        initTalk(cmdList);
    }

    public void initWidthAndHeight(int height, int width) {
        mm.setHeight(height);
        mm.setWidth(width);
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

}
