package billy.rpg.common.util;

import java.awt.*;

/**
 * @author lei.liu@datatist.com
 * @since 2018-11-09 11:21:23
 */
public enum ColorUtil {
    INSTANCE;

    public Color reverseColor(Color color) {
        int rgb = color.getRGB();
        return new Color(0xFFFFFF - rgb);
    }


    /**
     * rgb to intValue
     * @param red r
     * @param green g
     * @param blue b
     * @param alpha a
     */
    public int rgba2int(int red, int green, int blue, int alpha) {
        return  ((alpha & 0xFF) << 24) |
                ((red & 0xFF) << 16) |
                ((green & 0xFF) << 8)  |
                ((blue & 0xFF) << 0);
    }

}
