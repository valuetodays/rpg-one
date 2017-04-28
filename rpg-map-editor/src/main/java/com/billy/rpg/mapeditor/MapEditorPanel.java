package com.billy.rpg.mapeditor;

import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;

public class MapEditorPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger(MapEditorPanel.class);

    AffineTransform transform = new AffineTransform();
    AffineTransformOp ato;
    private MapEditorFrame mapEditorFrame;

    public MapEditorPanel(MapEditorFrame mapEditorFrame) {
        this.mapEditorFrame = mapEditorFrame;
        setPreferredSize(new Dimension(MapEditorConstant.PANEL_WIDTH, MapEditorConstant.PANEL_HEIGHT));
        LOG.debug("new " + this.getClass().getSimpleName() + "()");
        transform.setToScale(1, 1);
        ato = new AffineTransformOp(transform, null);
        initComponents();
    }

    private JTextField tfMapName = null;
    private JTextField tfMapWidth = null;
    private JTextField tfMapHeight = null;
    private JFileChooser chooser;
    private JLabel labelTile;
    private JPanelEx panelCenter;
//    private JScrollPaneEx jspCenter;

    private void initComponents() {
        LayoutManager borderLayout = new BorderLayout(5, 5);
        setLayout(borderLayout);
        // add north start
        JPanel panelNorth = new JPanel();
        JLabel labelMapName = new JLabel("地图名");
        panelNorth.add(labelMapName);
        tfMapName = new JTextField(10);
        panelNorth.add(tfMapName);
        JLabel labelMapWidth = new JLabel("宽");
        panelNorth.add(labelMapWidth);
        tfMapWidth = new JTextField(10);
        tfMapWidth.setText("15");
        panelNorth.add(tfMapWidth);
        JLabel labelMapHeight = new JLabel("高");
        panelNorth.add(labelMapHeight);
        tfMapHeight = new JTextField(10);
        tfMapHeight.setText("15");
        panelNorth.add(tfMapHeight);
        JButton btnSave = new JButton("保存");
        panelNorth.add(btnSave);
        panelNorth.setBackground(new Color(125, 152, 189));
        bindSaveListener(btnSave); // 添加保存事件

        add(panelNorth, BorderLayout.NORTH); // add north panel to main panel
        // add north end

        // add west start
        JPanel panelWest = new JPanel();
        panelWest.setPreferredSize(new Dimension(260, 600));
        panelWest.setBackground(new Color(80, 116, 93));
        labelTile = new JLabel();
        bindTileListener();
        panelWest.add(labelTile);
        add(panelWest, BorderLayout.WEST);
        chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File("."));
        // add west end

        // add center start
        panelCenter = new JPanelEx(mapEditorFrame, this);
        panelCenter.setPreferredSize(new Dimension(500, 600));
        panelCenter.setBackground(new Color(199, 170, 90));
//        labelMap = new JLabel();
        panelCenter.bindMapListener();
//        panelCenter.add(labelMap);
        /*
        jspCenter = new JScrollPaneEx(panelCenter, mapEditorFrame, this);
        jspCenter.setPreferredSize(new Dimension(600, 600));
        jspCenter.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        jspCenter.bindMapListener();
        */
        add(panelCenter, BorderLayout.CENTER);
        // add center end
    }


    private int lastTileX;
    private int lastTileY;

    public int getLastTileX() {
        return lastTileX;
    }
    public int getLastTileY() {
        return lastTileY;
    }

    private void bindTileListener() {
        labelTile.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
            }
            @Override
            public void mouseMoved(MouseEvent e) {
            }
        });
        labelTile.addMouseListener(new MouseListener() {
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

    // 绑定保存点击事件
    private void bindSaveListener(JButton btnSave) {
        btnSave.addMouseListener( new MouseListener() {
            // 点击后鼠标移开不会触发此方法
            @Override
            public void mouseClicked(MouseEvent e) {
                String mapName = tfMapName.getText();
                LOG.debug("clicked, and get `map name="+mapName+"`.");
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

    public BufferedImage background;
    public BufferedImage dest;

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (background != null) {
            if (dest == null) {
                dest = new BufferedImage(
                        MapEditorConstant.PANEL_WIDTH,
                        MapEditorConstant.PANEL_HEIGHT,
                        BufferedImage.TYPE_4BYTE_ABGR);
            }

            ato.filter(background, dest);
            g.drawImage(dest, 0, 0, null);
        }
    }

    public JFileChooser getChooser() {
        return chooser;
    }

    public JLabel getLabelTile() {
        return labelTile;
    }

    public void setImageIcon(String image) {
        getLabelTile().setIcon(new ImageIcon(image));
//        jspCenter.repaint();
        panelCenter.repaint();
    }
}
