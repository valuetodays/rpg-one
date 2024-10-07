package billy.rpg.game.core.command;

import billy.rpg.game.core.command.processor.CmdProcessor;
import billy.rpg.game.core.container.GameContainer;

import java.util.List;

/**
 * @author lei.liu
 * @since 2018-09-29 15:09:20
 */
public class ConsoleCmd extends CmdBase {
    private String message;

    @Override
    public void init() {
        List<String> arguments = super.getArguments();
        message = arguments.get(0);
    }

    @Override
    public int execute(GameContainer gameContainer, CmdProcessor cmdProcessor) {
        System.out.println(message);
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
