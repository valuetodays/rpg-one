package billy.rpg.game.core.command;

import billy.rpg.game.core.command.processor.CmdProcessor;
import billy.rpg.game.core.command.processor.support.DefaultCmdProcessor;
import billy.rpg.game.core.container.GameContainer;
import billy.rpg.game.core.script.LabelBean;
import billy.rpg.game.core.script.event.EventTableDeterminer;

import java.util.List;

/**
 * 命令 - if
 * @author <a href="http://blog.sina.com.cn/valuetodays">liulei-home</a>
 * @since 2016-05-10 12:31
 */
public class IfCmd extends CmdBase {
    private String condition;
    private String triggerName;


    @Override
    public void init() {
        List<String> arguments = super.getArguments();
        condition = arguments.get(0);
        triggerName = arguments.get(1);
    }

    @Override
    public int execute(GameContainer gameContainer, CmdProcessor cmdProcessor) {
        if (EventTableDeterminer.getInstance().existsEvent(gameContainer, condition)) {
            LabelBean label = gameContainer.getLabelByTitle(triggerName);
            cmdProcessor.setInnerCmdProcessor(new DefaultCmdProcessor(label.getCmds()));
        } else {    // global event does not exist
            throw new RuntimeException("unknown event: " + condition);
        }
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
        return "IfCmd{" +
                "condition='" + condition + '\'' +
                ", triggerName='" + triggerName + '\'' +
                "} " + super.toString();
    }
}
