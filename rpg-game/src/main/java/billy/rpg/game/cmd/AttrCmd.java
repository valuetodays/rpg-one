package billy.rpg.game.cmd;

import billy.rpg.game.cmd.executor.CmdProcessor;

/**
 * 命令 - attr
 * @author <a href="http://blog.sina.com.cn/valuetodays">liulei-home</a>
 * @date 2016-05-10 12:31
 * @since 2016-05-10 12:31
 */
public class AttrCmd extends CmdBase {

    private int m;
    private int n;

    public AttrCmd() {
        super("attr");
    }

    @Override
    public int execute(CmdProcessor cmdProcessor) {
        return 0;
    }

    public int getM() {
        return m;
    }

    public void setM(int m) {
        this.m = m;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    @Override
    public String toString() {
        return "AttrCmd{" +
                "m=" + m +
                ", n=" + n +
                "} " + super.toString();
    }
}
