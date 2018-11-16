package billy.rpg.game.core.command;

import java.util.List;

import billy.rpg.game.core.command.processor.CmdProcessor;
import billy.rpg.game.core.container.GameContainer;
import billy.rpg.game.core.screen.AnimationScreen;
import billy.rpg.game.core.screen.BaseScreen;

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
    public int execute(GameContainer gameContainer, CmdProcessor cmdProcessor) {
        BaseScreen as = new AnimationScreen(gameContainer, number, x, y, gameContainer.getMapScreen());
        gameContainer.getGameFrame().pushScreen(as);
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
