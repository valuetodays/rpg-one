package billy.rpg.game.cmd;

import billy.rpg.game.GameFrame;
import billy.rpg.game.cmd.executor.CmdProcessor;
import billy.rpg.game.screen.MessageBoxScreen;
import billy.rpg.resource.goods.GoodsMetaData;

/**
 * 增加物品
 *
 * @author liulei@bshf360.com
 * @since 2017-09-05 10:53
 */
public class IncreaseGoodsCmd extends CmdBase {
    private int number;

    public IncreaseGoodsCmd(int number) {
        super("increasegoods");
        this.number = number;
    }

    @Override
    public int execute(CmdProcessor cmdProcessor) {
        GameFrame.getInstance().getGameData().increaseGoods(number);
        GoodsMetaData goodsMetaData = GameFrame.getInstance().getGameContainer().getGoodsMetaDataOf(number);
        GameFrame.getInstance().pushScreen(new MessageBoxScreen("物品增加 " + goodsMetaData.getName()));
        return 0;
    }
}
