package billy.rpg.game.core.command;

import billy.rpg.game.core.command.processor.CmdProcessor;
import billy.rpg.game.core.container.GameContainer;
import billy.rpg.game.core.screen.MessageBoxScreen;
import billy.rpg.resource.goods.GoodsMetaData;

import java.util.List;

/**
 * 增加物品
 *
 * @author liulei@bshf360.com
 * @since 2017-09-05 10:53
 */
public class AddGoodsCmd extends CmdBase {
    private int number;

    @Override
    public void init() {
        List<String> arguments = super.getArguments();
        number = Integer.parseInt(arguments.get(0));
    }

    @Override
    public int execute(GameContainer gameContainer, CmdProcessor cmdProcessor) {
        gameContainer.getGameData().addGoods(gameContainer, number);
        GoodsMetaData goodsMetaData = gameContainer.getGoodsMetaDataOf(number);
        gameContainer.getGameFrame().pushScreen(new MessageBoxScreen("物品增加 " + goodsMetaData.getName()));
        return 0;
    }

    @Override
    public String getUsage() {
        return null;
    }

    @Override
    public int getArgumentSize() {
        return 1;
    }
}
