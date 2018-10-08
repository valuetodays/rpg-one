package billy.rpg.game.command;

import billy.rpg.game.command.processor.CmdProcessor;
import billy.rpg.game.script.variable.VariableTableDeterminer;

import java.util.List;

/**
 * 设置一个变量，与{@link UnsetCmd}相反
 *
 * @see UnsetCmd
 * @author <a href="http://blog.sina.com.cn/valuetodays">liulei-home</a>
 * @since 2016-05-10 22:38
 */
public class SetCmd extends CmdBase {
    private String key;

    @Override
    public void init() {
        List<String> arguments = super.getArguments();
        key = arguments.get(0);
    }

    @Override
    public int execute(CmdProcessor cmdProcessor) {
        VariableTableDeterminer.getInstance().putVariable(key);
        return 0;
    }

    @Override
    public String getUsage() {
        return null;
    }

    @Override
    public int getArgumentSize() {
        return 1;
    }

    public String getKey() {
        return key;
    }

    @Override
    public String toString() {
        return "SetCmd{" +
                "key='" + key + '\'' +
                "} " + super.toString();
    }
}
