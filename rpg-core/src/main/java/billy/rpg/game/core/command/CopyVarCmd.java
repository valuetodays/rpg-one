package billy.rpg.game.core.command;

import billy.rpg.game.core.command.processor.CmdProcessor;
import billy.rpg.game.core.container.GameContainer;
import billy.rpg.game.core.script.variable.VariableDeterminer;

import java.util.List;

/**
 * @author lei.liu@datatist.com
 * @since 2018-12-10 14:05:20
 */
public class CopyVarCmd extends CmdBase {
    private String keyTarget;
    private String keySource;

    @Override
    public void init() {
        List<String> arguments = super.getArguments();
        keyTarget = arguments.get(0);
        keySource = arguments.get(1);
    }

    @Override
    public int execute(GameContainer gameContainer, CmdProcessor cmdProcessor) {
        Integer integer = VariableDeterminer.getInstance().get(keySource);
        if (integer == null) {
            throw new RuntimeException("variable " + keySource + " not exists");
        }
        VariableDeterminer.getInstance().set(keyTarget, integer);
        return 0;
    }

    @Override
    public String getUsage() {
        return null;
    }

    @Override
    public int getArgumentSize() {
        return 0;
    }
}
