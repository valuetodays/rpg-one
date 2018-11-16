package billy.rpg.game.core.command;

import billy.rpg.game.core.command.processor.CmdProcessor;
import billy.rpg.game.core.container.GameContainer;
import billy.rpg.game.core.screen.BaseScreen;
import billy.rpg.game.core.screen.ShopScreen;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author lei.liu@datatist.com
 * @since 2018-09-28 11:56:12
 */
public class BuyCmd extends CmdBase {
    private List<Integer> goodsList;

    @Override
    public void init() {
        List<String> arguments = super.getArguments();
        List<Integer> list = new ArrayList<>();
        for (String argument : arguments) {
            list.add(Integer.valueOf(argument));
        }
        goodsList = Collections.unmodifiableList(list);
//        goodsList = arguments.stream().map(e -> Integer.parseInt(e)).collect(Collectors.toList());
    }

    @Override
    public int execute(GameContainer gameContainer, CmdProcessor cmdProcessor) {
        BaseScreen screen = new ShopScreen(goodsList);
        gameContainer.getGameFrame().pushScreen(screen);
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
