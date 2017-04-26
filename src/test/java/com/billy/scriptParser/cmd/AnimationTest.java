package com.billy.scriptParser.cmd;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.Timer;

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
public class AnimationTest extends JFrame implements Runnable {
    private static final long serialVersionUID = 1L;
    final String path = "C:\\code-git\\mvn-all\\script-parser\\src\\main\\resources\\Sprites\\skill\\1";
    private List<Image> images = new ArrayList<>();
    private int currentFrameIndex;
    
    private Timer playTimer;// 播放动画用的定时器

    public AnimationTest() {

        for (int i = 1; i < 12; i++) {
            try {
                images.add(ImageIO.read(new FileInputStream(path + "/" + i + ".png")));
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }

        setTitle("测试动画");
        setLocation(400, 100);
        setPreferredSize(new Dimension(400, 400));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        
         playTimer = new Timer(150, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    showPic();
                }


         });
        
        playTimer.start();
        setVisible(true);
        pack();
    }

    public static void main(String[] args) {
        AnimationTest at = new AnimationTest();
        System.out.println(at);
//        new Thread(at).start();
    }

    private void showPic() {
        System.out.println(currentFrameIndex);
        currentFrameIndex++;
        
        if (currentFrameIndex >= 11)
            currentFrameIndex = 1;
        repaint();
    }
    
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Image image = images.get(currentFrameIndex);
        g.setColor(new Color(0, 0, 0, 0));
        g.drawImage(image, 100, 50, 400, 300, null);
    }

    @Override
    public void run() {
        while (true) {
            repaint();
            if (currentFrameIndex >= 11)
                currentFrameIndex = 1;

            try {
                Thread.sleep(40);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

}
