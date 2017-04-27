package com.billy.scriptParser.cmd;

import java.awt.Dimension;

import javax.swing.JFrame;

import com.billyrupeng.timer.AnimationTimer;

/**
 * 使用两种方式显示动画
 * 
 * <ul>
 *    <li>使用线程方式</li>
 *    <li>使用timer方式</li>
 * </ul>
 * 
 * @author ll
 * @date 2017-03-02 05:05
 */
public class AnimationTimerTest extends JFrame {
    private static final long serialVersionUID = 1L;
    
    private AnimationTimer playTimer;

    public AnimationTimerTest() {
        setTitle("测试动画");
        setLocation(400, 100);
        setPreferredSize(new Dimension(400, 400));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        playTimer = new AnimationTimer(150, 300, 200);

        playTimer.start();
        setVisible(true);
        pack();
    }

    public static void main(String[] args) {
        AnimationTimerTest at = new AnimationTimerTest();
        System.out.println(at);
//        new Thread(at).start();
    }

}



