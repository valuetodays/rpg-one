package billy.rpg.game;


import billy.rpg.game.constants.GameConstant;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class GamePanel extends JPanel {
    private static final long serialVersionUID = -104477368799466779L;
    private static final Logger LOG = Logger.getLogger(GamePanel.class);

    AffineTransform transform = new AffineTransform();
    AffineTransformOp ato;

    public GamePanel() {
        setPreferredSize(
                new Dimension(
                        GameConstant.GAME_WIDTH * GameConstant.SCALE,
                        GameConstant.GAME_HEIGHT * GameConstant.SCALE));
        LOG.debug("new " + this.getClass().getSimpleName() + "()");
        transform.setToScale(GameConstant.SCALE, GameConstant.SCALE);
        ato = new AffineTransformOp(transform, null);
    }

    public BufferedImage background;
    public BufferedImage dest;

    public void setBackground(BufferedImage background) {
        this.background = background;
    }

    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);

        if (background != null) {
            if (dest == null) {
                dest = new BufferedImage(
                        GameConstant.GAME_WIDTH * GameConstant.SCALE,
                        GameConstant.GAME_HEIGHT * GameConstant.SCALE,
                        BufferedImage.TYPE_4BYTE_ABGR);
            }

            ato.filter(background, dest);
            g.drawImage(dest, 0, 0, null);
            // 执行初始命令
            GameFrame.getInstance().getGameContainer().executePrimary();
        }
    }

}
