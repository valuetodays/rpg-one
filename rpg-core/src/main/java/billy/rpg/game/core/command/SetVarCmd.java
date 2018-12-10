package billy.rpg.game.core.command;

import billy.rpg.game.core.command.processor.CmdProcessor;
import billy.rpg.game.core.container.GameContainer;
import billy.rpg.game.core.script.event.EventTableDeterminer;
import billy.rpg.game.core.script.variable.GlobalVariable;
import billy.rpg.game.core.script.variable.VariableDeterminer;

import java.util.List;

public class SetVarCmd extends CmdBase {
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
        VariableDeterminer.getInstance().set(key, delta);
        return 0;
    }

    @Override
    public String getUsage() {
        return "setvar var value";
    }

    @Override
    public int getArgumentSize() {
        return 2;
    }
}
