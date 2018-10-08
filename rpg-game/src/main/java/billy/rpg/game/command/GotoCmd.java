package billy.rpg.game.command;

import billy.rpg.game.GameFrame;
import billy.rpg.game.command.processor.CmdProcessor;
import billy.rpg.game.command.processor.DefaultCmdProcessor;
import billy.rpg.game.resource.item.ScriptItem;
import billy.rpg.game.script.LabelBean;

import java.util.List;

/**
 * @author lei.liu@datatist.com
 * @since 2018-09-29 14:51:22
 */
public class GotoCmd extends CmdBase {
    private String labelName;

    @Override
    public void init() {
        List<String> arguments = super.getArguments();
        labelName = arguments.get(0);
    }

    @Override
    public int execute(CmdProcessor cmdProcessor) {
        ScriptItem activeScriptItem = GameFrame.getInstance().getGameContainer().getActiveScriptItem();
        LabelBean label = activeScriptItem.getLabelByTitle(labelName);
        CmdProcessor cmdProcessor0 = new DefaultCmdProcessor(label.getCmds());
        GameFrame.getInstance().getGameContainer().getActiveScriptItem().setCmdProcessor(cmdProcessor0);
        return 0;
    }

    @Override
    public String getUsage() {
        return "goto xxx";
    }

    @Override
    public int getArgumentSize() {
        return 1;
    }
}
