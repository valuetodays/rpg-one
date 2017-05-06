package com.billy.rpg.mapeditor;

import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;

/**
 * @author <a href="http://blog.sina.com.cn/valuetodays">liulei-home</a>
 * @date 2017-04-28 21:40
 * @since 2017-04-28 21:40
 */
public class MapAreaPanelEx extends JPanel {
    private static final Logger LOG = Logger.getLogger(MapAreaPanelEx.class);

    private MapEditorPanel mapEditorPanel;

    public MapAreaPanelEx(MapEditorPanel mapEditorPanel) {
        this.mapEditorPanel = mapEditorPanel;
        setBackground(new Color(214, 31, 17));
    }

    private java.util.List<int[][]> layers;
    private int tileXwidth;
    private int tileYheight;
    private int currentLayer = 0; // TODO 可变的层

    public void setCurrentLayer(int currentLayer) {
        this.currentLayer = currentLayer;
        repaint();
    }

    /**
     * 初始化map显示区域，
     * @param width w
     * @param height h
     */
    public void initMapShow(int width, int height) {
        int[][] layer1 = new int[width][height]; // layer1
        int[][] layer2 = new int[width][height]; // layer2
        int[][] layer3 = new int[width][height]; // layer3
        int[][] layer4 = new int[width][height]; // event
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                layer1[i][j] = -1;
                layer2[i][j] = -1;
                layer3[i][j] = -1;
                layer4[i][j] = -1;
            }
        }
        layers = new ArrayList<>();
        layers.add(layer1);
        layers.add(layer2);
        layers.add(layer3);
        layers.add(layer4);
        this.tileXwidth = width;
        this.tileYheight = height;

        LOG.debug("mapShow ["+width+"]["+height+"]");
    }

    public int getTileXwidth() {
        return tileXwidth;
    }

    public int getTileYheight() {
        return tileYheight;
    }

    public void bindMapListener() {
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
                if (nx > tileXwidth || ny > tileYheight) {
                    return ;
                }
                int[][] tmpLayer = layers.get(currentLayer);
                tmpLayer[nx][ny] = mapEditorPanel.getTileArea().getLastTileN();
                LOG.debug(" in map (x/y"+x + "/" + y +")["+nx +"," + ny +"]=" + tmpLayer[nx][ny]);
                repaint();
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



    @Override
    public void paint(Graphics g) {
        super.paint(g);

        Image tileImage = mapEditorPanel.getTileArea().getTileImage();

        if (tileImage == null) {
            LOG.debug("image is null");
            return ;
        }

        for (int layern = 0; layern < layers.size(); layern++) {
            BufferedImage paint = new BufferedImage(
                    tileXwidth * 32,
                    tileYheight * 32,
                    BufferedImage.TYPE_4BYTE_ABGR);
            // 得到缓冲区的画笔
            Graphics g2 = paint.getGraphics();

            int[][] layer = layers.get(layern); // TODO 只显示一层数据
            for (int i = 0; i < tileXwidth; i++) {
                for (int j = 0; j < tileYheight; j++) {
                    int s = layer[i][j];
                    if (s != -1) {
                        int y = s % 100;
                        int x = s / 100;

                        g2.drawImage(tileImage,
                            i*32, j*32, i*32+32, j*32+32,
                            x*32, y*32, x*32+32, y*32+32,
                            null);
                    }
                }
            }
            if (layern == currentLayer) {
                g.drawImage(paint, 0, 0, null);
            } else {
                g.drawImage(ImageUtil.toGray(paint), 0, 0, null);
            }
        }
    }

    public List<int[][]> getLayers() {
        return layers;
    }



}
