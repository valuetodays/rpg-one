package billy.rpg.game.cmd;

import billy.rpg.game.cmd.executor.CmdProcessor;
import billy.rpg.game.script.variable.VariableTableDeterminer;

/**
 * 设置一个变量，与{@link UnsetCmd}相反
 *
 * @see UnsetCmd
 * @author <a href="http://blog.sina.com.cn/valuetodays">liulei-home</a>
 * @since 2016-05-10 22:38
 */
public class SetCmd extends CmdBase {
    private String key;
    
    public SetCmd(String key) {
        super("set");
        this.key = key;
    }

    @Override
    public int execute(CmdProcessor cmdProcessor) {
        VariableTableDeterminer.getInstance().putVariable(key);
        return 0;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "SetCmd{" +
                "key='" + key + '\'' +
                "} " + super.toString();
    }
}
