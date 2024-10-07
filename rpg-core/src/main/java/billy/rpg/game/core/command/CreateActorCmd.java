package billy.rpg.game.core.command;

import billy.rpg.game.core.command.processor.CmdProcessor;
import billy.rpg.game.core.container.GameContainer;

import java.util.List;

/**
 * @author lei.liu
 * @since 2018-11-15 16:57:18
 */
public class CreateActorCmd extends CmdBase {
    private int heroId;

    @Override
    public void init() {
        List<String> arguments = super.getArguments();
        heroId = Integer.parseInt(arguments.get(0));
    }

    @Override
    public int execute(GameContainer gameContainer, CmdProcessor cmdProcessor) {
        gameContainer.getGameData().addHeroId(heroId);
        return 0;
    }

    @Override
    public String getUsage() {
        return "createactor heroId";
    }

    @Override
    public int getArgumentSize() {
        return 1;
    }
}
