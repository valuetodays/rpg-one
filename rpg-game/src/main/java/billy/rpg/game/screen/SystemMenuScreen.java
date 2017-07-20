package billy.rpg.game.screen;

import billy.rpg.game.GameCanvas;
import billy.rpg.game.GameFrame;
import billy.rpg.game.constants.GameConstant;
import billy.rpg.game.util.KeyUtil;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SystemMenuScreen extends BaseScreen {

    @Override
    public void update(long delta) {
        
    }

    @Override
    public void draw(GameCanvas gameCanvas) {
        BufferedImage paint = new BufferedImage(
                GameConstant.GAME_WIDTH, 
                GameConstant.GAME_HEIGHT, 
                BufferedImage.TYPE_4BYTE_ABGR);
        Graphics g = paint.getGraphics();

        // 此处Color若是(0,0,0,0)即为透明色
        g.setColor(new Color(64, 64, 102, 128));
        g.fillRect(0, 0, GameConstant.GAME_WIDTH, GameConstant.GAME_HEIGHT);
        g.setColor(Color.WHITE);
        g.fillRoundRect(0, 0, 8, GameConstant.GAME_HEIGHT, 1, 1);
        
        g.drawRect(30, 50, 200, 45);
        g.setFont(new Font("黑体", Font.BOLD, 18));
        g.setColor(Color.WHITE);
        g.drawString("属性", 38, 70);
        g.drawString("系统设置", 10, 20);
        
        gameCanvas.drawBitmap(paint, 0, 0);
    }

    @Override
    public void onKeyDown(int key) {
       
    }

    @Override
    public void onKeyUp(int key) {
        if (KeyUtil.isEsc(key)) {
            GameFrame.getInstance().changeScreen(1);
          }
    }

}
