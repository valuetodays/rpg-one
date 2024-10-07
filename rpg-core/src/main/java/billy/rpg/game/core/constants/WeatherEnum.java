package billy.rpg.game.core.constants;

import billy.rpg.game.core.util.AssertUtil;

/**
 * @author lei.liu
 * @since 2018-12-14 18:23:22
 */
public enum WeatherEnum {
    RAIN,
    SNOW,
    FOG;


    public static WeatherEnum valueByName(String weather) {
        AssertUtil.assertTrue(weather != null, "weather should not be null");
        WeatherEnum weatherEnum = valueOf(weather.toUpperCase());
        AssertUtil.assertTrue(weatherEnum != null, "illegal weather");
        return weatherEnum;
    }


}
