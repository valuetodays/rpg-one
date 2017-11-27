package billy.rpg.game.cmd;

/**
 * @author <a href="http://blog.sina.com.cn/valuetodays">liulei-home</a>
 * @since 2016-05-10 22:38
 */
public class ShowTextCmd extends CmdBase {
    private int headNumber;
    private int location;
    private String text;

    public ShowTextCmd(int number, int location, String text) {
        super("showtext");
        headNumber = number;
        this.location = location;
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

    @Override
    public String toString() {
        return "ShowTextCmd{" +
                "headNumber=" + headNumber +
                ", location=" + location +
                ", text='" + text + '\'' +
                "} " + super.toString();
    }

    public int getLocation() {
        return location;
    }

    public void setLocation(int location) {
        this.location = location;
    }
}
