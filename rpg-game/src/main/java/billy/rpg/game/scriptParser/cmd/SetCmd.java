package billy.rpg.game.scriptParser.cmd;

/**
 * @author <a href="http://blog.sina.com.cn/valuetodays">liulei-home</a>
 * @date 2016-05-10 22:38
 * @since 2016-05-10 22:38
 */
public class SetCmd extends CmdBase {
    private String key;
    
    public SetCmd(String key) {
        super("set");
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
