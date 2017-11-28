package billy.rpg.game.cmd;

import billy.rpg.game.GameFrame;
import billy.rpg.game.cmd.executor.CmdProcessor;
import billy.rpg.game.screen.MessageBoxScreen;

/**
 * 减少金币
 *
 * @author liulei@bshf360.com
 * @since 2017-09-05 10:53
 */
public class DecreaseMoneyCmd extends CmdBase {
    private int money;

    public DecreaseMoneyCmd(int money) {
        super("decreasemoney");
        this.money = money;
    }

    public int getMoney() {
        return money;
    }

    @Override
    public int execute(CmdProcessor cmdProcessor) {
        GameFrame.getInstance().getGameData().decreaseMoney(money);
        GameFrame.getInstance().pushScreen(new MessageBoxScreen("金币减少 " + money));
        return 0;
    }
}
