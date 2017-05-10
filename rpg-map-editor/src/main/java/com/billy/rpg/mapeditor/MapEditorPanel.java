package com.billy.rpg.mapeditor;

import org.apache.log4j.Logger;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * main panel to show the map editor
 */
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
    private JFileChooser fileTileChooser;
    private JFileChooser fileMapChooser;
    private TileAreaPanel tileArea;
    private MapAreaPanel mapArea;
    private JScrollPane jspCenter;
    private String mapName = "百草地";

    private void initComponents() {
        LayoutManager borderLayout = new BorderLayout(5, 5);
        setLayout(borderLayout);
        // add north start
        JPanel panelNorth = new JPanel();
        JLabel labelMapName = new JLabel("地图名");
        panelNorth.add(labelMapName);
        tfMapName = new JTextField(10);
        tfMapName.setText(mapName);
        mapName = tfMapName.getText();
        panelNorth.add(tfMapName);
        JLabel labelMapWidth = new JLabel("宽");
        panelNorth.add(labelMapWidth);
        tfMapWidth = new JTextField(10);
        tfMapWidth.setText("20");
        panelNorth.add(tfMapWidth);
        JLabel labelMapHeight = new JLabel("高");
        panelNorth.add(labelMapHeight);
        tfMapHeight = new JTextField(10);
        tfMapHeight.setText("15");
        panelNorth.add(tfMapHeight);
        JButton btnApply = new JButton("应用");
        panelNorth.add(btnApply);
        panelNorth.setBackground(new Color(79, 189, 58));
        bindApplyListener(btnApply); // 添加应用事件，此时将JPanelEx的mapShow[][]应用上

        add(panelNorth, BorderLayout.NORTH); // add north panel to main panel
        // add north end

        // add west start
        tileArea = new TileAreaPanel(this);
        tileArea.setPreferredSize(new Dimension(260, 600));
        tileArea.setBackground(new Color(80, 116, 93));
        tileArea.bindTileListener();
        add(tileArea, BorderLayout.WEST);
        fileTileChooser = new JFileChooser();
        fileTileChooser.setCurrentDirectory(new File("."));
        fileMapChooser = new JFileChooser();
        fileMapChooser.setCurrentDirectory(new File("."));
        FileFilter filter = new FileNameExtensionFilter( "map file", "map");
        fileMapChooser.setFileFilter(filter);//设置文件后缀过滤器

        // add west end

        // add center start
        mapArea = new MapAreaPanel(this);
        mapArea.setPreferredSize(new Dimension(1500, 1200));
        mapArea.setBackground(new Color(199, 170, 90));
        mapArea.bindMapListener();

        jspCenter = new JScrollPane(mapArea);
        jspCenter.setPreferredSize(new Dimension(600, 600));
        jspCenter.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
//        jspCenter.bindMapListener();

        add(jspCenter, BorderLayout.CENTER);
        // add center end
    }

    private void bindApplyListener(JButton btnApply) {
        btnApply.addMouseListener( new MouseListener() {
            // 点击后鼠标移开不会触发此方法
            @Override
            public void mouseClicked(MouseEvent e) {
                int newheight = Integer.parseInt(tfMapHeight.getText());
                int newwidth = Integer.parseInt(tfMapWidth.getText());
                mapArea.initMapShow(newwidth, newheight);
                mapName = tfMapName.getText();
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

    public JFileChooser getFileTileChooser() {
        return fileTileChooser;
    }
    public JFileChooser getFileMapChooser() {
        return fileMapChooser;
    }

    public void setTileImage(String imagePath) {
        // 加载Tile图片
        getTileArea().initTileImage(imagePath);
//        mapAreaPanelEx.repaint();
    }

    public TileAreaPanel getTileArea() {
        return tileArea;
    }
    public MapAreaPanel getMapArea() {
        return mapArea;
    }

    public String getMapName() {
        return mapName;
    }
}
