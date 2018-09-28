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
        int size = getArgumentSize();
        if (size == ARGUMENT_COUNT_GE_ZERO) {
            return;
        } else if (size == ARGUMENT_COUNT_ODD && !isOdd(size)) {
            logger.debug("command "+name+" needs even(1,3,5) arguments, but "+arguments.size()+" in fact. usage: " + getUsage());
        }

        if (arguments.size() != size) {
            logger.debug("command "+name+" needs "+ size +" arguments, but "+arguments.size()+" in fact. usage: " + getUsage());
        }
    }

    /**
     * whether a number is odd, such like 1,3,5...
     */
    private static boolean isOdd(int n) {
        return (n & 1) != 0;
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
