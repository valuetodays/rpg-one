package billy.rpg.game.cmd;

import billy.rpg.game.GameFrame;
import billy.rpg.game.cmd.executor.CmdProcessor;
import billy.rpg.game.screen.MessageBoxScreen;
import billy.rpg.resource.goods.GoodsMetaData;

import java.util.List;

/**
 * 减少物品
 *
 * @author liulei@bshf360.com
 * @since 2017-09-05 10:53
 */
public class UseGoodsCmd extends CmdBase {
    private int number;
    private int count;

    public int getNumber() {
        return number;
    }

    @Override
    public void init() {
        List<String> arguments = super.getArguments();
        number = Integer.parseInt(arguments.get(0));
        count = Integer.parseInt(arguments.get(1));
    }

    @Override
    public int execute(CmdProcessor cmdProcessor) {
        GameFrame.getInstance().getGameData().useGoods(number, count);
        GoodsMetaData goodsMetaData = GameFrame.getInstance().getGameContainer().getGoodsMetaDataOf(number);
        GameFrame.getInstance().pushScreen(new MessageBoxScreen("物品减少 " + goodsMetaData.getName() + " * " +
                count));
        return 0;
    }

    @Override
    public String getUsage() {
        return null;
    }

    @Override
    public int getArgumentSize() {
        return 2;
    }
}
