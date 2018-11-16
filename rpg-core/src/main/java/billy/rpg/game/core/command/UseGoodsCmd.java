package billy.rpg.game.core.command;

import billy.rpg.game.core.command.processor.CmdProcessor;
import billy.rpg.game.core.container.GameContainer;
import billy.rpg.game.core.screen.MessageBoxScreen;
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
    public int execute(GameContainer gameContainer, CmdProcessor cmdProcessor) {
        gameContainer.getGameData().useGoods(gameContainer, number, count);
        GoodsMetaData goodsMetaData = gameContainer.getGoodsMetaDataOf(number);
        gameContainer.getGameFrame().pushScreen(
                new MessageBoxScreen("物品减少 " + goodsMetaData.getName() + " * " + count));
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
