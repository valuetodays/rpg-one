package billy.rpg.game.core.command;

import billy.rpg.game.core.command.processor.CmdProcessor;
import billy.rpg.game.core.container.GameContainer;

/**
 * @author lei.liu
 * @since 2018-12-14 18:26:27
 * @see SetWeatherCmd
 */
public class ClearWeatherCmd extends CmdBase {
    @Override
    public void init() {
    }

    @Override
    public int execute(GameContainer gameContainer, CmdProcessor cmdProcessor) {
        gameContainer.getActiveScriptItem().setWeather(null);
        return 0;
    }

    @Override
    public String getUsage() {
        return "ClearWeather ";
    }

    @Override
    public int getArgumentSize() {
        return 0;
    }
}
