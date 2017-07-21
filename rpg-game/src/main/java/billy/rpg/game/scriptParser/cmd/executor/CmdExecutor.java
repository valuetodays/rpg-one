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
import billy.rpg.game.util.StackPrintUtil;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * 命令执行器
 *
 * 本类在最初设计时是为CUI而实现的。现在要转到GUI里，该类暂时因为如下原因不能胜任。
 * <ul>
 *     <li>命令应该是阻塞式的，它没执行完毕不应该往下执行，典型的就是对话</li>
 * </ul>
 *
 * @author <a href="http://blog.sina.com.cn/valuetodays">liulei-frx</a>
 * @since 2016-05-10 11:40
 */
@Deprecated
public class CmdExecutor {
    private static final Logger LOG = Logger.getLogger(CmdExecutor.class);

    /*
     * 执行脚本命令s
     */
    public static int executeCmds(List<CmdBase> cmdList) {
        for (int i = 0; i < cmdList.size(); i++) {
            CmdBase caa = cmdList.get(i);
            int executeCode = executeCmd(caa);
            if (-1 == executeCode) {
                return -1;
            } else if (-2 == executeCode) {
                continue;
            }
        }

        return 0;
    }


    /**
     *
     * 执行脚本命令
     *
     * @param cmd 命令对象
     * @return  -1: rtn occurs;
     *          -2: does not fulfill the if condition
     */
    private static int executeCmd(CmdBase cmd) {
        // 使用instanceof 分开处理各种情况
        if (cmd instanceof ReturnCmd) {
            return -1;
        } else if (cmd instanceof SetCmd) {
            GlobalVirtualTables.putGlobalVariable(((SetCmd) cmd).getKey()); // only one argument
        } else if (cmd instanceof IfCmd) {
            IfCmd ic = (IfCmd) cmd;
            if (GlobalVirtualTables.containsVariable(ic.getCondition())) { // global variable exists
                LabelBean fun = GlobalVirtualTables.getLabel(ic.getTriggerName());
                if (-1 == executeCmds(fun.getCmds())) {
                    return -1;
                }
            } else {    // global variable does not exist
                return -2;
            }
        } else if (cmd instanceof ShowTextCmd) {
            final String text = ((ShowTextCmd)cmd).getText();
            final String text0 = text.substring(1, text.length() - 1);
            StackPrintUtil.p("scriptprocessor");
            LOG.debug(text0);
            DialogScreen ms = new DialogScreen(null, text0);
            GameFrame.getInstance().pushScreen(ms);
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
//            AnimationTimer at = new AnimationTimer(500, 400, 200);
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




}
