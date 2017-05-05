package com.billy.rpg.mapeditor;

import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 *
 * @since 2017-04-28 09:56:29
 */
public class MapEditorFrame extends JFrame {
    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger(MapEditorFrame.class);

    public MapEditorPanel mapEditorPanel;


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
        initMenuBar();

        pack();
        LOG.info("MapEditor starts");
    }

    /**
     * 初始化菜单项
     */
    private void initMenuBar() {
        MenuBar mb;
        Menu menuFile;
        MenuItem mItemOpen, mItemSave;

        mb = new MenuBar(); // 创建菜单栏MenuBar
        menuFile = new Menu("File");

        mItemOpen = new MenuItem("Open");
        mItemOpen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = mapEditorPanel.getChooser();
                int result = chooser.showOpenDialog(null);
                if (result == JFileChooser.APPROVE_OPTION){
                    String name = chooser.getSelectedFile().getPath();
                    mapEditorPanel.setImageIcon(name);
                }
            }
        });
        menuFile.add(mItemOpen);
        mItemSave = new MenuItem("Save");

        // 加入分割线
        menuFile.addSeparator();
        menuFile.add(mItemSave);
        mb.add(menuFile);
        // 菜单栏中加入“文件”菜单

        // setMenuBar:将此窗体的菜单栏设置为指定的菜单栏。
        setMenuBar(mb);
    }



}

