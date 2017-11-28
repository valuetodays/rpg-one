package billy.rpg.game.cmd;

import billy.rpg.game.GameFrame;
import billy.rpg.game.cmd.executor.CmdProcessor;
import billy.rpg.game.screen.MessageBoxScreen;

/**
 * 增加金币
 *
 * @author liulei@bshf360.com
 * @since 2017-09-05 10:53
 */
public class IncreaseMoneyCmd extends CmdBase {
    private int money;

    public IncreaseMoneyCmd(int money) {
        super("increasemoney");
        this.money = money;
    }

    public int getMoney() {
        return money;
    }

    @Override
    public int execute(CmdProcessor cmdProcessor) {
        GameFrame.getInstance().getGameData().increaseMoney(money);
        GameFrame.getInstance().pushScreen(new MessageBoxScreen("金币增加 " + money));
        return 0;
    }
}
