package com.billy.scriptParser.cmd.executor;

import org.apache.log4j.Logger;

import com.billy.scriptParser.cmd.AnimationCmd;
import com.billy.scriptParser.cmd.AttrCmd;
import com.billy.scriptParser.cmd.CmdBase;
import com.billy.scriptParser.cmd.IfCmd;
import com.billy.scriptParser.cmd.LabelCmd;
import com.billy.scriptParser.cmd.LoadMapCmd;
import com.billy.scriptParser.cmd.MessageBoxCmd;
import com.billy.scriptParser.cmd.ReturnCmd;
import com.billy.scriptParser.cmd.ScenenameCmd;
import com.billy.scriptParser.cmd.SetCmd;
import com.billy.scriptParser.cmd.ShowTextCmd;
import com.billy.scriptParser.cmd.TalkCmd;
import com.billy.scriptParser.cmd.TriggerCmd;

/**
 * @author <a href="http://blog.sina.com.cn/valuetodays">liulei-frx</a>
 * @date 2016-05-10 13:00
 * @since 2016-05-10 13:00
 */
public class ArguementCmdExecutor {

    private static final Logger LOG = Logger.getLogger(ArguementCmdExecutor.class);

    /**
     * 核心方法，解析一行数据到一个命令里
     * @param line
     * @return
     */
    public static CmdBase doDeal(String line) {
        int spaceInx = line.indexOf(" ");
        CmdBase cmdBase = null;
        if (spaceInx < 0) { // 没有' '，说明是无参数命令（rtn, label）
            cmdBase = doDeal0(line);
        } else {
            cmdBase = doDealN(line.substring(0, spaceInx), line.substring(spaceInx+1));
        }

        return cmdBase;
    }


    /**
     * 处理多个参数的命令，现有if scenename  attr showText set trigger loadmap talk
     * @param cmdname
     * @param cmdarg
     * @return
     */
    private static CmdBase doDealN(String cmdname, String cmdarg) {
        if ("if".equals(cmdname)) { // 两个参数
            String[] cmdargs = cmdarg.split(" ");
            if (cmdargs.length != 2) {
                LOG.debug("command "+cmdname+" needs "+2+" arguments, "
                        + "but "+cmdargs.length+" in fact.");
                return null;
            }
            return new IfCmd(cmdargs[0], cmdargs[1]);
        } else if ("scenename".equals(cmdname)) {
            return new ScenenameCmd(cmdarg);
        } else if ("attr".equals(cmdname)) {
            String[] cmdargs = cmdarg.split(" ");
            if (cmdargs.length != 4) {
                LOG.debug("command "+cmdname+" needs "+4+" arguments, "
                        + "but "+cmdargs.length+" in fact.");
                return null;
            }
            AttrCmd attrCmd = new AttrCmd();
            attrCmd.setM(Integer.parseInt(cmdargs[0]));
            attrCmd.setN(Integer.parseInt(cmdargs[1]));
            attrCmd.setWidth(Integer.parseInt(cmdargs[2]));
            attrCmd.setHeight(Integer.parseInt(cmdargs[3]));
            return attrCmd;
        } else if ("showtext".toLowerCase().equals(cmdname.toLowerCase())) {
            return new ShowTextCmd(cmdarg);
        } else if ("set".equals(cmdname)) {
            return new SetCmd(cmdarg);
        } else if ("trigger".equals(cmdname)) {
            String[] cmdargs = cmdarg.split(" ");
            if (cmdargs.length != 3) {
                LOG.debug("command "+cmdname+" needs "+3+" arguments, "
                        + "but "+cmdargs.length+" in fact.");
                return null;
            }
            TriggerCmd tc = new TriggerCmd();
            tc.setX(Integer.valueOf(cmdargs[0]));
            tc.setY(Integer.valueOf(cmdargs[1]));
            tc.setTriggerName(cmdargs[2]);
            return tc;
        } else if ("loadmap".equals(cmdname)) {
            String[] cmdargs = cmdarg.split(" ");
            if (cmdargs.length != 4) {
                LOG.debug("command "+cmdname+" needs "+4+" arguments, "
                        + "but "+cmdargs.length+" in fact.");
                return null;
            }
            return new LoadMapCmd(Integer.valueOf(cmdargs[0]), Integer.valueOf(cmdargs[1]),
                    Integer.valueOf(cmdargs[2]), Integer.valueOf(cmdargs[3]));
        } else if ("talk".equals(cmdname)) {
            String[] cmdargs = cmdarg.split(" ");
            if (cmdargs.length != 2) {
                LOG.debug("command "+cmdname+" needs "+2+" arguments, "
                        + "but "+cmdargs.length+" in fact.");
                return null;
            }
            TalkCmd tc = new TalkCmd(Integer.valueOf(cmdargs[0]), cmdargs[1]);
            return tc;
        } else if ("messagebox".equals(cmdname)) {
            return new MessageBoxCmd(cmdarg);
        } else if ("animation".equals(cmdname)) {
          
            return parseToAnimation(cmdname, cmdarg);
        } else {
            LOG.warn("unknown command " +  cmdname  + cmdarg);
        }

        return null;
    }

    private static CmdBase parseToAnimation(String cmdname, String cmdarg) {
        String[] cmdargs = cmdarg.split(" ");
        if (cmdargs.length != 2) {
            LOG.debug("command "+cmdname+" needs "+2+" arguments, "
                    + "but "+cmdargs.length+" in fact.");
            return null;
        }
        int no = Integer.parseInt(cmdargs[0]);
        int repeat = Integer.parseInt(cmdargs[1]);
        
        AnimationCmd ac = new AnimationCmd(no, repeat == 1 ? true : false);
        
        return ac;
    }


    /**
     * 处理0个参数的命令 现有 rtn, label
     * @param cmdname
     * @return
     */
    private static CmdBase doDeal0(String cmdname) {
        // TODO 可优化，尽量不要使用if语句
        if ("return".equals(cmdname)) {
            return new ReturnCmd(cmdname); // TODO 可优化，只要是这个命令就可以忽略cmdname了
        } else if (cmdname.endsWith(":")) {
            return new LabelCmd(cmdname);
        }

        return null;
    }
}
