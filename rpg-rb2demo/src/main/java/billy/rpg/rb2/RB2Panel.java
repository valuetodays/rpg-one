package billy.rpg.rb2;

import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author liulei@bshf360.com
 * @since 2017-12-15 09:54
 */
public class RB2Panel extends JPanel {
    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger(RB2Panel.class);

    public static final int PANEL_WIDTH = 640;
    public static final int PANEL_HEIGHT = 480;

    public BufferedImage background;


    public RB2Panel() {
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
    }


    public void setBackground(BufferedImage background) {
        this.background = background;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (background != null) {
            g.drawImage(background, 0, 0, null);
        }

        g.dispose();
    }

}
