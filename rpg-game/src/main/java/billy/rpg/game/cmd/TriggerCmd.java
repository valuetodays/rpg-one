package billy.rpg.game.cmd;

import billy.rpg.game.cmd.executor.CmdProcessor;

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

    @Override
    public int execute(CmdProcessor cmdProcessor) {
        // TODO
        return 0;
    }

    @Override
    public String getUsage() {
        return null;
    }

    @Override
    public int getArgumentSize() {
        return 0;
    }

    @Override
    public String toString() {
        return "TriggerCmd{" +
                "num=" + num +
                ", triggerName='" + triggerName + '\'' +
                "} " + super.toString();
    }
}
