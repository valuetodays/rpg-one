package com.billy.rpg.mapeditor;

import org.apache.log4j.Logger;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * to show tile
 *
 * @author <a href="http://blog.sina.com.cn/valuetodays">liulei-home</a>
 * @since 2017-04-28 21:40
 */
public class TileAreaPanel extends JPanel {
    private static final Logger LOG = Logger.getLogger(TileAreaPanel.class);

    private MapEditorPanel mapEditorPanel;

    public TileAreaPanel(MapEditorPanel mapEditorPanel) {
        this.mapEditorPanel = mapEditorPanel;
        setBackground(new Color(214, 31, 17));
    }

    private Image tileImage;

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (tileImage != null) {
            g.drawImage(tileImage, 0, 0, null);
            Color oldColor = g.getColor();
            g.setColor(Color.RED);
            g.drawRect(rectX*32, rectY*32, width, height);
            g.setColor(oldColor);
//            LOG.debug("paint()");
        }

    }
    private int rectX;
    private int rectY;
    private int width = 32;
    private int height = 32;

    public void bindTileListener() {
        addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
            }
            @Override
            public void mouseMoved(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                rectX = x / 32;
                rectY = y / 32;
//                LOG.debug("rectX/rectY=" + rectX + "/" + rectY);
                repaint();
            }
        });

        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }
            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                int nx = x / 32;
                int ny = y / 32;
                LOG.debug("title...x/y=[" + x + "," + y + "], "
                        + ", nx/ny=" + nx + "/" + ny
                );
                lastTileN = nx * 100 + ny;
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



    private int lastTileN;

    public int getLastTileN() {
        return lastTileN;
    }

    public Image getTileImage() {
        return tileImage;
    }

    public void initTileImage(String imagePath) {
        try {
            tileImage = ImageIO.read(new FileInputStream(imagePath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        LOG.debug("tileImage" + (tileImage == null));
        repaint();
    }
}
