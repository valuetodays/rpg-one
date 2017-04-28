package com.billy.rpg.mapeditor;

import org.apache.log4j.Logger;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * @author liulei
 * @date 2017-04-28 18:46
 */
public class JScrollPaneEx extends JScrollPane {
    private static final Logger LOG = Logger.getLogger(JScrollPaneEx.class);

    private MapEditorFrame mapEditorFrame;
    private MapEditorPanel mapEditorPanel;

    public JScrollPaneEx(JPanel panelCenter, MapEditorFrame jFrame, MapEditorPanel mapEditorPanel) {
        super(panelCenter);
        this.mapEditorFrame = jFrame;
        this.mapEditorPanel = mapEditorPanel;
        setBackground(new Color(214, 31, 17));
        getIcon0();
    }

    private String[][] mapShow = new String[20][20];

    public void bindMapListener() {
        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                int nx = x / 32;
                int ny = y / 32;
                mapShow[ny][nx] = mapEditorPanel.getLastTileX() + "-" + mapEditorPanel.getLastTileY();
                LOG.debug(" in map (x/y"+x + "/" + y +")["+nx +"," + ny +"]=" + mapShow[ny][nx]);
                repaint();
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

    private Image icon0;

    public void getIcon0() {
        if (icon0 != null) {
            return;
        }
        String path = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(path + "100.png");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            icon0 = ImageIO.read(fileInputStream);
            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

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

        for (int i = 0; i < mapShow.length; i++) {
            for (int j = 0; j < mapShow[i].length; j++) {
                //LOG.debug(i + "," + j + "=" + mapShow[i][j]);
            }
            //LOG.debug("");
        }

        int length = mapShow.length;
        for (int i = 0; i < length; i++) {
            int length1 = mapShow[i].length;
//            LOG.debug(length + "," + length1);
            for (int j = 0; j < length1; j++) {
                String s = mapShow[i][j];
                    System.out.print(s + ", ");
                if (null != s && s.contains("-")) {
                    String[] split = s.split("-");
                    int x = Integer.parseInt(split[0]);
                    int y = Integer.parseInt(split[1]);
//                    LOG.debug("draw i/j" + i + "/" + j + ":::" + s + ",,,,x/y=" + x + "/" + y);


                    g.drawImage(bufferedImage, x * 32, y * 32, 32, 32, null);
                }
            }
                System.out.println("");
        }
        // 如下代码说明可以draw().. TODO
        //g.drawImage(icon0, 0, 0, 16, 16, null);

    }
}
