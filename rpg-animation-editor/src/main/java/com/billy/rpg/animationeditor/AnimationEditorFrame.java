package com.billy.rpg.animationeditor;

import org.apache.log4j.Logger;

import javax.swing.*;


/**
 * main frame to show the animation editor
 *
 * @since 2017-07-07 09:46
 */
public class AnimationEditorFrame extends JFrame {
    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger(AnimationEditorFrame.class);
    private AnimationEditorPanel animationEditorPanel;


    public static void main(String[] args) {
        new AnimationEditorFrame();
    }

    public AnimationEditorFrame() {
        animationEditorPanel = new AnimationEditorPanel(this);
        this.setContentPane(animationEditorPanel);

        setTitle("动画编辑器");
        setLocation(500, 100);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        pack();
        LOG.info("MapAnimation starts");
    }

}

