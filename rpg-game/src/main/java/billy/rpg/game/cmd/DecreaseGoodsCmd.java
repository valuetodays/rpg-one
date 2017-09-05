package billy.rpg.game.cmd;

/**
 * 减少金币
 *
 * @author liulei@bshf360.com
 * @since 2017-09-05 10:53
 */
public class DecreaseGoodsCmd extends CmdBase {
    private int number;
    private int count;

    public DecreaseGoodsCmd(int number, int count) {
        super("decreasemoney");
        this.number = number;
        this.count = count;
    }

    public int getNumber() {
        return number;
    }

    public int getCount() {
        return count;
    }
}
