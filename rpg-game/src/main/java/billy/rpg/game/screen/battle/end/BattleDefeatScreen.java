package billy.rpg.game.screen.battle.end;

import billy.rpg.game.GameCanvas;
import billy.rpg.game.GameFrame;
import billy.rpg.game.constants.GameConstant;
import billy.rpg.game.screen.BaseScreen;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * 战斗失败
 *
 * @author liulei@bshf360.com
 * @since 2017-07-18 18:21
 */
public class BattleDefeatScreen extends BaseScreen {

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

        // TODO 应该加载一个背景图
        g.setColor(Color.black);
        g.fillRect(0, 0, paint.getWidth(), paint.getHeight());
        g.setColor(Color.WHITE);
        g.drawString("妖怪：菜鸡，打不过我，哈哈！", 20, 50);

        gameCanvas.drawBitmap(paint, 0, 0);
    }

    @Override
    public void onKeyDown(int key) {

    }

    @Override
    public void onKeyUp(int key) {
        GameFrame.getInstance().changeScreen(9);
    }
}
