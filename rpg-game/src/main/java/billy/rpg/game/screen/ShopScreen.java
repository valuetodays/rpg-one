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
 * @author lei.liu@datatist.com
 * @since 2018-09-28 12:38:35
 */
public class ShopScreen extends BaseScreen {
    private List<Integer> goodsIdList;

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
    }
}
