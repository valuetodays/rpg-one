package billy.rpg.game.cmd;

import billy.rpg.game.GameFrame;
import billy.rpg.game.cmd.executor.CmdProcessor;
import billy.rpg.game.container.GameContainer;

import java.util.List;

/**
 * 命令 - 加载地图
 * @author <a href="http://blog.sina.com.cn/valuetodays">liulei-home</a>
 * @since 2016-05-09 22:25
 */
public class LoadMapCmd extends CmdBase {
    private int m; // 地图序号 m
    private int n; // 地图序号 n
    private int x; // 地图坐标 x
    private int y; // 地图坐标 y
    private int offsetX;
    private int offsetY;

    public int getM() {
        return m;
    }

    public int getN() {
        return n;
    }


    public int getX() {
        return x;
    }


    public int getY() {
        return y;
    }


    public int getOffsetX() {
        return offsetX;
    }


    public int getOffsetY() {
        return offsetY;
    }


    @Override
    public void init() {
        List<String> arguments = super.getArguments();
        m = Integer.parseInt(arguments.get(0));
        n = Integer.parseInt(arguments.get(1));
        x = Integer.parseInt(arguments.get(2));
        y = Integer.parseInt(arguments.get(3));
        offsetX = Integer.parseInt(arguments.get(4));
        offsetY = Integer.parseInt(arguments.get(5));
    }

    @Override
    public int execute(CmdProcessor cmdProcessor) {
//        GameFrame.getInstance().getGameContainer().getMapScreen().clearOffset();
        GameFrame.getInstance().getGameContainer().getMapScreen().setOffset(offsetX, offsetY);
        GameContainer.getInstance().startChapter(getM(), getN(), getX() + "-" + getY());
        return 0;
    }

    @Override
    public String getUsage() {
        return null;
    }

    @Override
    public int getArgumentSize() {
        return 6;
    }

    @Override
    public String toString() {
        return "LoadMapCmd{" +
                "m=" + m +
                ", n=" + n +
                ", x=" + x +
                ", y=" + y +
                ", offsetX=" + offsetX +
                ", offsetY=" + offsetY +
                "} " + super.toString();
    }
}
