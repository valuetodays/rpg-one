package com.billy.rpg.mapeditor;

import org.apache.log4j.Logger;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

/**
 *
 * @author liulei
 * @date 2017-04-28 13:35
 */
@Deprecated
public class TilePanel extends JPanel {
    private static final Logger LOG = Logger.getLogger(TilePanel.class);
    private String tileImagePath;
    private Image tileImage;


    public TilePanel() {
        setPreferredSize(new Dimension(256, 576));
        BufferedImage paint = new BufferedImage(
                256,
                576,
                BufferedImage.TYPE_4BYTE_ABGR);
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.BLACK);

        g.drawImage(tileImage, 0, 0, null);
        super.paint(g);
    }

    public void setTileImagePath(String tileImagePath) {
        if (tileImagePath == null) {
            return;
        }

        this.tileImagePath = tileImagePath;
        try {
            tileImage = ImageIO.read(new FileInputStream(tileImagePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.revalidate();
    }


    public void bindListener() {
        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
//                LOG.debug("mouse in tile = `" + x + "," + y + "`");
                int width = getWidth();
                int height = getHeight();

                int x1 = getX();
                int y1 = getY();

                LOG.debug("x/y=[" + x + "," + y + "], "
                        + ",w/h=" + width + "/" + height
                        + ",x1/y1=" + x1 + "/" + y1
                        + ","
                );
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }
            @Override
            public void mouseReleased(MouseEvent e) {
            }
            @Override
            public void mouseEntered(MouseEvent e) {
            }
            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
    }



}
