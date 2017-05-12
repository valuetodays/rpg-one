package com.billyrupeng.screen;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.billyrupeng.GameFrame;
import org.apache.commons.lang.StringUtils;

import com.billy.constants.GameConstant;
import com.billyrupeng.GameCanvas;

/**
 * show scene name, and it will disappear in {@link #delay} ms, cannot cancel by key down
 * @author liulei-home
 * @since 2016-12-23 02:16
 *
 */
public class ScenenameScreen extends BaseScreen {
    private String name;
    /**
     * the last time of this Scenename
     */
    private int delay = 2000;
    private int cnt = 0;
    
    public ScenenameScreen(String name) {
        this.name = name;
    }

    @Override
    public void update(long delta) {
        cnt += delta;
        if (cnt > delay) {
            GameFrame.getInstance().popScreen();
        }
    }

    @Override
    public void draw(GameCanvas gameCanvas) {
        if (StringUtils.isEmpty(name)) {
            return ;
        }
        
        // 创建一个缓冲区
        BufferedImage paint = new BufferedImage(
                GameConstant.GAME_WIDTH, 
                GameConstant.GAME_HEIGHT, 
                BufferedImage.TYPE_4BYTE_ABGR);
        // 得到缓冲区的画笔
        Graphics g = paint.getGraphics();
        
        g.setColor(new Color(64, 64, 64, 5)); // the last argument indicates the transparency
        g.fillRoundRect(70, 150, 100, 30, 14, 14);
        g.setFont(new Font("黑体", Font.BOLD, 14));
        g.setColor(Color.YELLOW);
        g.drawString(name, 90, 170);
        
        gameCanvas.drawBitmap(paint, 0, 0);
    }

    @Override
    public void onKeyDown(int key) {
    }

    @Override
    public void onKeyUp(int key) {
        
    }

    @Override
    public boolean isEnd() {
        // TODO Auto-generated method stub
        return false;
    }

}
