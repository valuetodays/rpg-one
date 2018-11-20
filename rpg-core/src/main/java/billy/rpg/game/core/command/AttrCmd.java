package billy.rpg.game.core.command;


import billy.rpg.game.core.command.processor.CmdProcessor;
import billy.rpg.game.core.container.GameContainer;
import billy.rpg.game.core.item.ScriptItem;

import java.util.List;

/**
 * 命令 - attr
 * @author <a href="http://blog.sina.com.cn/valuetodays">liulei-home</a>
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

    /**
     * @see BattleImageCmd
     * @see TriggerCmd
     * @see ScriptItem#init(java.util.List)
     */
    @Override
    public int execute(GameContainer gameContainer, CmdProcessor cmdProcessor) {
        System.out.println(">>> " + getClass().getName() + " is used in billy.rpg.game.core.item.ScriptItem.init()");
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
