package billy.rpg.game.core.command;

import billy.rpg.game.core.command.processor.CmdProcessor;
import billy.rpg.game.core.command.processor.support.DefaultCmdProcessor;
import billy.rpg.game.core.container.GameContainer;
import billy.rpg.game.core.item.ScriptItem;
import billy.rpg.game.core.script.LabelBean;

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
    public int execute(GameContainer gameContainer, CmdProcessor cmdProcessor) {
        ScriptItem activeScriptItem = gameContainer.getActiveScriptItem();
        LabelBean label = activeScriptItem.getLabelByTitle(labelName);
        CmdProcessor cmdProcessor0 = new DefaultCmdProcessor(label.getCmds());
        gameContainer.getActiveScriptItem().setCmdProcessor(cmdProcessor0);
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
