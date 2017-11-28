package billy.rpg.game.cmd;


import billy.rpg.game.cmd.executor.CmdProcessor;

/**
 * 命令 - 标签 （其下会有很多其它基础命令）
 * @author <a href="http://blog.sina.com.cn/valuetodays">liulei-home</a>
 * @date 2016-05-10 22:29
 * @since 2016-05-10 22:29
 */
public class LabelCmd extends CmdBase {

    public LabelCmd(String name) {
        super(name);
    }

    @Override
    public int execute(CmdProcessor cmdProcessor) {
        return 0;
    }
}
