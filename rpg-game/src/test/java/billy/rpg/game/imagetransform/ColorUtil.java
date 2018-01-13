package billy.rpg.game.imagetransform;

/**
 * @author liulei@bshf360.com
 * @since 2018-01-13 18:02
 */
public class ColorUtil {
    private ColorUtil() {}

    /**
     * rgb to intValue
     * @param red r
     * @param green g
     * @param blue b
     * @param alpha a
     */
    public static int color2int(int red, int green, int blue, int alpha) {
        return  ((alpha & 0xFF) << 24) |
                ((red & 0xFF) << 16) |
                ((green & 0xFF) << 8)  |
                ((blue & 0xFF) << 0);
    }
}
