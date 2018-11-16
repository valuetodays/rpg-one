package billy.rpg.game.core.command;

import billy.rpg.game.core.command.processor.CmdProcessor;
import billy.rpg.game.core.container.GameContainer;
import billy.rpg.game.core.screen.BaseScreen;
import billy.rpg.game.core.screen.MessageBoxScreen;

import java.util.List;

/**
 * 命令 - messagebox
 * @author <a href="http://blog.sina.com.cn/valuetodays">liulei-home</a>
 * @since 2016-12-23 01:31
 */
public class MessageBoxCmd extends CmdBase {
    private String msg;

    @Override
    public void init() {
        List<String> arguments = super.getArguments();
        msg = arguments.get(0);
    }

    @Override
    public int execute(GameContainer gameContainer, CmdProcessor cmdProcessor) {
        final BaseScreen bs = new MessageBoxScreen(msg);
        gameContainer.getGameFrame().pushScreen(bs);
        return 0;
    }

    @Override
    public String getUsage() {
        return null;
    }

    @Override
    public int getArgumentSize() {
        return 1;
    }

    public String getMsg() {
        return msg;
    }

    @Override
    public String toString() {
        return "MessageBoxCmd{" +
                "msg='" + msg + '\'' +
                "} " + super.toString();
    }
}
