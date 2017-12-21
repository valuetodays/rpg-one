package billy.rpg.game.cmd;

import billy.rpg.game.GameFrame;
import billy.rpg.game.cmd.executor.CmdProcessor;
import billy.rpg.game.screen.BaseScreen;
import billy.rpg.game.screen.MessageBoxScreen;

/**
 * 命令 - messagebox
 * @author <a href="http://blog.sina.com.cn/valuetodays">liulei-home</a>
 * @since 2016-12-23 01:31
 */
public class MessageBoxCmd extends CmdBase {
    private String msg;


    public MessageBoxCmd(String msg) {
        super("messagebox");
        this.msg = msg;
    }

    @Override
    public int execute(CmdProcessor cmdProcessor) {
        final BaseScreen bs = new MessageBoxScreen(msg);
        GameFrame.getInstance().pushScreen(bs);
        return 0;
    }

    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "MessageBoxCmd{" +
                "msg='" + msg + '\'' +
                "} " + super.toString();
    }
}
