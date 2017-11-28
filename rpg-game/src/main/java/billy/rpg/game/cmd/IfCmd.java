package billy.rpg.game.cmd;

import billy.rpg.game.cmd.executor.CmdProcessor;
import billy.rpg.game.script.LabelBean;
import billy.rpg.game.virtualtable.GlobalVirtualTables;

/**
 * 命令 - if
 * @author <a href="http://blog.sina.com.cn/valuetodays">liulei-home</a>
 * @date 2016-05-10 12:31
 * @since 2016-05-10 12:31
 */
public class IfCmd extends CmdBase {
    private String condition;
    private String triggerName;


    public IfCmd(String condition, String triggerName) {
        super("if");
        this.condition = condition;
        this.triggerName = triggerName;
    }



    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getTriggerName() {
        return triggerName;
    }

    public void setTriggerName(String triggerName) {
        this.triggerName = triggerName;
    }

    @Override
    public int execute(CmdProcessor cmdProcessor) {
        if (GlobalVirtualTables.containsVariable(getCondition())) { // global variable exists
            LabelBean fun = GlobalVirtualTables.getLabel(getTriggerName());
            cmdProcessor.setInnerCmdProcessor(new CmdProcessor(fun.getCmds()));
        } else {    // global variable does not exist
            return -2;
        }
        return 0;
    }

    @Override
    public String toString() {
        return "IfCmd{" +
                "condition='" + condition + '\'' +
                ", triggerName='" + triggerName + '\'' +
                "} " + super.toString();
    }
}
