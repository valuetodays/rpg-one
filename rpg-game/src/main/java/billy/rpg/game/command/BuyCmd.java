package billy.rpg.game.command;

import billy.rpg.game.GameFrame;
import billy.rpg.game.command.processor.CmdProcessor;
import billy.rpg.game.screen.BaseScreen;
import billy.rpg.game.screen.ShopScreen;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author lei.liu@datatist.com
 * @since 2018-09-28 11:56:12
 */
public class BuyCmd extends CmdBase {
    private List<Integer> goodsList;

    @Override
    public void init() {
        List<String> arguments = super.getArguments();
        goodsList = arguments.stream().map(e -> Integer.parseInt(e)).collect(Collectors.toList());
    }

    @Override
    public int execute(CmdProcessor cmdProcessor) {
        BaseScreen screen = new ShopScreen(goodsList);
        GameFrame.getInstance().pushScreen(screen);
        return 0;
    }

    @Override
    public String getUsage() {
        return "buy m m m m m m m m";
    }

    @Override
    public int getArgumentSize() {
        return ARGUMENT_COUNT_GE_ZERO;
    }
}
