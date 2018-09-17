package billy.rpg.game.character;

import java.util.Arrays;

/**
 * @author lei.liu@datatist.com
 * @since 2018-09-17 17:58:16
 */
public enum PositionEnum {
    LEFT("LEFT"),
    RIGHT("RIGHT");

    private String info;

    PositionEnum(String info) {
        this.info = info;
    }

    public static boolean isLegal(PositionEnum position) {
        return Arrays.asList(PositionEnum.values()).contains(position);
    }

    public static void assertLegal(PositionEnum position, String errorMessage) {
        if(!isLegal(position)) {
            throw new RuntimeException(errorMessage);
        }
    }
}
