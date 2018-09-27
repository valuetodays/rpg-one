package billy.rpg.game.cmd;

import billy.rpg.game.GameFrame;
import billy.rpg.game.cmd.executor.CmdProcessor;
import billy.rpg.game.screen.AnimationScreen;
import billy.rpg.game.screen.BaseScreen;

import java.util.List;

/**
 * 动画命令
 */
public class AnimationCmd extends CmdBase {
    private int number;
    private int x;
    private int y;

    @Override
    public void init() {
        List<String> arguments = super.getArguments();
        number = Integer.parseInt(arguments.get(0));
        x = Integer.parseInt(arguments.get(1));
        y = Integer.parseInt(arguments.get(2));
    }

    @Override
    public int execute(CmdProcessor cmdProcessor) {
        BaseScreen as = new AnimationScreen(number, x, y, GameFrame.getInstance().getGameContainer().getMapScreen());
        GameFrame.getInstance().pushScreen(as);
        return 0;
    }

    @Override
    public String getUsage() {
        return "animation number x y";
    }

    @Override
    public int getArgumentSize() {
        return 3;
    }

    @Override
    public String toString() {
        return "AnimationCmd{" +
                "number=" + number +
                ", x=" + x +
                ", y=" + y +
                "} " + super.toString();
    }
}
