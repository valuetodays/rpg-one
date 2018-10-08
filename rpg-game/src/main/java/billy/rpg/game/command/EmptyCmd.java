package billy.rpg.game.command;

import billy.rpg.game.command.processor.CmdProcessor;

/**
 * an empty command, do nothing
 *
 * @author lei.liu@datatist.com
 * @since 2018-09-17 17:38:14
 */
public class EmptyCmd extends CmdBase {

    private EmptyCmd() {}

    @Override
    public void init() {

    }

    @Override
    public int execute(CmdProcessor cmdProcessor) {
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

    public static final EmptyCmd EMPTY_CMD = new EmptyCmd();
}
