package billy.rpg.game.cmd;

/**
 * 命令 - messagebox
 * @author <a href="http://blog.sina.com.cn/valuetodays">liulei-home</a>
 * @date 2016-12-23 01:31
 * @since 2016-12-23 01:31
 */
public class MessageBoxCmd extends CmdBase {
    private String msg;


    public MessageBoxCmd(String msg) {
        super("messagebox");
        this.msg = msg;
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
