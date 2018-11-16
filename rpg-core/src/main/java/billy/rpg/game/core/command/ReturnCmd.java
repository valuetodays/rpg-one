package billy.rpg.game.core.command;

import billy.rpg.game.core.command.processor.CmdProcessor;
import billy.rpg.game.core.container.GameContainer;

/**
 * 命令 - return返回
 * @author <a href="http://blog.sina.com.cn/valuetodays">liulei-home</a>
 * @since 2016-05-10 12:31
 */
public class ReturnCmd extends CmdBase {

    @Override
    public void init() {

    }

    @Override
    public int execute(GameContainer gameContainer, CmdProcessor cmdProcessor) {
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
