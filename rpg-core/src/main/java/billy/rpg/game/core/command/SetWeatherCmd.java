package billy.rpg.game.core.command;

import billy.rpg.game.core.command.processor.CmdProcessor;
import billy.rpg.game.core.container.GameContainer;
import billy.rpg.game.core.enums.WeatherEnum;

import java.util.List;

/**
 * @author lei.liu@datatist.com
 * @since 2018-12-14 18:21:35
 * @see ClearWeatherCmd
 */
public class SetWeatherCmd extends CmdBase {
    private String weather;

    @Override
    public void init() {
        List<String> arguments = super.getArguments();
        weather = arguments.get(0);
    }

    @Override
    public int execute(GameContainer gameContainer, CmdProcessor cmdProcessor) {
        WeatherEnum weatherEnum = WeatherEnum.valueByName(weather);
        gameContainer.getActiveScriptItem().setWeather(weatherEnum);
        return 0;
    }

    @Override
    public String getUsage() {
        return "setweather rain";
    }

    @Override
    public int getArgumentSize() {
        return 1;
    }
}
