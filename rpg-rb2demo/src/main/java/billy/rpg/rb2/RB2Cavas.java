package billy.rpg.rb2;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author liulei@bshf360.com
 * @since 2017-12-18 15:40
 */
public class RB2Cavas extends Canvas {
    private BufferedImage background;
    private RB2Frame frame;

    public RB2Cavas(RB2Frame rb2Frame) {
        frame = rb2Frame;
        background = new BufferedImage(
                RB2Panel.PANEL_WIDTH,
                RB2Panel.PANEL_HEIGHT,
                BufferedImage.TYPE_4BYTE_ABGR);
    }

    public void drawBitmap(BufferedImage bitmap, int left, int top) {
        Graphics g = background.getGraphics();
        g.setColor(Color.BLACK);
        g.drawImage(bitmap, left, top, null);
        frame.getPanel().setBackground(background);
    }

}
