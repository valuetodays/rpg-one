package billy.rpg.game.cmd;

import billy.rpg.game.GameFrame;
import billy.rpg.game.cmd.processor.CmdProcessor;
import billy.rpg.game.screen.BaseScreen;
import billy.rpg.game.screen.ScenenameScreen;

import java.util.List;

/**
 * 显示场景名称
 * @author <a href="http://blog.sina.com.cn/valuetodays">liulei-frx</a>
 * @since 2016-05-10 20:09
 */
public class ScenenameCmd extends CmdBase {
    private String sname; // scene name

    @Override
    public void init() {
        List<String> arguments = super.getArguments();
        sname = arguments.get(0);
    }

    @Override
    public int execute(CmdProcessor cmdProcessor) {
        BaseScreen bs = new ScenenameScreen(sname);
        GameFrame.getInstance().pushScreen(bs);
        return 0;
    }

    @Override
    public String getUsage() {
        return null;
    }

    @Override
    public int getArgumentSize() {
        return 1;
    }

    public String getSname() {
        return sname;
    }


    @Override
    public String toString() {
        return "ScenenameCmd{" +
                "sname='" + sname + '\'' +
                "} " + super.toString();
    }
}
