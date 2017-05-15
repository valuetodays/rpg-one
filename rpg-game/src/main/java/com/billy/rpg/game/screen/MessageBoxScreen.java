package com.billy.rpg.game.screen;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.billy.rpg.game.GameFrame;
import org.apache.commons.lang.StringUtils;

import com.billy.rpg.game.constants.GameConstant;
import com.billy.rpg.game.GameCanvas;

/**
 * show messagebox, and it will disappear in {@link #delay} ms, can cancel by key down
 * @author liulei-home
 * @since 2016-12-23 02:11
 *
 */
public class MessageBoxScreen extends BaseScreen {
    private String msg;
    /**
     * the last time of this messagebox
     */
    private int delay;
    private int cnt = 0;
    
    public MessageBoxScreen(String msg) {
        this(msg, 2000);
    }
    public MessageBoxScreen(String msg, int delay) {
        this.msg = msg;
        this.delay = delay;
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
        if (StringUtils.isEmpty(msg)) {
            return ;
        }
        
        // 创建一个缓冲区
        BufferedImage paint = new BufferedImage(
                GameConstant.GAME_WIDTH, 
                GameConstant.GAME_HEIGHT, 
                BufferedImage.TYPE_4BYTE_ABGR);
        // 得到缓冲区的画笔
        Graphics g = paint.getGraphics();
        
        g.setColor(new Color(64, 64, 64, 128));
        g.fillRoundRect(30, 50, 200, 45, 20, 20);
        g.setFont(new Font("黑体", Font.BOLD, 14));
        g.setColor(Color.WHITE);
        g.drawString(msg, 38, 70);
        
        gameCanvas.drawBitmap(paint, 0, 0);
    }

    @Override
    public void onKeyDown(int key) {
        GameFrame.getInstance().popScreen();
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
