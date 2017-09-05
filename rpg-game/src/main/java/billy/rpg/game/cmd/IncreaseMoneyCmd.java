package billy.rpg.game.cmd;

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
}
