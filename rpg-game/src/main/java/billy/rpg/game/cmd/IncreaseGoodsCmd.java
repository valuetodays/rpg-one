package billy.rpg.game.cmd;

/**
 * 增加金币
 *
 * @author liulei@bshf360.com
 * @since 2017-09-05 10:53
 */
public class IncreaseGoodsCmd extends CmdBase {
    private int number;

    public IncreaseGoodsCmd(int number) {
        super("increasegoods");
        this.number = number;
    }

    public int getNumber() {
        return number;
    }
}
