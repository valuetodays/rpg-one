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
public class JPanelEx extends JPanel {
    private static final Logger LOG = Logger.getLogger(JPanelEx.class);

    private MapEditorFrame mapEditorFrame;
    private MapEditorPanel mapEditorPanel;

    public JPanelEx(MapEditorFrame jFrame, MapEditorPanel mapEditorPanel) {
        this.mapEditorFrame = jFrame;
        this.mapEditorPanel = mapEditorPanel;
        setBackground(new Color(214, 31, 17));
    }

    private java.util.List<String[][]> layers;
    private int width;
    private int height;
    private int currentLayer = 0; // TODO 可变的层

    /**
     * 初始化map显示区域，
     * @param width w
     * @param height h
     */
    public void initMapShow(int width, int height) {
        String[][] layer1 = new String[MapEditorConstant.MAX_MAP_WIDTH_IN_TILE][MapEditorConstant
                .MAX_MAP_HEIGHT_IN_TILE];
        String[][] layer2 = new String[MapEditorConstant.MAX_MAP_WIDTH_IN_TILE][MapEditorConstant
                .MAX_MAP_HEIGHT_IN_TILE];
        String[][] layer3 = new String[MapEditorConstant.MAX_MAP_WIDTH_IN_TILE][MapEditorConstant
                .MAX_MAP_HEIGHT_IN_TILE];
        layers = new ArrayList<>();
        layers.add(layer1);
        layers.add(layer2);
        layers.add(layer3);
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
                tmpLayer[nx][ny] = mapEditorPanel.getLastTileX() + "-" + mapEditorPanel.getLastTileY();
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
        // 从tile中取数据将其显示在此处
        BufferedImage bufferedImage = null;

        Icon icon = mapEditorPanel.getLabelTile().getIcon();
        if (icon instanceof ImageIcon) {
//            LOG.debug(icon.getClass().getName());
            bufferedImage = ImageUtil.getBufferedImage((ImageIcon) icon);
        }

        if (bufferedImage == null) {
            LOG.debug("icon is null");
            return ;
        }

//        for (String[][] layer : layers) {
        String[][] strings = layers.get(currentLayer); // TODO 只显示一层数据
        for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    String s = strings[i][j];
                    if (null != s && s.contains("-")) {
                        String[] split = s.split("-");
                        int x = Integer.parseInt(split[0]);
                        int y = Integer.parseInt(split[1]);
    //                    LOG.debug("draw i/j" + i + "/" + j + ":::" + s + ",,,,x/y=" + x + "/" + y);

                        g.drawImage(bufferedImage,
                            i*32, j*32, i*32+32, j*32+32,
                            x*32, y*32, x*32+32, y*32+32,
                            null);
                    }
                }
            }
//        }

        // 如下代码说明可以draw()..
        //g.drawImage(icon0, 0, 0, 16, 16, null);
    }

}
