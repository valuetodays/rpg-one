package billy.rpg.game.cmd;

import billy.rpg.game.GameFrame;
import billy.rpg.game.cmd.executor.CmdProcessor;
import billy.rpg.game.screen.AnimationScreen;
import billy.rpg.game.screen.BaseScreen;

/**
 * 动画命令
 */
public class AnimationCmd extends CmdBase {
    private int number;
    private int x;
    private int y;

    public AnimationCmd(int number, int x, int y) {
        super("animation");
        this.number = number;
        this.x = x;
        this.y = y;
    }

    @Override
    public int execute(CmdProcessor cmdProcessor) {
        BaseScreen as = new AnimationScreen(number, x, y, GameFrame.getInstance().getGameContainer().getMapScreen());
        GameFrame.getInstance().pushScreen(as);
        return 0;
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
