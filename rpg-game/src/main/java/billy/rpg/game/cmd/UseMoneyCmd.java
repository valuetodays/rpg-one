package billy.rpg.game.cmd;

import billy.rpg.game.GameFrame;
import billy.rpg.game.cmd.executor.CmdProcessor;
import billy.rpg.game.screen.MessageBoxScreen;

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
    public int execute(CmdProcessor cmdProcessor) {
        GameFrame.getInstance().getGameData().useMoney(money);
        GameFrame.getInstance().pushScreen(new MessageBoxScreen("金币减少 " + money));
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
