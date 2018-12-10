package billy.rpg.game.core.command;

import billy.rpg.game.core.command.processor.CmdProcessor;
import billy.rpg.game.core.container.GameContainer;
import billy.rpg.game.core.script.variable.VariableDeterminer;

import java.util.List;

/**
 * @author lei.liu@datatist.com
 * @since 2018-12-10 13:59:20
 */
public class AddVarCmd extends CmdBase {
    private String key;
    private int delta;

    @Override
    public void init() {
        List<String> arguments = super.getArguments();
        key = arguments.get(0);
        delta = Integer.parseInt(arguments.get(1));
    }

    @Override
    public int execute(GameContainer gameContainer, CmdProcessor cmdProcessor) {
        Integer integer = VariableDeterminer.getInstance().get(key);
        if (integer == null) {
            throw new RuntimeException("variable " + key + " not exists");
        }
        VariableDeterminer.getInstance().set(key, integer + delta);
        return 0;
    }

    @Override
    public String getUsage() {
        return "addvar var 100";
    }

    @Override
    public int getArgumentSize() {
        return 2;
    }
}
