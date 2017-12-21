package billy.rpg.game.cmd;

import billy.rpg.game.GameFrame;
import billy.rpg.game.cmd.executor.CmdProcessor;
import billy.rpg.game.screen.BaseScreen;
import billy.rpg.game.screen.ScenenameScreen;

/**
 * 显示场景名称
 * @author <a href="http://blog.sina.com.cn/valuetodays">liulei-frx</a>
 * @since 2016-05-10 20:09
 */
public class ScenenameCmd extends CmdBase {
    private String sname; // scene name

    public ScenenameCmd(String sname) {
        super("scenename");
        this.sname = sname;
    }

    @Override
    public int execute(CmdProcessor cmdProcessor) {
        BaseScreen bs = new ScenenameScreen(sname);
        GameFrame.getInstance().pushScreen(bs);
        return 0;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    @Override
    public String toString() {
        return "ScenenameCmd{" +
                "sname='" + sname + '\'' +
                "} " + super.toString();
    }
}
