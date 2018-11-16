package billy.rpg.game.core.command;

import billy.rpg.game.core.command.processor.CmdProcessor;
import billy.rpg.game.core.container.GameContainer;
import billy.rpg.game.core.script.variable.VariableTableDeterminer;

import java.util.List;

/**
 * 取消一个变量，与{@link SetCmd}相反
 *
 * @see SetCmd
 * @author lei.liu@datatist.com
 * @since 2018-09-18 15:13:16
 */
public class UnsetCmd extends CmdBase {
    private String key;

    @Override
    public void init() {
        List<String> arguments = super.getArguments();
        key = arguments.get(0);
    }

    @Override
    public int execute(GameContainer gameContainer, CmdProcessor cmdProcessor) {
        VariableTableDeterminer.getInstance().delVariable(gameContainer, key);
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
}
