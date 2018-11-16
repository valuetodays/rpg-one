package billy.rpg.game.core.command;

import billy.rpg.game.core.command.processor.CmdProcessor;
import billy.rpg.game.core.container.GameContainer;
import billy.rpg.game.core.screen.BaseScreen;
import billy.rpg.game.core.screen.ScenenameScreen;

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
    public int execute(GameContainer gameContainer, CmdProcessor cmdProcessor) {
        BaseScreen bs = new ScenenameScreen(sname);
        gameContainer.getGameFrame().pushScreen(bs);
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
