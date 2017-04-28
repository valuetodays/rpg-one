package com.billy.rpg.mapeditor;

import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

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

    private String[][] mapShow = new String[15][15];


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
                if (nx > mapShow.length || ny > mapShow[0].length) {
                    return ;
                }
                mapShow[nx][ny] = mapEditorPanel.getLastTileX() + "-" + mapEditorPanel.getLastTileY();
                LOG.debug(" in map (x/y"+x + "/" + y +")["+nx +"," + ny +"]=" + mapShow[nx][ny]);
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

        int nnn = 0;
        int length = mapShow.length;
        for (int i = 0; i < length; i++) {
            int length1 = mapShow[i].length;
//            LOG.debug(length + "," + length1);
            for (int j = 0; j < length1; j++) {
                String s = mapShow[i][j];
//                System.out.print(s + ", ");
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

        // 如下代码说明可以draw()..
        //g.drawImage(icon0, 0, 0, 16, 16, null);
    }

}
