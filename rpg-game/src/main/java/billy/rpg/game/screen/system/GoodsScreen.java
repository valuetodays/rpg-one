package billy.rpg.game.screen.system;

import billy.rpg.game.GameCanvas;
import billy.rpg.game.GameData;
import billy.rpg.game.GameFrame;
import billy.rpg.game.constants.GameConstant;
import billy.rpg.game.screen.BaseScreen;
import billy.rpg.game.util.KeyUtil;
import billy.rpg.resource.goods.GoodsMetaData;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author liulei@bshf360.com
 * @since 2017-09-05 11:29
 */
public class GoodsScreen extends BaseScreen {
    private SystemScreen systemScreen;
    private int x; // 一屏显示10条物品，左右各5条
    private int offset;

    public GoodsScreen(SystemScreen systemScreen) {
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

        GameData gameData = GameFrame.getInstance().getGameData();
        g.setFont(new Font("楷体", Font.BOLD, 18));
        g.setColor(Color.YELLOW);

        java.util.List<GoodsMetaData> goodsList = gameData.getGoodsList();
        for (int i = 0; i < goodsList.size(); i++) {
            GoodsMetaData goods = goodsList.get(i);
            g.drawString(goods.getName() + " (" + goods.getCount() + ")",
                    180 + ((i % 2 == 0) ? 0 : 100),
                    70 + i*70);
        }

        g.drawRect(175, 50 + x * 70, 150, 30);
        g.drawString(goodsList.get(x).getDesc(), 175, 400);
        gameCanvas.drawBitmap(paint, 0, 0);
    }

    @Override
    public void onKeyDown(int key) {
        if (KeyUtil.isUp(key)) {
            if (x > 1) {
                x--;
            }
        } else if (KeyUtil.isDown(key)) {
            GameData gameData = GameFrame.getInstance().getGameData();
            java.util.List<GoodsMetaData> goodsList = gameData.getGoodsList();
            if (x < goodsList.size()) {
                x++;
            }
        }
    }

    @Override
    public void onKeyUp(int key) {
        if (KeyUtil.isEsc(key)) {
            systemScreen.pop();
        }
    }
}
