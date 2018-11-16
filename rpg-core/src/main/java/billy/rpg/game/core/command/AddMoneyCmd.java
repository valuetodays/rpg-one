package billy.rpg.game.core.command;

import billy.rpg.game.core.command.processor.CmdProcessor;
import billy.rpg.game.core.container.GameContainer;
import billy.rpg.game.core.screen.MessageBoxScreen;

import java.util.List;

/**
 * 增加金币
 *
 * @author liulei@bshf360.com
 * @since 2017-09-05 10:53
 */
public class AddMoneyCmd extends CmdBase {
    private int money;

    @Override
    public void init() {
        List<String> arguments = super.getArguments();
        money = Integer.parseInt(arguments.get(0));
    }

    @Override
    public int execute(GameContainer gameContainer, CmdProcessor cmdProcessor) {
        gameContainer.getGameData().addMoney(money);
        gameContainer.getGameFrame().pushScreen(new MessageBoxScreen("金币增加 " + money));
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
