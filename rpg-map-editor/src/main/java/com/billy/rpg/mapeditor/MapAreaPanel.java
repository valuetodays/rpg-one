package com.billy.rpg.mapeditor;

import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="http://blog.sina.com.cn/valuetodays">liulei-home</a>
 * @since 2017-04-28 21:40
 */
public class MapAreaPanel extends JPanel {
    private static final Logger LOG = Logger.getLogger(MapAreaPanel.class);

    private MapEditorPanel mapEditorPanel;

    public MapAreaPanel(MapEditorPanel mapEditorPanel) {
        this.mapEditorPanel = mapEditorPanel;
        setBackground(new Color(214, 31, 17));
    }

    private java.util.List<int[][]> layers;
    private int tileXwidth;
    private int tileYheight;
    private int currentLayer = 0;
    private static final int FLAG_LAYER = 3;

    /**
     * 置当前层为活动层
     * @param currentLayer 当前层数，注意，此处传的是1~4，但layers的下标是0~3，所以需要减1
     */
    public void setCurrentLayer(int currentLayer) {
        this.currentLayer = currentLayer-1;
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
    private int rectX;
    private int rectY;

    public void bindMapListener() {
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
                if (nx > tileXwidth || ny > tileYheight) {
                    return ;
                }
                // 分开处理地图层与事件层
                if (currentLayer != FLAG_LAYER) {
                    int[][] tmpLayer = layers.get(currentLayer);
                    tmpLayer[nx][ny] = mapEditorPanel.getTileArea().getLastTileN();
                    LOG.debug("layer " + currentLayer
                            + " in map (x/y" + x + "/" + y + ")[" + nx + "," + ny + "]="
                            + tmpLayer[nx][ny]);
                } else {
                    int[][] flagLayer = layers.get(currentLayer);
                    flagLayer[nx][ny] *= -1; //

                }
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

        BufferedImage paint = new BufferedImage(
                tileXwidth * 32,
                tileYheight * 32,
                BufferedImage.TYPE_4BYTE_ABGR);
        // 得到缓冲区的画笔
        Graphics g2 = paint.getGraphics();
        // 先画地图层
        for (int layern = 0; layern < FLAG_LAYER; layern++) {

            int[][] layer = layers.get(layern);
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
            g.drawRect(rectX * 32, rectY * 32, 32, 32);
        }
        // 再画事件层
        int[][] flagLayer = layers.get(FLAG_LAYER);
        for (int i = 0; i < tileXwidth; i++) {
            for (int j = 0; j < tileYheight; j++) {
                if (flagLayer[i][j] == -1) { // 不可行 TODO 使用常量类
                    int leftX = i * 32;
                    int leftY = j * 32;
                    int rightX = i * 32 + 32;
                    int rightY = j * 32 + 32;
                    // TODO 
                    g.drawLine(leftX, leftY, rightX, rightY);
                }
            }
        }


    }

    public List<int[][]> getLayers() {
        return layers;
    }



}
