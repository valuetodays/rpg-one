package com.billy.rpg.mapeditor;

import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

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

    private java.util.List<String[][]> layers;
    private int width;
    private int height;
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
        String[][] layer1 = new String[width][height]; // layer1
        String[][] layer2 = new String[width][height]; // layer2
        String[][] layer3 = new String[width][height]; // layer3
        String[][] layer4 = new String[width][height]; // event
        layers = new ArrayList<>();
        layers.add(layer1);
        layers.add(layer2);
        layers.add(layer3);
        layers.add(layer4);
        this.width = width;
        this.height = height;

        LOG.debug("mapShow ["+width+"]["+height+"]");
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
                if (nx > width || ny > height) {
                    return ;
                }
                String[][] tmpLayer = layers.get(currentLayer);
                tmpLayer[nx][ny] = mapEditorPanel.getTileArea().getLastTileX()
                        + "-" + mapEditorPanel.getTileArea().getLastTileY();
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
                    width * 32,
                    height * 32,
                    BufferedImage.TYPE_4BYTE_ABGR);
            // 得到缓冲区的画笔
            Graphics g2 = paint.getGraphics();

            String[][] layer = layers.get(layern); // TODO 只显示一层数据
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    String s = layer[i][j];
                    if (null != s && s.contains("-")) {
                        String[] split = s.split("-");
                        int x = Integer.parseInt(split[0]);
                        int y = Integer.parseInt(split[1]);
    //                    LOG.debug("draw i/j" + i + "/" + j + ":::" + s + ",,,,x/y=" + x + "/" + y);

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

}
