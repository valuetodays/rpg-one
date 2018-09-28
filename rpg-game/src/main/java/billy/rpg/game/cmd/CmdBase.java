package billy.rpg.game.cmd;

import billy.rpg.game.cmd.executor.CmdProcessor;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * 命令 - 基础
 * @author <a href="http://blog.sina.com.cn/valuetodays">liulei-home</a>
 * @since 2016-05-09 22:27
 */
public abstract class CmdBase {
    public final Logger logger = Logger.getLogger(getClass());
    protected int ARGUMENT_COUNT_GE_ZERO = -1000; // >= 0
    protected int ARGUMENT_COUNT_EVEN = -1000; // 0, 2, 4
    protected int ARGUMENT_COUNT_ODD = -1000; // 1, 3, 5
    protected int ARGUMENT_MAX_COUNT = 512;


    private int lineNo;
    private String name; // 命令名称
    private List<String> arguments;

    public void initCommand(int lineNo, String name, List<String> arguments) {
        this.lineNo = lineNo;
        this.name = name;
        this.arguments = arguments;

        this.checkArgumentSize();
        init();
    }

    public String getName() {
        return name;
    }
    public int getLineNo() {
        return lineNo;
    }
    public List<String> getArguments() {
        return arguments;
    }

    public abstract void init();

    public abstract int execute(CmdProcessor cmdProcessor);
    public abstract String getUsage();
    public abstract int getArgumentSize();
    private void checkArgumentSize() {
        int expectArgumentSize = getArgumentSize();
        int realArgumentSize   = arguments.size();
        if (expectArgumentSize == ARGUMENT_COUNT_GE_ZERO) {
            return;
        } else if (expectArgumentSize == ARGUMENT_COUNT_ODD) {
            if (isGtZero(realArgumentSize) || !isOdd(realArgumentSize)) {
                logger.debug("command "+name+" needs even(1,3,5) arguments, but "+ realArgumentSize +" in fact. usage: " + getUsage());
            }
        }

        if (expectArgumentSize > ARGUMENT_MAX_COUNT) {
            logger.debug("command can only have "+ARGUMENT_MAX_COUNT+" arguments, but "+ realArgumentSize +" in fact. usage: " + getUsage());
        }
        if (realArgumentSize != expectArgumentSize) {
            logger.debug("command "+name+" needs "+ expectArgumentSize +" arguments, but "+ realArgumentSize +" in fact. usage: " + getUsage());
        }
    }

    /**
     * whether a number is odd, such like 1,3,5...
     */
    private static boolean isOdd(int n) {
        return (n & 1) != 0;
    }

    /**
     * whether a number is greater than 0
     */
    private static boolean isGtZero(int n) {
        return n > 0;
    }

    @Override
    public String toString() {
        return "CmdBase{" +
                "lineNo=" + lineNo +
                ", name='" + name + '\'' +
                ", arguments=" + arguments +
                '}';
    }
}
