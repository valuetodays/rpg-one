package billy.rpg.game.core.command;


import billy.rpg.game.core.command.processor.CmdProcessor;
import billy.rpg.game.core.container.GameContainer;

/**
 * @author liulei-home
 * @since 2018-09-27 22:14
 */
public class PlayBGMCmd extends CmdBase {

    @Override
    public void init() {

    }

    @Override
    public int execute(GameContainer gameContainer, CmdProcessor cmdProcessor) {
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
