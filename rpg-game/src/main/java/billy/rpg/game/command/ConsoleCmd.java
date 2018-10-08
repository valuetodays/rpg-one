package billy.rpg.game.command;

import billy.rpg.game.command.processor.CmdProcessor;

import java.util.List;

/**
 * @author lei.liu@datatist.com
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
    public int execute(CmdProcessor cmdProcessor) {
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
