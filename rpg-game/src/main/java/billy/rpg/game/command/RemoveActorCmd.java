package billy.rpg.game.command;

import billy.rpg.game.GameFrame;
import billy.rpg.game.command.processor.CmdProcessor;

import java.util.List;

/**
 * @author lei.liu@datatist.com
 * @since 2018-11-15 17:00:02
 */
public class RemoveActorCmd extends CmdBase {
    private int heroId;

    @Override
    public void init() {
        List<String> arguments = super.getArguments();
        heroId = Integer.parseInt(arguments.get(0));
    }

    @Override
    public int execute(CmdProcessor cmdProcessor) {
        GameFrame.getInstance().getGameData().removeHeroId(heroId);
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
