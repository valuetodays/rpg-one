package billy.rpg.game.cmd.executor;

import billy.rpg.game.GameFrame;
import billy.rpg.game.character.NPCCharacter;
import billy.rpg.game.character.npc.CommonNPCCharacter;
import billy.rpg.game.character.npc.NoWalkNPCCharacter;
import billy.rpg.game.cmd.*;
import billy.rpg.game.container.GameContainer;
import billy.rpg.game.screen.*;
import billy.rpg.game.script.LabelBean;
import billy.rpg.game.virtualtable.GlobalVirtualTables;
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
    private CmdProcessor innerCmdProcessor;

    public void update() {
        if (innerCmdProcessor != null) {
            innerCmdProcessor.update();
        } else {
            if (!pausing) {
                if (endExecute()) {
                    return;
                }
                executeCmd(cmdList.get(cmdIndex));
                cmdIndex++;
            }
        }
    }

    private boolean endExecute() {
        if (cmdIndex >= cmdSize) {
            return true;
        }

        return metReturnCmd;
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
//                executeCmds(fun.getCmds());
                innerCmdProcessor = new CmdProcessor(fun.getCmds());
            } else {    // global variable does not exist
                return -2;
            }
        } else if (cmd instanceof ShowTextCmd) {
            ShowTextCmd stc = ((ShowTextCmd) cmd);
            int headNumber = stc.getHeadNumber();
            final String text = stc.getText();
            Image headImage = GameContainer.getInstance().getHeadImageItemOf(headNumber);
            DialogScreen ms = new DialogScreen(this, headImage, text);
            GameFrame.getInstance().pushScreen(ms);
            startPause();
        } else if (cmd instanceof LoadMapCmd) {
            LoadMapCmd lmc = (LoadMapCmd) cmd;
            LOG.debug("go to map" + lmc.getM() + "-" + lmc.getN() + " in " + lmc.getX() + "," + lmc.getY());
            GameContainer.getInstance().startChapter(lmc.getM(), lmc.getN(), lmc.getX() + "-" + lmc.getY());
        } else if (cmd instanceof ScenenameCmd) {
            String sname = ((ScenenameCmd) cmd).getSname();
            BaseScreen bs = new ScenenameScreen(sname);
            GameFrame.getInstance().pushScreen(bs);
        } else if (cmd instanceof AttrCmd) {
            LOG.debug(" >> basic attribute of this map ");
            AttrCmd ac = (AttrCmd) cmd;
        } else if (cmd instanceof TalkCmd) {
            System.out.println("talk to u");
        } else if (cmd instanceof MessageBoxCmd) {
            final MessageBoxCmd mb = (MessageBoxCmd) cmd;
            LOG.debug(mb);
            final String msg = mb.getMsg();
            final BaseScreen bs = new MessageBoxScreen(msg);
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
            npc.setNumber(GameFrame.getInstance().getGameContainer().getActiveFileItem().getNpcs().size() + 1001);
            GameFrame.getInstance().getGameContainer().getActiveFileItem().getNpcs().add(npc);
        } else if (cmd instanceof ChoiceCmd) {
            final ChoiceCmd cc = (ChoiceCmd) cmd;
            String title = cc.getTitle();
            List<String> choice = cc.getChoice();
            List<String> label = cc.getLabel();
            ChoiceScreen cs = new ChoiceScreen(this, title, choice, label);
            GameFrame.getInstance().pushScreen(cs);
            startPause();
        } else if (cmd instanceof MonstersCmd) {
            List<Integer> monsterIds = ((MonstersCmd) cmd).getMonsterIds();
            GameFrame.getInstance().getGameContainer().getActiveFileItem().setPredictedMonsterIds(monsterIds);
        } else {
            LOG.warn("unknown command " + cmd);
            throw new RuntimeException("unknown command " + cmd);
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
