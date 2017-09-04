package billy.rpg.game.screen.system;

import billy.rpg.game.GameCanvas;
import billy.rpg.game.GameFrame;
import billy.rpg.game.constants.GameConstant;
import billy.rpg.game.screen.BaseScreen;
import billy.rpg.game.util.KeyUtil;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author liulei@bshf360.com
 * @since 2017-09-04 11:04
 */
public class MenuScreen extends BaseScreen {
    private final SystemScreen systemScreen;
    private int menuPosX = 1; // 1,2,3,4

    public MenuScreen(SystemScreen systemScreen) {
        this.systemScreen = systemScreen;
    }

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
        //g.fillRoundRect(0, 0, 8, GameConstant.GAME_HEIGHT, 1, 1);

        g.setColor(Color.YELLOW);
        g.drawRect(25, 50 + (menuPosX - 1) * 30, 70, 30);
        g.setFont(new Font("黑体", Font.BOLD, 18));
        g.setColor(Color.WHITE);
        g.drawString("属性", 30, 70);
        g.drawString("物品", 30, 100);
        g.drawString("技能", 30, 130);
        g.drawString("系统", 30, 160);

        gameCanvas.drawBitmap(paint, 0, 0);
    }

    @Override
    public void onKeyDown(int key) {
        if (KeyUtil.isUp(key)) {
            if (menuPosX > 1) {
                menuPosX--;
            }
        } else if (KeyUtil.isDown(key)) {
            if (menuPosX < 4) {
                menuPosX++;
            }
        }
    }

    @Override
    public void onKeyUp(int key) {
        if (KeyUtil.isEsc(key)) {
            GameFrame.getInstance().changeScreen(1);
        } else if (KeyUtil.isEnter(key)) {
            switch (menuPosX) {
                case 1:
                    systemScreen.push(new AttributeScreen(systemScreen));
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
            }

        } // end of KeyUtil.isEnter()
    }

}
