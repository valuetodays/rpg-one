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
    private int posY; // 一屏显示10条物品，左右各5条  0~9
    private int posX; // 0在左侧，1在右侧    0~1
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
            end = Math.min(start+MAX_SHOW, goodsList.size());
            for (int i = start; i < end; i++) {
                GoodsMetaData goods = goodsList.get(i);
                g.drawString(goods.getName() + " (" + goods.getCount() + ")" + posY + ","+start,
                        180 + (((i-start) % 2 == 0) ? 0 : 200),
                        70 + ((i-start)/2)*50);
            }
            if (start > 0) {
                g.drawString("^", 200, 45);
            }
            if (start < goodsList.size()-MAX_SHOW) {
                g.drawString("V", 200, 320);
            }

            g.drawRect(175 + posX *200, 50 + ((posY)/2) * 50, 150, 30); // 当前选中物品
            //g.drawString(goodsList.get(posY+start).getDesc(), 175, 400);
        } else {
            g.drawString("无物品", 175, 50);
        }

        gameCanvas.drawBitmap(paint, 0, 0);
    }

    @Override
    public void onKeyDown(int key) {
        if (KeyUtil.isLeft(key)) {
            if (posY > 0) {
                posX = (posX+1) % 2;
                posY--;
            } else {
                if (start > 1) {
                    posX = (posX+1) % 2;
                    start -= 2;
                    posY++;
                }
            }
            start = Math.max(start, 0);
        } else if (KeyUtil.isRight(key)) {
            GameData gameData = GameFrame.getInstance().getGameData();
            java.util.List<GoodsMetaData> goodsList = gameData.getGoodsList();
            if (posY < MAX_SHOW-1 && ((posY + start) < goodsList.size()-1)) {
                posX = (posX+1) % 2;
                posY++;
            } else {
                if (start < goodsList.size()-MAX_SHOW) {
                    posX = (posX+1) % 2;
                    start += 2;
                    posY--;
                }
            }
            start = Math.min(start, goodsList.size()-MAX_SHOW+1);
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
