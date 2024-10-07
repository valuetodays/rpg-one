package billy.rpg.common.util;

import java.awt.*;
import java.util.List;

/**
 * @author lei.liu
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
        return drawString(g, text, null, x, y);
    }

    public static Point drawString(Graphics g, String text, Color color, int x, int y) {
        FontMetrics fm = g.getFontMetrics();
        Color oldColor = g.getColor();
        if (color != null) {
            g.setColor(color);
        }
        g.drawString(text, x, y + fm.getAscent());
        int width = fm.stringWidth(text);
        int height = fm.getAscent() + fm.getDescent() + fm.getLeading();
        g.setColor(oldColor);
        return new Point(x + width, y + height);
    }

    public static Point drawColorStringHorizontal(Graphics g, List<String> texts, List<Color> colors, int x, int y) {
        Point point = new Point(x, y);
        for (int i = 0; i < texts.size(); i++) {
            String text = texts.get(i);
            Color color = colors.get(i);
            // 水平写字，y不变
            point = drawString(g, text, color, point.x, y);
        }
        return point;
    }
}
