package billy.rpg.game.core.command;

import billy.rpg.game.core.command.processor.CmdProcessor;
import billy.rpg.game.core.command.processor.support.DefaultCmdProcessor;
import billy.rpg.game.core.container.GameContainer;
import billy.rpg.game.core.script.LabelBean;
import billy.rpg.game.core.script.variable.VariableDeterminer;

import java.util.List;

/**
 * @author lei.liu@datatist.com
 * @since 2018-12-10 14:14:32
 */
public class IfVarCmd extends CmdBase {
    private String key;
    private int value;
    private String labelName;

    @Override
    public void init() {
        List<String> arguments = super.getArguments();
        key = arguments.get(0);
        value = Integer.parseInt(arguments.get(1));
        labelName = arguments.get(2);
    }

    @Override
    public int execute(GameContainer gameContainer, CmdProcessor cmdProcessor) {
        Integer integer = VariableDeterminer.getInstance().get(key);
        if (null == integer) {
            integer = 0;
        }
        if (integer == value) {
            LabelBean label = gameContainer.getLabelByTitle(labelName);
            cmdProcessor.setInnerCmdProcessor(new DefaultCmdProcessor(label.getCmds()));
        }

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
}
