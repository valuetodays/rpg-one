package billy.rpg.game.core.screen.system;

import billy.rpg.game.core.DesktopCanvas;
import billy.rpg.game.core.GameData;
import billy.rpg.game.core.callback.ConfirmCallback;
import billy.rpg.game.core.constants.GameConstant;
import billy.rpg.game.core.container.GameContainer;
import billy.rpg.game.core.screen.BaseScreen;
import billy.rpg.game.core.screen.ConfirmScreen;
import billy.rpg.game.core.util.KeyUtil;
import billy.rpg.resource.goods.GoodsMetaData;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 物品
 *
 * @author liulei@bshf360.com
 * @since 2017-09-05 11:29
 */
public class GoodsScreen extends BaseScreen {
    private SystemUIScreen systemUIScreen;
    private int goodsIndex; // 一屏显示10条物品，0~9
    private static int MAX_SHOW = 10;

    public GoodsScreen(SystemUIScreen systemScreen) {
        this.systemUIScreen = systemScreen;
    }

    @Override
    public void update(GameContainer gameContainer, long delta) {

    }

    @Override
    public void draw(GameContainer gameContainer, DesktopCanvas desktopCanvas) {
        BufferedImage paint = new BufferedImage(
                GameConstant.GAME_WIDTH,
                GameConstant.GAME_HEIGHT,
                BufferedImage.TYPE_4BYTE_ABGR);
        Graphics g = paint.getGraphics();

        GameData gameData = gameContainer.getGameData();
        g.setFont(new Font("楷体", Font.BOLD, 18));
        g.setColor(Color.YELLOW);

        java.util.List<GoodsMetaData> goodsList = gameData.getGoodsList();
        if (CollectionUtils.isNotEmpty(goodsList)) {
            int end = Math.min(MAX_SHOW, goodsList.size());
            for (int i = 0; i < end; i++) {
                GoodsMetaData goods = goodsList.get(i);
                g.drawString(goods.getName() + " (持有" + goods.getCount() + ")",
                        180,70 + i * 25);
            }

            g.drawRect(175, 50 + goodsIndex *25, 290, 25); // 当前选中物品
            g.drawRect(470, 50, 120, 400);
            List<String> formattedDesc = formatDesc(goodsList.get(goodsIndex).getDesc());
            for (int i = 0; i < formattedDesc.size(); i++) {
                String s = formattedDesc.get(i);
                g.drawString(s, 473, 70 + i * 25);
            }
        } else {
            g.drawString("暂无物品", 175, 50);
        }

        desktopCanvas.drawBitmap(gameContainer.getGameFrame(), paint, 0, 0);
    }
    private List<String> formatDesc(String desc) {
        int LENGTH = 6;
        if (StringUtils.isEmpty(desc)) {
            return Collections.emptyList();
        }
        if (desc.length() <= LENGTH) {
            return Collections.singletonList(desc);
        }
        List<String> target = new ArrayList<>();
        while (true) {
            if (desc.length() > LENGTH) {
                target.add(desc.substring(0, 5));
                desc = desc.substring(5);
            } else {
                target.add(desc);
                break;
            }
        }
        return target;
    }

    @Override
    public void onKeyDown(GameContainer gameContainer, int key) {

    }

    @Override
    public boolean isPopup() {
        return !super.isPopup();
    }

    @Override
    public void onKeyUp(GameContainer gameContainer, int key) {
        if (KeyUtil.isEsc(key)) {
            systemUIScreen.pop();
        } else if (KeyUtil.isUp(key) && goodsIndex > 0) {
            goodsIndex--;
        } else if (KeyUtil.isDown(key)) {
            GameData gameData = gameContainer.getGameData();
            java.util.List<GoodsMetaData> goodsList = gameData.getGoodsList();
            if (goodsIndex < goodsList.size() - 1) {
                goodsIndex++;
            }
        } else if (KeyUtil.isEnter(key)) {
            GameData gameData = gameContainer.getGameData();
            java.util.List<GoodsMetaData> goodsList = gameData.getGoodsList();
            if (CollectionUtils.isNotEmpty(goodsList)) {
                GoodsMetaData goodsMetaData = goodsList.get(goodsIndex);
                final BaseScreen bs = new ConfirmScreen("确定要服用【" + goodsMetaData.getName() + "】吗？",
                        systemUIScreen, new ConfirmCallback() {
                    @Override
                    public void onOK() {
                        gameContainer.getGameData().useGoods(gameContainer, goodsMetaData.getNumber(), 1);
                    }

                    @Override
                    public void onCancel() {

                    }
                });
                systemUIScreen.push(bs);
            }
        }
    }
}
