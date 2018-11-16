package billy.rpg.game.core.constants;

import java.util.Arrays;

/**
 *
 * @author liulei
 * @since 2017-05-15 15:41
 */
public interface WalkableConstant {
    int DIRECTION_DOWN = PositionEnum.DOWN.getValue();
    int DIRECTION_LEFT = PositionEnum.LEFT.getValue();
    int DIRECTION_RIGHT = PositionEnum.RIGHT.getValue();
    int DIRECTION_UP = PositionEnum.UP.getValue();

    public static enum PositionEnum {
        LEFT(1),
        RIGHT(2),
        UP(3),
        DOWN(0);

        private int value;

        PositionEnum(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static boolean isX(PositionEnum position) {
            return (position == LEFT || position == RIGHT);
        }
        public static boolean isY(PositionEnum position) {
            return (position == UP || position == DOWN);
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
}
