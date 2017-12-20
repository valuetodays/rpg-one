package billy.rpg.game.cmd;

import billy.rpg.game.cmd.executor.CmdProcessor;
import org.apache.log4j.Logger;

/**
 * 命令 - 基础
 * @author <a href="http://blog.sina.com.cn/valuetodays">liulei-home</a>
 * @since 2016-05-09 22:27
 */
public abstract class CmdBase {
    protected Logger LOG = Logger.getLogger(CmdBase.class);
    private String name; // 命令名称
    private int lineNo;


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

    public abstract int execute(CmdProcessor cmdProcessor);

    @Override
    public String toString() {
        return "CmdBase [name=" + name + ", lineNo=" + lineNo + "]";
    }
    
    
}
