package billy.rpg.game.cmd;

import billy.rpg.game.cmd.executor.CmdProcessor;

import java.util.List;

/**
 * 命令 - attr
 * @author <a href="http://blog.sina.com.cn/valuetodays">liulei-home</a>
 * @date 2016-05-10 12:31
 * @since 2016-05-10 12:31
 */
public class AttrCmd extends CmdBase {
    private int m;
    private int n;

    public int getM() {
        return m;
    }

    public int getN() {
        return n;
    }

    @Override
    public void init() {
        List<String> arguments = super.getArguments();
        m = Integer.parseInt(arguments.get(0));
        n = Integer.parseInt(arguments.get(1));
    }

    @Override
    public int execute(CmdProcessor cmdProcessor) {
        // TODO nothing?
        return 0;
    }

    @Override
    public String getUsage() {
        return "attr m n";
    }

    @Override
    public int getArgumentSize() {
        return 2;
    }

    @Override
    public String toString() {
        return "AttrCmd{" +
                "m=" + m +
                ", n=" + n +
                "} " + super.toString();
    }
}
