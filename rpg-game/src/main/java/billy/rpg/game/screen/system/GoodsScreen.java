package billy.rpg.game.screen.system;

import billy.rpg.game.GameCanvas;
import billy.rpg.game.GameData;
import billy.rpg.game.GameFrame;
import billy.rpg.game.constants.GameConstant;
import billy.rpg.game.screen.BaseScreen;
import billy.rpg.game.util.KeyUtil;
import billy.rpg.resource.goods.GoodsMetaData;
import org.apache.commons.collections.CollectionUtils;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author liulei@bshf360.com
 * @since 2017-09-05 11:29
 */
public class GoodsScreen extends BaseScreen {
    private SystemScreen systemScreen;
    private int x; // 一屏显示10条物品，左右各5条
    private int y; // 0在左侧，1在右侧
    private static int MAX_SHOW = 10;

    private int start = 0;
    private int end = 0;

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
        if (CollectionUtils.isNotEmpty(goodsList)) {
            start = Math.min(0, x-start);
            end = Math.min(start+MAX_SHOW, goodsList.size());
            if (x > MAX_SHOW-1) { //因为x是从0开始
                start = x - (MAX_SHOW-2);
                end = Math.min(start+MAX_SHOW, goodsList.size());
            }
            for (int i = start; i < end; i++) {
                GoodsMetaData goods = goodsList.get(i);
                g.drawString(goods.getName() + " (" + goods.getCount() + ")" + x,
                        180 + (((i-start) % 2 == 0) ? 0 : 200),
                        70 + ((i-start)/2)*50);
            }

            g.drawRect(175 + y*200, 50 + ((x-start)/2) * 50, 150, 30); // 当前选中物品
            g.drawString(goodsList.get(x).getDesc(), 175, 400);
        } else {
            g.drawString("无物品", 175, 50);
        }

        gameCanvas.drawBitmap(paint, 0, 0);
    }

    @Override
    public void onKeyDown(int key) {
        if (KeyUtil.isUp(key)) {
            if (x > 1) {
                x -= 2;
            }
        } else if (KeyUtil.isDown(key)) {
            GameData gameData = GameFrame.getInstance().getGameData();
            java.util.List<GoodsMetaData> goodsList = gameData.getGoodsList();
            if (x < goodsList.size() - 2 || x > start+MAX_SHOW) {
                x += 2;
            }
        } else if (KeyUtil.isLeft(key)) {
            if (y > 0) {
                y--;
                x--;
            }
        } else if (KeyUtil.isRight(key)) {
            GameData gameData = GameFrame.getInstance().getGameData();
            java.util.List<GoodsMetaData> goodsList = gameData.getGoodsList();
            if (y < 1 && x < goodsList.size()-1) {
                y++;
                x++;
            }
        }

    }

    @Override
    public boolean isPopup() {
        return !super.isPopup();
    }

    @Override
    public void onKeyUp(int key) {
        if (KeyUtil.isEsc(key)) {
            systemScreen.pop();
        }
    }
}
