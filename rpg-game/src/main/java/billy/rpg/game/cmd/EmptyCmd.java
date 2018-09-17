package billy.rpg.game.cmd;

import billy.rpg.game.cmd.executor.CmdProcessor;

/**
 * an empty command, do nothing
 *
 * @author lei.liu@datatist.com
 * @since 2018-09-17 17:38:14
 */
public class EmptyCmd extends CmdBase {

    private EmptyCmd() {}

    @Override
    public int execute(CmdProcessor cmdProcessor) {
        return 0;
    }

    public static final EmptyCmd EMPTY_CMD = new EmptyCmd();
}
