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
public class MapEditorFrame extends JFrame implements Runnable {
    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger(MapEditorFrame.class);
    private boolean running = false;
    public MapEditorPanel mapEditorPanel;


    public static void main(String[] args) {
        MapEditorFrame m = new MapEditorFrame();

        new Thread(m, "map-editor").start();
    }


    public MapEditorFrame() {
        mapEditorPanel = new MapEditorPanel(this);
        this.setContentPane(mapEditorPanel);
//        this.add(mapEditorPanel);

        setTitle("地图编辑器");
        setLocation(500, 100);
//        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        initMenuBar();
//        setResizable(false);
//        setAlwaysOnTop(true);
//        addListener();//键盘监听
        running = true;
        pack();
        LOG.info("MapEditor starts");
    }

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

    @Override
    public void run() {
        long curTime = System.currentTimeMillis();
        long lastTime = curTime;
        int i = 0;

//        while (running) {
//
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
    }
/*

    public void addListener() {
        this.addKeyListener(new KeyListener() {//键盘监听
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                int key = c(e.getKeyCode());
//                synchronized (screenStack) {
                screenStack.peek().onKeyUp(key);
//                }
            }
            @Override
            public void keyPressed(KeyEvent e) {
                int key = c(e.getKeyCode());
//                synchronized (screenStack) {
                screenStack.peek().onKeyDown(key);
//                }
            }

            int c(int c){
                return c;
            }
        });
    }

*/



}

