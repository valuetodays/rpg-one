package billy.rpg.game.core.command;

import billy.rpg.game.core.command.processor.CmdProcessor;
import billy.rpg.game.core.container.GameContainer;

import java.util.List;

/**
 * @author lei.liu
 * @since 2018-11-20 11:14:32
 */
public class BattleImageCmd extends CmdBase {
    private String imagePath;

    @Override
    public void init() {
        List<String> arguments = super.getArguments();
        imagePath = arguments.get(0);
    }

    public String getImagePathe() {
        return imagePath;
    }

    /**
     * @see AttrCmd
     * @see TriggerCmd
     */
    @Override
    public int execute(GameContainer gameContainer, CmdProcessor cmdProcessor) {
        return 0;
    }

    @Override
    public String getUsage() {
        return "battleimage \"xxxx.png/jpg\"";
    }

    @Override
    public int getArgumentSize() {
        return 1;
    }
}
