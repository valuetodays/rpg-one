package billy.rpg.game.cmd;

import billy.rpg.game.cmd.executor.CmdProcessor;

/**
 * 命令 - 触发器
 * @author <a href="http://blog.sina.com.cn/valuetodays">liulei-home</a>
 * @since 2016-12-08 15:37
 */
public class TriggerCmd extends CmdBase {

    private int num; // npc number
    private String triggerName; // 触发器名称

    public TriggerCmd(int num, String triggerName) {
        super("trigger");
        this.num = num;
        this.triggerName = triggerName;
    }

    public int getNum() {
        return num;
    }
    public void setNum(int num) {
        this.num = num;
    }
    public String getTriggerName() {
        return triggerName;
    }
    public void setTriggerName(String triggerName) {
        this.triggerName = triggerName;
    }

    @Override
    public int execute(CmdProcessor cmdProcessor) {
        // TODO
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
