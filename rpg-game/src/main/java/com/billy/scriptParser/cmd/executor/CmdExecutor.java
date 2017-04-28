package com.billy.scriptParser.cmd.executor;

import com.billy.scriptParser.bean.script.LabelBean;
import com.billy.scriptParser.cmd.*;
import com.billy.scriptParser.container.GameContainer;
import com.billy.scriptParser.virtualtable.GlobalVirtualTables;
import com.billyrupeng.MainFrame;
import com.billyrupeng.screen.BaseScreen;
import com.billyrupeng.screen.DialogScreen;
import com.billyrupeng.screen.MessageBoxScreen;
import com.billyrupeng.screen.ScenenameScreen;
import com.billyrupeng.timer.AnimationTimer;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * 命令执行器
 *
 * @author <a href="http://blog.sina.com.cn/valuetodays">liulei-frx</a>
 * @date 2016-05-10 11:40
 * @since 2016-05-10 11:40
 */
public class CmdExecutor {

    private static final Logger logger = Logger.getLogger(CmdExecutor.class);

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
     * @return -1: rtn occurs;
     * -2: does not fulfill the if condition
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
//            StackPrintUtil.p();
            final String text = ((ShowTextCmd)cmd).getText();
            final String text0 = text.substring(1, text.length() - 1);
            logger.debug(text0);
            DialogScreen ms = new DialogScreen(text0);
            MainFrame.getInstance().pushScreen(ms);
        } else if (cmd instanceof LoadMapCmd) {
            LoadMapCmd lmc = (LoadMapCmd) cmd;
            logger.debug("go to map" + lmc.getM() + "-" + lmc.getN() + " in " + lmc.getX() + "," + lmc.getY());
//            MapScreen ms = new MapScreen();
//            MainFrame.getInstance().pushScreen(ms);
            GameContainer.getInstance().changeActiveScriptItemTo(lmc.getM(), lmc.getN(), lmc.getX() + "-" + lmc.getY());
        } else if (cmd instanceof ScenenameCmd) {
            final String scenename = ((ScenenameCmd) cmd).getSname();
            final String scenename0 = scenename.substring(1, scenename.length() - 1);
            BaseScreen bs = new ScenenameScreen(scenename0);
            MainFrame.getInstance().pushScreen(bs);
        } else if (cmd instanceof AttrCmd) {
            System.out.println(" >> basic attribute of this map ");
            AttrCmd ac = (AttrCmd) cmd;
            GameContainer.getInstance().getActiveFileItem().getMm().setHeight(ac.getHeight());
            GameContainer.getInstance().getActiveFileItem().getMm().setWidth(ac.getWidth());
        } else if (cmd instanceof TalkCmd)  {
            System.out.println("talk to u");
        } else if (cmd instanceof MessageBoxCmd) {
            final MessageBoxCmd mb = (MessageBoxCmd) cmd;
            logger.debug(mb);
            final String msg = mb.getMsg();
            final String msg0 = msg.substring(1, msg.length() - 1);
            final BaseScreen bs = new MessageBoxScreen(msg0);
            MainFrame.getInstance().pushScreen(bs);
        } else if (cmd instanceof AnimationCmd) {
            final AnimationCmd ac = (AnimationCmd) cmd;
            int no = ac.getNo();
            boolean repeat = ac.isRepeat();
//            BaseScreen bs = new AnimationScreen(null, no, repeat); 
//            MainFrame.getInstance().pushScreen(bs);
            AnimationTimer at = new AnimationTimer(500, 400, 200);
        } else {
            logger.warn("no command comfit " + cmd);
        }

        return 0;
    }

    /**
     * 解析一行脚本 处理成命令
     *
     *  各命令可进一步解析（可根据参数个数与参数中是否允许出现' '联合判断）：
     *   scenename 后只有一个参数
     *   attr 后跟4个参数（纯数字）
     *   showText 后只有一个参数
     *   if 两个参数 (条件 触发器)
     *   rtn 无参数
     *   set
     *   *: 以冒号结尾 说明是标签
     * @param line
     * @return
     */
    public static CmdBase processLine(String line) {
        if (line == null) {
            return null;
        }
        line = line.trim();
        if (line.length() == 0) {
            return null;
        }

        logger.debug("data is `"+line+"`");

        // 注释，忽略本行数据
        if (line.startsWith("@")) {
            //	StaticPrintUtils.p("ignoring ... ");
            return null;
        }

        return ArguementCmdExecutor.doDeal(line);
    }



}
