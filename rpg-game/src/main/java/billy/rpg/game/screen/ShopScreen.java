package billy.rpg.game.screen;

import billy.rpg.game.GameCanvas;
import billy.rpg.game.GameFrame;
import billy.rpg.game.constants.GameConstant;
import billy.rpg.game.util.KeyUtil;
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
    public void update(long delta) {

    }

    @Override
    public void draw(GameCanvas gameCanvas) {
        String s = goodsIdList.toString();

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
        g.drawString("您要买什么", 240, 70);
        g.drawString(s, 240, 90);
        for (int i = 0; i < goodsIdList.size(); i++) {
            Integer goodsId = goodsIdList.get(i);
            GoodsMetaData goodsMetaData = GameFrame.getInstance().getGameContainer().getGoodsMetaDataOf(goodsId);
            g.drawString(goodsMetaData.getName(), 200, 90 + i * 20);
        }
        g.drawString("->", 180, 90 + goodsIndex*20);
        gameCanvas.drawBitmap(paint, 0, 0);
    }

    @Override
    public void onKeyDown(int key) {

    }

    @Override
    public void onKeyUp(int key) {
        if (KeyUtil.isEsc(key)) {
            GameFrame.getInstance().popScreen();
        }
        if (KeyUtil.isUp(key) && goodsIndex > 0) {
            goodsIndex--;
        } else if (KeyUtil.isDown(key) && goodsIndex < goodsIdList.size()-1) {
            goodsIndex++;
        } else if (KeyUtil.isEnter(key)) {
            Integer goodsId = goodsIdList.get(goodsIndex);
            GoodsMetaData goodsMetaData = GameFrame.getInstance().getGameContainer().getGoodsMetaDataOf(goodsId);
            int buy = goodsMetaData.getBuy();
            int money = GameFrame.getInstance().getGameData().getMoney();
            if (money < buy) {
                GameFrame.getInstance().pushScreen(new MessageBoxScreen("你的金钱不足以购买 【"+goodsMetaData.getName()+"】"));
            } else {
                GameFrame.getInstance().getGameData().useMoney(buy);
                GameFrame.getInstance().getGameData().addGoods(goodsMetaData.getNumber());
            }
        }
    }
}
