package billy.rpg.game.cmd.executor;

import billy.rpg.game.cmd.*;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * 脚本解析成命令
 *
 * @author <a href="http://blog.sina.com.cn/valuetodays">liulei-frx</a>
 * @since 2016-05-10 13:00
 */
public class CmdParser {
    private static final Logger LOG = Logger.getLogger(CmdParser.class);
    private CmdParser() { }



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
     * @param line 命令
     */
    public static CmdBase parseLine(String line) {
        if (line == null) {
            return null;
        }
        line = line.trim();
        if (line.length() == 0) {
            return null;
        }

        LOG.debug("data is `"+line+"`");

        // 注释，忽略本行数据
        if (line.startsWith("@")) {
            //	StaticPrintUtils.p("ignoring ... ");
            return null;
        }

        return parse(line);
    }

    /**
     * 核心方法，解析一行数据到一个命令里
     * @param line command & its argument
     */
    private static CmdBase parse(String line) {
        int spaceInx = line.indexOf(" ");
        CmdBase cmdBase = null;
        // 注意monsters命令是一个例外，它可以出现的形式有 monsters; monsters 10;两种
        if (spaceInx < 0) { // 没有' '，说明是无参数命令（rtn, label）
            cmdBase = parse0(line.toLowerCase());
        } else {
            cmdBase = parseN(line.substring(0, spaceInx).toLowerCase(), line.substring(spaceInx+1));
        }

        return cmdBase;
    }


    /**
     * 处理0个参数的命令 现有 rtn, label, 以及特殊情况下的monsters
     * @param cmdname command name
     */
    private static CmdBase parse0(String cmdname) {
        // TODO 可优化，尽量不要使用if语句
        if ("return".equals(cmdname)) {
            return new ReturnCmd(cmdname); // TODO 可优化，只要是这个命令就可以忽略cmdname了
        } else if (cmdname.endsWith(":")) {
            return new LabelCmd(cmdname);
        } else if ("monsters".equals(cmdname)) {
            return new MonstersCmd(null);
        }

        return null;
    }


