package billy.rpg.game.core.command;

import billy.rpg.game.core.command.processor.CmdProcessor;
import billy.rpg.game.core.container.GameContainer;

import org.apache.log4j.Logger;

import java.util.List;

/**
 * 命令 - 基础
 * @author <a href="http://blog.sina.com.cn/valuetodays">liulei-home</a>
 * @since 2016-05-09 22:27
 */
public abstract class CmdBase {
    protected final Logger logger = Logger.getLogger(getClass());
    // 参数个数>=0 (greater or equal)
    protected int ARGUMENT_COUNT_GE_ZERO = -1000; // >= 0
    // 参数个数>0 (greater than)
    protected int ARGUMENT_COUNT_GT_ZERO = -1001; // >= 0
    // 偶数个数的参数
    protected int ARGUMENT_COUNT_EVEN = -1002; // 0, 2, 4
    // 奇数个数的参数
    protected int ARGUMENT_COUNT_ODD = -1003; // 1, 3, 5
    // 最大参数数量
    protected int ARGUMENT_MAX_COUNT = 512;


    private int lineNo; // 命令行号
    private String name; // 命令名称
    private List<String> arguments; // 参数列表

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
    public abstract int execute(GameContainer gameContainer, CmdProcessor cmdProcessor);
    public abstract String getUsage();
    public abstract int getArgumentSize();

    private void checkArgumentSize() {
        int expectArgumentSize = getArgumentSize();
        int realArgumentSize   = arguments.size();
        if (expectArgumentSize == ARGUMENT_COUNT_GT_ZERO) {
            if (!isGtZero(realArgumentSize)) {
                logger.debug("command " + name + " 's arguments size illegal");
            }
        } else if (expectArgumentSize == ARGUMENT_COUNT_GE_ZERO) {
            return;
        } else if (expectArgumentSize == ARGUMENT_COUNT_ODD) {
            if (isGtZero(realArgumentSize) || !isOdd(realArgumentSize)) {
                logger.debug("command "+name+" needs even(1,3,5) arguments, but "+ realArgumentSize +" in fact. usage: " + getUsage());
            }
        } else if (expectArgumentSize > ARGUMENT_MAX_COUNT) {
            logger.debug("command can only have "+ARGUMENT_MAX_COUNT+" arguments, but "+ realArgumentSize +" in fact. usage: " + getUsage());
        } else if (realArgumentSize != expectArgumentSize) {
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
