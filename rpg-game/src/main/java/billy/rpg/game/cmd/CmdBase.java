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
    public static final String CHAR_SPACE = " ";

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
        if (arguments.size() != size) {
            logger.debug("command "+name+" needs "+ size +" arguments, but "+arguments.size()+" in fact. usage: " + getUsage());
        }
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
