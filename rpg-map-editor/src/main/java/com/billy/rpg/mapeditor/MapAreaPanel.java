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
    private static final int WALK_FLAG = 3;
    private static final int EVENT_LAYER = 4;

    /**
     * 置当前层为活动层
     * @param currentLayer 当前层数，注意，此处传的是1~5，但layers的下标是0~4，所以需要减1
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
    public void initMapLayer(int width, int height) {
        int[][] layer1 = new int[width][height]; // layer1
        int[][] layer2 = new int[width][height]; // layer2
        int[][] layer3 = new int[width][height]; // layer3
        int[][] layer4 = new int[width][height]; // walk
        int[][] layer5 = new int[width][height]; // event
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                layer1[i][j] = -1;
                layer2[i][j] = -1;
                layer3[i][j] = -1;
                layer4[i][j] = -1;
                layer5[i][j] = -1;
            }
        }
        layers = new ArrayList<>();
        layers.add(layer1);
        layers.add(layer2);
        layers.add(layer3);
        layers.add(layer4);
        layers.add(layer5);
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
                mapEditorPanel.getMapEditorFrame().getEventNumDialog().setVisible(false);

                int x = e.getX();
                int y = e.getY();
                rectX = x / 32;
                rectY = y / 32;
                if (rectX > tileXwidth || rectY > tileYheight) {
                    return ;
                }
                // 分开处理行走层，事件层与地图层
                if (currentLayer == WALK_FLAG) { // 行走层
                    int[][] flagLayer = layers.get(currentLayer);
                    flagLayer[rectX][rectY] *= -1; //
                } else if (currentLayer == EVENT_LAYER) { // 事件层
                    // TODO 位置要考虑贴边的情况
                    EventNumDialog eventNumDialog = mapEditorPanel.getMapEditorFrame().getEventNumDialog();
                    eventNumDialog.setLocation(e.getXOnScreen(), e.getYOnScreen());
                    eventNumDialog.setVisible(true);
                } else {
                    int[][] tmpLayer = layers.get(currentLayer);
                    tmpLayer[rectX][rectY] = mapEditorPanel.getTileArea().getLastTileN();
                    LOG.debug("layer " + currentLayer
                            + " in map (x/y" + x + "/" + y + ")[" + rectX + "," + rectY + "]="
                            + tmpLayer[rectX][rectY]);
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

        final Image tileImage = mapEditorPanel.getTileArea().getTileImage();

        if (tileImage == null) {
            LOG.debug("image is null");
            return ;
        }

        // 先画地图层
        for (int layern = 0; layern < WALK_FLAG; layern++) {
            BufferedImage paint = new BufferedImage(
                    tileXwidth * 32,
                    tileYheight * 32,
                    BufferedImage.TYPE_4BYTE_ABGR);
            // 得到缓冲区的画笔
            Graphics g2 = paint.getGraphics();

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

        if (currentLayer == WALK_FLAG) {
            // 再画事件层，事件层用magenta颜色
            Color oldColor = g.getColor();
            g.setColor(Color.MAGENTA);
            int[][] flagLayer = layers.get(WALK_FLAG);
            for (int i = 0; i < tileXwidth; i++) {
                for (int j = 0; j < tileYheight; j++) {
                    if (flagLayer[i][j] == -1) { // 不可行 TODO 使用常量类
                        int topX = i * 32;
                        int topY = j * 32;
                        int bottomX = i * 32 + 32;
                        int bottomY = j * 32 + 32;

                        // 先画边框，再画交叉
                        g.drawRect(topX, topY, 32, 32);
                        g.drawLine(topX, topY, bottomX, bottomY);
                        g.drawLine(topX, bottomY, bottomX, topY);
                    }
                }
            }
            g.setColor(oldColor);
        }

    }

    public List<int[][]> getLayers() {
        return layers;
    }


    public void setEventNum(int eventNum) {
        if (currentLayer != EVENT_LAYER) {
            return ;
        }
        int[][] eventLayer = layers.get(EVENT_LAYER);
        eventLayer[rectX][rectY] = eventNum;
    }

    public void setLayers(List<int[][]> layers) {
        this.layers = layers;
    }

    public void setTileXwidth(int tileXwidth) {
        this.tileXwidth = tileXwidth;
    }

    public void setTileYheight(int tileYheight) {
        this.tileYheight = tileYheight;
    }
}
