package billy.rpg.common.util;

import java.awt.*;

/**
 * @author lei.liu@datatist.com
 * @since 2018-11-09 09:52:03
 */
public class TextUtil {
    private TextUtil() {}

    /**
     * only for test, please use {@link #drawString(Graphics, String, int, int)}
     * @see #drawString(Graphics, String, int, int)
     */
    public static Point drawStringWithBaseline(Graphics g, String text, int x, int y) {
        g.drawString(text, x, y);
        int width = g.getFontMetrics().stringWidth(text);
        Color oldColor = g.getColor();
        g.setColor(ColorUtil.INSTANCE.reverseColor(oldColor));
        g.drawLine(x, y, x + width, y);
        g.setColor(oldColor);
        return new Point(x + width, y);
    }

    /**
     * only for test, please use {@link #drawString(Graphics, String, int, int)}
     * @see #drawString(Graphics, String, int, int)
     */
    public static Point drawStringWithAscent(Graphics g, String text, int x, int y) {
        FontMetrics fm = g.getFontMetrics();
        g.drawString(text, x, y + fm.getAscent());
        int width = fm.stringWidth(text);
        Color oldColor = g.getColor();
        g.setColor(ColorUtil.INSTANCE.reverseColor(oldColor));
        g.drawLine(x, y, x + width, y);
        g.setColor(oldColor);
        return new Point(x + width, y);
    }

    /**
     * only for test, please use {@link #drawString(Graphics, String, int, int)}
     * @see #drawString(Graphics, String, int, int)
     */
    public static Point drawStringWithHeight(Graphics g, String text, int x, int y) {
        FontMetrics fm = g.getFontMetrics();
        g.drawString(text, x, y + fm.getAscent());
        int width = fm.stringWidth(text);
        int height = fm.getAscent() + fm.getDescent() + fm.getLeading();
        Color oldColor = g.getColor();
        g.setColor(ColorUtil.INSTANCE.reverseColor(oldColor));
        g.drawLine(x, y, x + width, y);
        g.drawLine(x, y + height, x + width, y + height);
        g.setColor(oldColor);
        return new Point(x + width, y + height);
    }

    /**
     * only for test, please use {@link #drawString(Graphics, String, int, int)}
     * @see #drawString(Graphics, String, int, int)
     */
    public static Point drawStringWithRect(Graphics g, String text, int x, int y) {
        FontMetrics fm = g.getFontMetrics();
        g.drawString(text, x, y + fm.getAscent());
        int width = fm.stringWidth(text);
        int height = fm.getAscent() + fm.getDescent() + fm.getLeading();

        Color oldColor = g.getColor();
        g.setColor(ColorUtil.INSTANCE.reverseColor(oldColor));
        g.drawRect(x, y, width, height);
        g.setColor(oldColor);
        return new Point(x + width, y + height);
    }

    public static Point drawString(Graphics g, String text, int x, int y) {
        FontMetrics fm = g.getFontMetrics();
        g.drawString(text, x, y + fm.getAscent());
        int width = fm.stringWidth(text);
        int height = fm.getAscent() + fm.getDescent() + fm.getLeading();

        return new Point(x + width, y + height);
    }
}
