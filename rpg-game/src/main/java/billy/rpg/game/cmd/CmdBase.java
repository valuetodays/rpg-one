package billy.rpg.game.cmd;

/**
 * 命令 - 基础
 * @author <a href="http://blog.sina.com.cn/valuetodays">liulei-home</a>
 * @since 2016-05-09 22:27
 */
public class CmdBase {
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
    @Override
    public String toString() {
        return "CmdBase [name=" + name + ", lineNo=" + lineNo + "]";
    }
    
    
}
