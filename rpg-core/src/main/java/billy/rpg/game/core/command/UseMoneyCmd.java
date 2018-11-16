package billy.rpg.game.core.command;

import billy.rpg.game.core.command.processor.CmdProcessor;
import billy.rpg.game.core.container.GameContainer;
import billy.rpg.game.core.screen.MessageBoxScreen;

import java.util.List;

/**
 * 减少金币
 *
 * @author liulei@bshf360.com
 * @since 2017-09-05 10:53
 */
public class UseMoneyCmd extends CmdBase {
    private int money;

    public int getMoney() {
        return money;
    }

    @Override
    public void init() {
        List<String> arguments = super.getArguments();
        money = Integer.parseInt(arguments.get(0));
    }

    @Override
    public int execute(GameContainer gameContainer, CmdProcessor cmdProcessor) {
        gameContainer.getGameData().useMoney(money);
        gameContainer.getGameFrame().pushScreen(new MessageBoxScreen("金币减少 " + money));
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
