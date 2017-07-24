package billy.rpg.game.cmd;

/**
 * @author <a href="http://blog.sina.com.cn/valuetodays">liulei-frx</a>
 * @date 2016-05-10 20:09
 * @since 2016-05-10 20:09
 */
public class ScenenameCmd extends CmdBase {
    private String sname; // scene name

    public ScenenameCmd(String sname) {
        super("scenename");
        this.sname = sname;
    }


    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }
}
