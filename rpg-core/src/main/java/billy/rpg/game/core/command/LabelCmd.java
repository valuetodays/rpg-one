package billy.rpg.game.core.command;


import billy.rpg.game.core.command.processor.CmdProcessor;
import billy.rpg.game.core.container.GameContainer;

/**
 * 命令 - 标签 （其下会有很多其它基础命令）
 * @author <a href="http://blog.sina.com.cn/valuetodays">liulei-home</a>
 * @since 2016-05-10 22:29
 */
public class LabelCmd extends CmdBase {

    @Override
    public void init() {
    }

    @Override
    public int execute(GameContainer gameContainer, CmdProcessor cmdProcessor) {
        return 0;
    }

    @Override
    public String getUsage() {
        return "LABELNAME:";
    }

    @Override
    public int getArgumentSize() {
        return 0;
    }
}
