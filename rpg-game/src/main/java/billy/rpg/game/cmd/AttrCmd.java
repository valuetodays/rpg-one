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

    public AttrCmd(int m, int n) {
        super("attr");
        this.m = m;
        this.n = n;
    }

    public int getM() {
        return m;
    }

    public int getN() {
        return n;
    }

    @Override
    public int execute(CmdProcessor cmdProcessor) {
        // TODO nothing?
        return 0;
    }

    @Override
    public String toString() {
        return "AttrCmd{" +
                "m=" + m +
                ", n=" + n +
                "} " + super.toString();
    }
}
