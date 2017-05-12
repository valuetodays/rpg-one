package com.billyrupeng.timer;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.Timer;

import com.billy.constants.GameConstant;
import com.billyrupeng.GameCanvas;
import com.billyrupeng.GameFrame;

public class AnimationTimer {
    

//    final String path = "C:\\code-git\\mvn-all\\script-parser\\src\\main\\resources\\Sprites\\skill\\1";
    final String path = "C:\\code-git\\mvn-all\\script-parser\\src\\main\\resources\\Sprites\\skill\\2";
    // 播放动画用的定时器
    private Timer timer;
    private List<Image> images = new ArrayList<>();
    private int currentFrameIndex;
    final int posX;
    final int posY;
    
    public AnimationTimer(int ms, int posX, int posY) {
        
        for (int i = 1; i < 3; i++) {
            try {
                images.add(ImageIO.read(new FileInputStream(path + "/" + i + ".png")));
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        this.posX = posX;
        this.posY = posY;
        
        timer = new Timer(ms, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                draw();
            }
        });
        
    }
    
    public void start() { timer.start(); }
    public void stop () { timer.stop(); }
    
    public void draw() {
        GameCanvas gameCanvas = GameFrame.getInstance().getGameCanvas();
        Image image = images.get(currentFrameIndex);
        
        BufferedImage paint = new BufferedImage(
                GameConstant.GAME_WIDTH, 
                GameConstant.GAME_HEIGHT, 
                BufferedImage.TYPE_4BYTE_ABGR);
        
        Graphics g = paint.getGraphics();
        g.setColor(new Color(0, 0, 0, 0));
        g.drawImage(image, posY, posY, image.getWidth(null), image.getHeight(null), null);
        
        gameCanvas.drawBitmap(paint, 0, 0);
        currentFrameIndex++;
        if (currentFrameIndex > images.size()) {
            currentFrameIndex = 0;
        }
    }

    
}
