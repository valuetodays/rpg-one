package billy.rpg.game.core.command;


import billy.rpg.game.core.command.processor.CmdProcessor;
import billy.rpg.game.core.container.GameContainer;
import billy.rpg.game.core.item.ScriptItem;

import java.util.List;

/**
 * 命令 - 触发器
 * @author <a href="http://blog.sina.com.cn/valuetodays">liulei-home</a>
 * @since 2016-12-08 15:37
 */
public class TriggerCmd extends CmdBase {
    private int num; // npc number
    private String triggerName; // 触发器名称，不区分大小写


    public int getNum() {
        return num;
    }

    public String getTriggerName() {
        return triggerName;
    }

    @Override
    public void init() {
        List<String> arguments = super.getArguments();
        num = Integer.parseInt(arguments.get(0));
        triggerName = arguments.get(1);
    }

    /**
     * @see ScriptItem#init(java.util.List)
     */
    @Override
    public int execute(GameContainer gameContainer, CmdProcessor cmdProcessor) {
        System.out.println(">>> " + getClass().getName() + " is used in billy.rpg.game.core.item.ScriptItem.init()");
        return 0;
    }

    @Override
    public String getUsage() {
        return null;
    }

    @Override
    public int getArgumentSize() {
        return 2;
    }

    @Override
    public String toString() {
        return "TriggerCmd{" +
                "num=" + num +
                ", triggerName='" + triggerName + '\'' +
                "} " + super.toString();
    }
}
