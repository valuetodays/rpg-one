package billy.rpg.game.cmd;

import billy.rpg.game.GameFrame;
import billy.rpg.game.cmd.executor.CmdProcessor;
import billy.rpg.game.resource.item.ScriptItem;
import billy.rpg.game.script.LabelBean;
import billy.rpg.game.script.variable.VariableTableDeterminer;

/**
 * 命令 - if
 * @author <a href="http://blog.sina.com.cn/valuetodays">liulei-home</a>
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


    @Override
    public int execute(CmdProcessor cmdProcessor) {
        if (VariableTableDeterminer.getInstance().existsVariable(condition)) {
            ScriptItem activeScriptItem = GameFrame.getInstance().getGameContainer().getActiveScriptItem();
            LabelBean fun = activeScriptItem.getLabelByTitle(triggerName);
            if (fun == null) {
                throw new RuntimeException("cannot find label '"+triggerName+"' in script " + activeScriptItem
                        .getScriptId());
            }
            //LabelBean fun = GlobalVirtualTables.getLabel(triggerName);
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
