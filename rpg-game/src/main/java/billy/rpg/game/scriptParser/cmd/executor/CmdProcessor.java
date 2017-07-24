package billy.rpg.game.scriptParser.cmd.executor;

import billy.rpg.game.GameFrame;
import billy.rpg.game.character.NPCCharacter;
import billy.rpg.game.character.npc.CommonNPCCharacter;
import billy.rpg.game.character.npc.NoWalkNPCCharacter;
import billy.rpg.game.container.GameContainer;
import billy.rpg.game.screen.*;
import billy.rpg.game.scriptParser.bean.script.LabelBean;
import billy.rpg.game.scriptParser.cmd.*;
import billy.rpg.game.scriptParser.virtualtable.GlobalVirtualTables;
import org.apache.log4j.Logger;

import java.awt.*;
import java.util.List;


/**
 * @author liulei@bshf360.com
 * @since 2017-07-21 14:03
 */
public class CmdProcessor {
    private static Logger LOG = Logger.getLogger(CmdProcessor.class);
    private boolean pausing; // 当对话开始时，该值为true，命令暂时不再往下执行
    private List<CmdBase> cmdList;
    private int cmdSize;
    private int cmdIndex;
    // 遇到了return命令就说明执行完毕了，典型是的if语句的子语句，它的每个分支都有return，任何一个分支走完都算结束了
    private boolean metReturnCmd;

    public void update() {
        if (!pausing) {
            if (endExecute()) {
                return;
            }
            executeCmd(cmdList.get(cmdIndex));
            cmdIndex++;
        }
    }

    private boolean endExecute() {
        if (cmdIndex >= cmdSize) {
            return true;
        }

        return metReturnCmd;
    }

    private void executeCmds(List<CmdBase> cmdList) {
        for (int i = 0; i < cmdList.size(); i++) {
            CmdBase caa = cmdList.get(i);
            int executeCode = executeCmd(caa);
            if (-1 == executeCode) {
                metReturnCmd = true;
            } else if (-2 == executeCode) {
                continue;
            }
        }
    }


    /**
     *
     * 执行脚本命令
     * 有的脚本应该是阻塞式的，它没执行完毕不应该往下执行，典型的就是对话
     *
     * @param cmd 命令对象
     * @return  -1: rtn occurs;
     *          -2: does not fulfill the if condition
     */
    private int executeCmd(CmdBase cmd) {
        // 使用instanceof 分开处理各种情况
        if (cmd instanceof ReturnCmd) {
            return -1;
        } else if (cmd instanceof SetCmd) {
            GlobalVirtualTables.putGlobalVariable(((SetCmd) cmd).getKey()); // only one argument
        } else if (cmd instanceof IfCmd) {
            IfCmd ic = (IfCmd) cmd;
            if (GlobalVirtualTables.containsVariable(ic.getCondition())) { // global variable exists
                LabelBean fun = GlobalVirtualTables.getLabel(ic.getTriggerName());
                executeCmds(fun.getCmds());
            } else {    // global variable does not exist
                return -2;
            }
        } else if (cmd instanceof ShowTextCmd) {

            ShowTextCmd stc = ((ShowTextCmd) cmd);
            int headNumber = stc.getHeadNumber();
            final String text = stc.getText();
            final String text0 = text.substring(1, text.length() - 1);
            LOG.debug(text0);
            Image headImage = GameContainer.getInstance().getHeadImageItemOf(headNumber);
            DialogScreen ms = new DialogScreen(this, headImage, text0);
            GameFrame.getInstance().pushScreen(ms);
            startPause();
        } else if (cmd instanceof LoadMapCmd) {
            LoadMapCmd lmc = (LoadMapCmd) cmd;
            LOG.debug("go to map" + lmc.getM() + "-" + lmc.getN() + " in " + lmc.getX() + "," + lmc.getY());
            GameContainer.getInstance().startChapter(lmc.getM(), lmc.getN(), lmc.getX() + "-" + lmc.getY());
        } else if (cmd instanceof ScenenameCmd) {
            final String scenename = ((ScenenameCmd) cmd).getSname();
            final String scenename0 = scenename.substring(1, scenename.length() - 1);
            BaseScreen bs = new ScenenameScreen(scenename0);
            GameFrame.getInstance().pushScreen(bs);
        } else if (cmd instanceof AttrCmd) {
            LOG.debug(" >> basic attribute of this map ");
            AttrCmd ac = (AttrCmd) cmd;
        } else if (cmd instanceof TalkCmd)  {
            System.out.println("talk to u");
        } else if (cmd instanceof MessageBoxCmd) {
            final MessageBoxCmd mb = (MessageBoxCmd) cmd;
            LOG.debug(mb);
            final String msg = mb.getMsg();
            final String msg0 = msg.substring(1, msg.length() - 1);
            final BaseScreen bs = new MessageBoxScreen(msg0);
            GameFrame.getInstance().pushScreen(bs);
        } else if (cmd instanceof AnimationCmd) {
            final AnimationCmd ac = (AnimationCmd) cmd;
            int no = ac.getNumber();
            int x = ac.getX();
            int y = ac.getY();
            BaseScreen as = new AnimationScreen(no, x, y, new MapScreen());
            GameFrame.getInstance().pushScreen(as);
        } else if (cmd instanceof CreateNPCCmd) {
            final CreateNPCCmd cnc = (CreateNPCCmd) cmd;
            int x = cnc.getX();
            int y = cnc.getY();
            int npcNum = cnc.getNpcNum();
            int type = cnc.getType();
            NPCCharacter npc = null;
            if (type == 1) {
                npc = new NoWalkNPCCharacter();
            } else {
                npc = new CommonNPCCharacter();
            }
            npc.setHeight(GameFrame.getInstance().getGameContainer().getActiveMap().getHeight());
            npc.setWidth(GameFrame.getInstance().getGameContainer().getActiveMap().getWidth());
            npc.initPos(x, y);
            npc.setTileNum(npcNum);
            GameFrame.getInstance().getGameContainer().getActiveFileItem().getNpcs().add(npc);
        } else {
            LOG.warn("no command comfit " + cmd);
        }

        return 0;
    }


    public CmdProcessor(List<CmdBase> cmdList) {
        this.cmdList = cmdList;
        cmdSize = cmdList.size();
    }

    /**
     * 挂起脚本执行 - 开始
     */
    public void startPause() {
        pausing = true;
    }

    /**
     * 挂起脚本执行 - 结束
     */
    public void endPause() {
        pausing = false;
    }



}
