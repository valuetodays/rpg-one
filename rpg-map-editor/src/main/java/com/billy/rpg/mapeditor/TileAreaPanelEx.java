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
public class TileAreaPanelEx extends JPanel {
    private static final Logger LOG = Logger.getLogger(TileAreaPanelEx.class);

    private MapEditorPanel mapEditorPanel;

    public TileAreaPanelEx(MapEditorPanel mapEditorPanel) {
        this.mapEditorPanel = mapEditorPanel;
        setBackground(new Color(214, 31, 17));
    }

    private String[][] layer;
    private int width;
    private int height;
    private Image tileImage;

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (tileImage != null) {
            g.drawImage(tileImage, 0, 0, null);
        }

    }

    public void bindTileListener() {
        addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
            }
            @Override
            public void mouseMoved(MouseEvent e) {
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
                lastTileX = nx;
                lastTileY = ny;
                LOG.debug("title...x/y=[" + x + "," + y + "], "
                        + ", lastTileX/lastTileY=" + lastTileX + "/" + lastTileY
                );
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



    private int lastTileX;
    private int lastTileY;

    public int getLastTileX() {
        return lastTileX;
    }
    public int getLastTileY() {
        return lastTileY;
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
