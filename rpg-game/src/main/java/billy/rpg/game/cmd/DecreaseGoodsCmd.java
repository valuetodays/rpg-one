package billy.rpg.game.cmd;

import billy.rpg.game.GameFrame;
import billy.rpg.game.cmd.executor.CmdProcessor;
import billy.rpg.game.screen.MessageBoxScreen;
import billy.rpg.resource.goods.GoodsMetaData;

/**
 * 减少金币
 *
 * @author liulei@bshf360.com
 * @since 2017-09-05 10:53
 */
public class DecreaseGoodsCmd extends CmdBase {
    private int number;
    private int count;

    public DecreaseGoodsCmd(int number, int count) {
        super("decreasemoney");
        this.number = number;
        this.count = count;
    }

    public int getNumber() {
        return number;
    }

    public int getCount() {
        return count;
    }

    @Override
    public int execute(CmdProcessor cmdProcessor) {
        GameFrame.getInstance().getGameData().decreaseGoods(number, count);
        GoodsMetaData goodsMetaData = GameFrame.getInstance().getGameContainer().getGoodsMetaDataOf(number);
        GameFrame.getInstance().pushScreen(new MessageBoxScreen("物品减少 " + goodsMetaData.getName() + " * " +
                count));
        return 0;
    }
}
