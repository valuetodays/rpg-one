package billy.rpg.game.cmd;

import billy.rpg.game.cmd.processor.CmdProcessor;

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
    public int execute(CmdProcessor cmdProcessor) {
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
