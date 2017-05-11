package com.billy.rpg.mapeditor;

import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;


/**
 * main frame to show the map editor
 *
 * @since 2017-04-28 09:56:29
 */
public class MapEditorFrame extends JFrame {
    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger(MapEditorFrame.class);

    private MapEditorPanel mapEditorPanel;
    private MapSaver mapSaver;


    public static void main(String[] args) {
        new MapEditorFrame();
    }


    public MapEditorFrame() {
        mapEditorPanel = new MapEditorPanel(this);
        this.setContentPane(mapEditorPanel);

        setTitle("地图编辑器");
        setLocation(500, 100);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        mapSaver = new MapSaver(this);
        initMenuBar();

        pack();
        LOG.info("MapEditor starts");
    }

    /**
     * 初始化菜单项
     */
    private void initMenuBar() {
        JMenuBar mb;
        JMenu menuFile, menuLayer;
        JMenuItem mItemFileOpen, mItemFileSave, menuItemFileExit, menuItemLayer1, menuItemLayer2, menuItemLayer3,
                menuItemLayer4, menuItemLayer5;

        mb = new JMenuBar(); // 创建菜单栏MenuBar
        menuFile = new JMenu("File");
        menuLayer = new JMenu("Layer");

        mItemFileOpen = new JMenuItem("Open");
        mItemFileOpen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = mapEditorPanel.getFileTileChooser();
                int result = chooser.showOpenDialog(null);
                if (result == JFileChooser.APPROVE_OPTION){
                    String name = chooser.getSelectedFile().getPath();
                    mapEditorPanel.setTileImage(name);
                }
            }
        });
        menuFile.add(mItemFileOpen);
        mItemFileSave = new JMenuItem("Save");
        mItemFileSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JFileChooser chooser = mapEditorPanel.getFileMapChooser();
                int result = chooser.showSaveDialog(null);
                if (result == JFileChooser.APPROVE_OPTION){
                    File selectedFile = chooser.getSelectedFile();
                    String name = chooser.getName(selectedFile);
                    if (!name.endsWith(".map")){
                        name += ".map";
                    }
                    mapSaver.save(chooser.getCurrentDirectory() + File.separator + name);
                }
            }
        });
        menuFile.add(mItemFileSave);
        menuFile.addSeparator(); // 加入分割线
        menuItemFileExit = new JMenuItem("Exit");
        menuItemFileExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        menuFile.add(menuItemFileExit);
        mb.add(menuFile); // 菜单栏中加入“文件”菜单

        ButtonGroup layerGroup = new ButtonGroup();//设置单选组
        menuItemLayer1 = new JRadioButtonMenuItem("Layer1");
        menuItemLayer1.doClick();
        menuItemLayer1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LOG.debug("layer1 picked");
                mapEditorPanel.getMapArea().setCurrentLayer(1);
            }
        });
        menuItemLayer2 = new JRadioButtonMenuItem("Layer2");
        menuItemLayer2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LOG.debug("layer2 picked");
                mapEditorPanel.getMapArea().setCurrentLayer(2);
            }
        });
        menuItemLayer3 = new JRadioButtonMenuItem("Layer3");
        menuItemLayer3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LOG.debug("layer3 picked");
                mapEditorPanel.getMapArea().setCurrentLayer(3);
            }
        });
        menuItemLayer4 = new JRadioButtonMenuItem("Layer4(walk)");
        menuItemLayer4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LOG.debug("layer4 picked");
                mapEditorPanel.getMapArea().setCurrentLayer(4);
            }
        });
        menuItemLayer5 = new JRadioButtonMenuItem("Layer5(event)");
        menuItemLayer5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LOG.debug("layer5 picked");
                mapEditorPanel.getMapArea().setCurrentLayer(5);
            }
        });
        layerGroup.add(menuItemLayer1);
        layerGroup.add(menuItemLayer2);
        layerGroup.add(menuItemLayer3);
        layerGroup.add(menuItemLayer4);
        layerGroup.add(menuItemLayer5);
        menuLayer.add(menuItemLayer1);
        menuLayer.add(menuItemLayer2);
        menuLayer.add(menuItemLayer3);
        menuLayer.add(menuItemLayer4);
        menuLayer.addSeparator();

        mb.add(menuLayer);

        // setMenuBar:将此窗体的菜单栏设置为指定的菜单栏。
        setJMenuBar(mb);
    }


    public MapEditorPanel getMapEditorPanel() {
        return mapEditorPanel;
    }
}

