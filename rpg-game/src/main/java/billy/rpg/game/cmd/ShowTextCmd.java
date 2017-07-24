package billy.rpg.game.cmd;

/**
 * @author <a href="http://blog.sina.com.cn/valuetodays">liulei-home</a>
 * @since 2016-05-10 22:38
 */
public class ShowTextCmd extends CmdBase {
    private String text;
    private int headNumber;

    public ShowTextCmd(int number, String text) {
        super("showtext");
        headNumber = number;
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getHeadNumber() {
        return headNumber;
    }

    public void setHeadNumber(int headNumber) {
        this.headNumber = headNumber;
    }
}