    /**
     * 处理多个参数的命令，现有if scenename  attr showText set trigger loadmap talk
     * @param cmdname cmd name
     * @param cmdarg cmd arg
     */
    private static CmdBase parseN(String cmdname, String cmdarg) {
        if ("if".equals(cmdname)) { // 两个参数
            String[] cmdargs = cmdarg.split(" ");
            if (cmdargs.length != 2) {
                LOG.debug("command "+cmdname+" needs "+2+" arguments, "
                        + "but "+cmdargs.length+" in fact.");
                return null;
            }
            return new IfCmd(cmdargs[0], cmdargs[1]);
        } else if ("scenename".equals(cmdname)) {
            return new ScenenameCmd(cmdarg.substring(1, cmdarg.length()-1));
        } else if ("attr".equals(cmdname)) {
            String[] cmdargs = cmdarg.split(" ");
            if (cmdargs.length != 2) {
                LOG.debug("command "+cmdname+" needs "+2+" arguments, "
                        + "but "+cmdargs.length+" in fact.");
                return null;
            }
            return new AttrCmd(Integer.parseInt(cmdargs[0]), Integer.parseInt(cmdargs[1]));
        } else if ("showtext".equals(cmdname)) {
            String[] cmdargs = cmdarg.split(" ");
            if (cmdargs.length != 3) {
                LOG.debug("command "+cmdname+" needs "+3+" arguments, "
                        + "but "+cmdargs.length+" in fact.");
                return null;
            }
            final int headNumber = Integer.parseInt(cmdargs[0]);
            final int location = Integer.parseInt(cmdargs[1]);
            final String text = cmdargs[2].substring(1, cmdargs[2].length()-1);
            return new ShowTextCmd(headNumber, location, text);
        } else if ("set".equals(cmdname)) {
            return new SetCmd(cmdarg);
        } else if ("loadmap".equals(cmdname)) {
            String[] cmdargs = cmdarg.split(" ");
            if (cmdargs.length != 6) {
                LOG.debug("command "+cmdname+" needs "+6+" arguments, "
                        + "but "+cmdargs.length+" in fact.");
                return null;
            }
            return new LoadMapCmd(Integer.valueOf(cmdargs[0]), Integer.valueOf(cmdargs[1]),
                    Integer.valueOf(cmdargs[2]), Integer.valueOf(cmdargs[3]),
                    Integer.valueOf(cmdargs[4]), Integer.valueOf(cmdargs[5]) );
        } else if ("trigger".equals(cmdname)) {
            String[] cmdargs = cmdarg.split(" ");
            if (cmdargs.length != 2) {
                LOG.debug("command "+cmdname+" needs "+2+" arguments, "
                        + "but "+cmdargs.length+" in fact.");
                return null;
            }
            return new TriggerCmd(Integer.valueOf(cmdargs[0]), cmdargs[1].toLowerCase());
        } else if ("messagebox".equals(cmdname)) {
            return new MessageBoxCmd(cmdarg.substring(1, cmdarg.length()-1));
        } else if ("animation".equals(cmdname)) {
            String[] cmdargs = cmdarg.split(" ");
            if (cmdargs.length != 3) {
                LOG.debug("command "+cmdname+" needs "+3+" arguments, "
                        + "but "+cmdargs.length+" in fact.");
                return null;
            }
            int number = Integer.parseInt(cmdargs[0]);
            int x = Integer.parseInt(cmdargs[1]);
            int y = Integer.parseInt(cmdargs[2]);

            return new AnimationCmd(number, x, y);
        } else if ("createnpc".equals(cmdname)) {
            String[] cmdargs = cmdarg.split(" ");
            if (cmdargs.length != 5) {
                LOG.debug("command "+cmdname+" needs "+5+" arguments, "
                        + "but "+cmdargs.length+" in fact.");
                return null;
            }
            CreateNPCCmd cnc = new CreateNPCCmd(
                    Integer.parseInt(cmdargs[0]),
                    Integer.parseInt(cmdargs[1]),
                    Integer.parseInt(cmdargs[2]),
                    Integer.parseInt(cmdargs[3]),
                    Integer.parseInt(cmdargs[4])
                    );
            return cnc;
        } else if ("choice".equals(cmdname)) {
            String[] cmdargs = cmdarg.split(" ");
            String title = cmdargs[0];
            ChoiceCmd cc = new ChoiceCmd(title);
            int length = cmdargs.length;
            for (int i = 1; i <= length/2; i++) {
                String choice1 = cmdargs[i]; // 要把它的左右两个引号去掉
                String label1 = cmdargs[length/2 + i];
                cc.addItem(choice1.substring(1, choice1.length()-1), label1);
            }

            return cc;
        } else if ("monsters".equals(cmdname)) {
            if (StringUtils.isEmpty(cmdarg)) {
                return new MonstersCmd(null);
            }
            List<Integer> list = new ArrayList<>();
            String[] monsterIdsStr = cmdarg.split(" ");
            for (String monsterIdStr : monsterIdsStr) {
                if (StringUtils.isNotEmpty(monsterIdStr)) {
                    list.add(Integer.valueOf(monsterIdStr));
                }
            }
            return new MonstersCmd(list);
        } else if ("inscreasemoney".equals(cmdname)) {
            String moneyTxt = cmdarg;
            if (StringUtils.isEmpty(cmdarg)) {
                moneyTxt = "0";
            }
            return new IncreaseMoneyCmd(Integer.valueOf(moneyTxt));
        }  else if ("descreasemoney".equals(cmdname)) {
            String moneyTxt = cmdarg;
            if (StringUtils.isEmpty(cmdarg)) {
                moneyTxt = "0";
            }
            return new DecreaseMoneyCmd(Integer.valueOf(moneyTxt));
        } else if ("increasegoods".equals(cmdname)) {
            return new IncreaseGoodsCmd(Integer.valueOf(cmdarg));
        } else if ("decreasegoods".equals(cmdname)) {
            String[] cmdargArr = cmdarg.split(" ");
            if (cmdargArr.length != 2) {
                LOG.debug("command "+cmdname+" needs "+2+" arguments, "
                        + "but "+cmdargArr.length+" in fact.");
                return null;
            }
            return new DecreaseGoodsCmd(Integer.valueOf(cmdargArr[0]), Integer.valueOf(cmdargArr[1]));
        } else if ("showgut".equals(cmdname)) {
            return new ShowGutCmd(cmdarg.substring(1, cmdarg.length()-1));
        } else if ("npcstep".equals(cmdname)) {
            String[] cmdargArr = cmdarg.split(" ");
            if (cmdargArr.length != 3) {
                LOG.debug("command "+cmdname+" needs "+3+" arguments, "
                        + "but "+cmdargArr.length+" in fact.");
                return null;
            }
            return new NpcstepCmd(Integer.parseInt(cmdargArr[0]), Integer.parseInt(cmdargArr[1]), Integer.parseInt
                    (cmdargArr[2]));
        } else if ("move".equals(cmdname)) {
            String[] cmdargArr = cmdarg.split(" ");
            if (cmdargArr.length != 2) {
                LOG.debug("command "+cmdname+" needs "+2+" arguments, "
                        + "but "+cmdargArr.length+" in fact.");
                return null;
            }
            return new MoveCmd(Integer.parseInt(cmdargArr[0]), Integer.parseInt(cmdargArr[1]));
        } else if ("deletenpc".equals(cmdname)) {
            return new DeleteNpcCmd(Integer.parseInt(cmdarg));
        } else {
            LOG.warn("unknown command " +  cmdname + cmdarg);
            throw new RuntimeException("unknown command " + cmdname + " " + cmdarg);
        }
    }

}
