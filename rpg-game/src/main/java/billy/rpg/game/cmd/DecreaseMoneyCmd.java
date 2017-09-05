package billy.rpg.game.cmd;

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
}
