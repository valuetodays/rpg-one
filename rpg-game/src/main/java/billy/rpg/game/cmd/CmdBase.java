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

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getLineNo() {
        return lineNo;
    }

    public void setLineNo(int lineNo) {
        this.lineNo = lineNo;
    }
    public CmdBase(String name) {
        this.name = name;
    }
    public CmdBase() {
    }

    public void init(List<String> arguments) {}

    protected abstract int execute(CmdProcessor cmdProcessor);
    protected String getSample() { return ""; }
    protected int getArgumentSize() {return 0;}
    protected void checkArgumentSize(int size) {
        if (arguments.size() != size) {
            logger.debug("command "+name+" needs "+size+" arguments, but "+arguments.size()+" in fact.");
        }
    }

    @Override
    public String toString() {
        return "CmdBase [name=" + name + ", lineNo=" + lineNo + "]";
    }
    
    
}
