package billy.rpg.game.cmd;

import billy.rpg.game.cmd.executor.CmdProcessor;

/**
 * 命令 - return返回
 * @author <a href="http://blog.sina.com.cn/valuetodays">liulei-home</a>
 * @since 2016-05-10 12:31
 */
public class ReturnCmd extends CmdBase {

    public ReturnCmd(String name) {
        super(name);
    }

    @Override
    public int execute(CmdProcessor cmdProcessor) {
        return 0;
    }
}
