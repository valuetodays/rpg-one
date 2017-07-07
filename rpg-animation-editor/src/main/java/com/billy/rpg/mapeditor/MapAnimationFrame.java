package com.billy.rpg.mapeditor;

import org.apache.log4j.Logger;

import javax.swing.*;


/**
 * main frame to show the animation editor
 *
 * @since 2017-07-07 09:46
 */
public class MapAnimationFrame extends JFrame {
    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger(MapAnimationFrame.class);


    public static void main(String[] args) {
        new MapAnimationFrame();
    }

    public MapAnimationFrame() {
        setTitle("动画编辑器");
        setLocation(500, 100);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);


        pack();
        LOG.info("MapAnimation starts");
    }



}

