package billy.rpg.game.core.enums;

import billy.rpg.game.core.util.AssertUtil;

/**
 * @author lei.liu@datatist.com
 * @since 2018-12-14 18:23:22
 */
public enum WeatherEnum {
    RAIN,
    SNOW,
    FOG;


    public static WeatherEnum valueByName(String weather) {
        WeatherEnum weatherEnum = valueOf(weather);
        AssertUtil.assertTrue(weatherEnum != null, "illegal weather");
        return weatherEnum;
    }


}
