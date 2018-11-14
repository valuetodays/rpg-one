package billy.rpg.common.util;

import java.awt.*;

/**
 * @author lei.liu@datatist.com
 * @since 2018-11-14 11:52:22
 */
public class DrawUtil {
    private DrawUtil() {}

    public static void drawSubImage(Graphics g, Image image,
                                    int destX, int destY,
                                    int sourceX, int sourceY,
                                    int width, int height) {
        g.drawImage(image,
                destX, destY,
                destX + width, destY + height,
                sourceX, sourceY,
                sourceX + width, sourceY + height,
                null);
    }
}
