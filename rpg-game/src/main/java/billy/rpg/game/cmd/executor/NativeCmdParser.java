package billy.rpg.game.cmd.executor;

import billy.rpg.game.cmd.*;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jline.reader.ParsedLine;
import org.jline.reader.Parser;
import org.jline.reader.impl.DefaultParser;

import java.util.ArrayList;
import java.util.List;

/**
 * 脚本解析成命令
 *
 * @author <a href="http://blog.sina.com.cn/valuetodays">liulei-frx</a>
 * @since 2016-05-10 13:00
 */
public class NativeCmdParser {
    private static final Logger LOG = Logger.getLogger(NativeCmdParser.class);
    private static final String CHAR_SPACE = CmdBase.CHAR_SPACE;
    private NativeCmdParser() { }




    /**
     * 核心方法，解析一行数据到一个命令里
     * @param line command & its argument
     */
    public static CmdBase parse(String script, int lineNumber, String line) {
        int spaceInx = line.indexOf(CHAR_SPACE);
        CmdBase cmdBase = null;
        // 注意monsters命令是一个例外，它可以出现的形式有 monsters; monsters 10;两种

        Parser parser = new DefaultParser();
        ParsedLine parse = parser.parse(line, 0);
        System.out.println(parse.words());
        List<String> words = parse.words();
        if (words.size() == 1) {
            cmdBase = parse0(script, lineNumber, line.toLowerCase());
        } else {
            cmdBase = parseNew(script, lineNumber, words.get(0), words.subList(1, words.size()));
        }
        /*
        if (spaceInx < 0) { // 没有' '，说明是无参数命令（rtn, label）
            cmdBase = parse0(script, lineNumber, line.toLowerCase());
        } else {
            cmdBase = parseN(script, lineNumber, line.substring(0, spaceInx).toLowerCase(), line.substring(spaceInx+1));
        }*/
        cmdBase = EmptyCmd.EMPTY_CMD;

        return cmdBase;
    }

    private static CmdBase parseNew(String script, int lineNumber, String s, List<String> arguments) {

        return null;
    }


    /**
     * 处理0个参数的命令 现有 rtn, label, 以及特殊情况下的monsters
     * @param cmdname command name
     */
    private static CmdBase parse0(String script, int lineNumber, String cmdname) {
        // TODO 可优化，尽量不要使用if语句
        if ("return".equals(cmdname)) {
            return new ReturnCmd(cmdname); // TODO 可优化，只要是这个命令就可以忽略cmdname了
        } else if (cmdname.endsWith(":")) {
            return new LabelCmd(cmdname);
        } else if ("monsters".equals(cmdname)) {
            return new MonstersCmd(null);
        }

        return EmptyCmd.EMPTY_CMD;
    }


    /**
     * 处理多个参数的命令，现有if scenename  attr showText set trigger loadmap talk
     * @param cmdname cmd name
     * @param cmdarg cmd arg
     */
    private static CmdBase parseN(String script, int lineNumber, String cmdname, String cmdarg) {
        cmdname = cmdname.trim();
        if ("if".equals(cmdname)) { // 两个参数
            String[] cmdargs = cmdarg.split(CHAR_SPACE);
            if (cmdargs.length != 2) {
                LOG.debug("command "+cmdname+" needs "+2+" arguments, "
                        + "but "+cmdargs.length+" in fact.");
                return EmptyCmd.EMPTY_CMD;
            }
            return new IfCmd(cmdargs[0], cmdargs[1]);
        } else if ("scenename".equals(cmdname)) {
            return new ScenenameCmd(cmdarg.substring(1, cmdarg.length()-1));
        } else if ("attr".equals(cmdname)) {
            String[] cmdargs = cmdarg.split(CHAR_SPACE);
            if (cmdargs.length != 2) {
                LOG.debug("command "+cmdname+" needs "+2+" arguments, "
                        + "but "+cmdargs.length+" in fact.");
                return null;
            }
            return new AttrCmd();
        } else if ("showtext".equals(cmdname)) {
            return ShowTextCmd.ofNew(script, lineNumber, cmdarg);
        } else if ("set".equals(cmdname)) {
            return new SetCmd(cmdarg);
        }  else if ("unset".equals(cmdname)) {
            return new UnsetCmd(cmdarg);
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
        } else if ("increasemoney".equals(cmdname)) {
            String moneyTxt = cmdarg;
            if (StringUtils.isEmpty(cmdarg)) {
                moneyTxt = "0";
            }
            return new IncreaseMoneyCmd(Integer.valueOf(moneyTxt));
        }  else if ("decreasemoney".equals(cmdname)) {
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
