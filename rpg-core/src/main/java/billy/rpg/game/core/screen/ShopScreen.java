package billy.rpg.game.core.screen;

import billy.rpg.game.core.DesktopCanvas;
import billy.rpg.game.core.constants.GameConstant;
import billy.rpg.game.core.container.GameContainer;
import billy.rpg.game.core.util.KeyUtil;
import billy.rpg.resource.goods.GoodsMetaData;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

/**
 * 商店 buy
 * @author lei.liu@datatist.com
 * @since 2018-09-28 12:38:35
 */
public class ShopScreen extends BaseScreen {
    private List<Integer> goodsIdList;
    private int goodsIndex;

    public ShopScreen(List<Integer> goodsIdList) {
        this.goodsIdList = goodsIdList;
    }

    @Override
    public void update(GameContainer gameContainer, long delta) {

    }

    @Override
    public void draw(GameContainer gameContainer, DesktopCanvas desktopCanvas) {
        // 创建一个缓冲区
        BufferedImage paint = new BufferedImage(
                GameConstant.GAME_WIDTH,
                GameConstant.GAME_HEIGHT,
                BufferedImage.TYPE_4BYTE_ABGR);
        // 得到缓冲区的画笔
        Graphics g = paint.getGraphics();

        g.setColor(new Color(64, 64, 64, 5)); // the last argument indicates the transparency
        g.fillRoundRect(80, 60, 480, 360, 14, 14);
        g.setFont(new Font("黑体", Font.BOLD, 14));
        g.setColor(Color.YELLOW);
        g.drawString("您要买什么？【银两："+gameContainer.getGameData().getMoney() +"】", 240, 70);
        for (int i = 0; i < goodsIdList.size(); i++) {
            Integer goodsId = goodsIdList.get(i);
            GoodsMetaData goodsMetaData = gameContainer.getGoodsMetaDataOf(goodsId);
            g.drawString(goodsMetaData.getName(), 200, 90 + i * 20);
        }
        g.drawString("->", 180, 90 + goodsIndex*20);
        desktopCanvas.drawBitmap(gameContainer.getGameFrame(), paint, 0, 0);
    }

    @Override
    public void onKeyDown(GameContainer gameContainer, int key) {

    }

    @Override
    public void onKeyUp(GameContainer gameContainer, int key) {
        if (KeyUtil.isEsc(key)) {
            gameContainer.getGameFrame().popScreen();
        }
        if (KeyUtil.isUp(key) && goodsIndex > 0) {
            goodsIndex--;
        } else if (KeyUtil.isDown(key) && goodsIndex < goodsIdList.size()-1) {
            goodsIndex++;
        } else if (KeyUtil.isEnter(key)) {
            Integer goodsId = goodsIdList.get(goodsIndex);
            GoodsMetaData goodsMetaData = gameContainer.getGoodsMetaDataOf(goodsId);
            int buy = goodsMetaData.getBuy();
            int money = gameContainer.getGameData().getMoney();
            if (money < buy) {
                gameContainer.getGameFrame().pushScreen(new MessageBoxScreen("你的金钱不足以购买 【"+goodsMetaData.getName()+"】"));
            } else {
                gameContainer.getGameData().useMoney(buy);
                gameContainer.getGameData().addGoods(gameContainer, goodsMetaData.getNumber());
            }
        }
    }
}
