package billy.rpg.game.command;

import billy.rpg.game.GameFrame;
import billy.rpg.game.command.processor.CmdProcessor;

import java.util.List;

/**
 * @author lei.liu@datatist.com
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
    public int execute(CmdProcessor cmdProcessor) {
        GameFrame.getInstance().getGameData().addHeroId(heroId);
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
