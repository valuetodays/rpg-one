package billy.rpg.game.cmd;

import billy.rpg.game.GameFrame;
import billy.rpg.game.cmd.executor.CmdProcessor;
import billy.rpg.game.container.GameContainer;

/**
 * 命令 - 加载地图
 * @author <a href="http://blog.sina.com.cn/valuetodays">liulei-home</a>
 * @date 2016-05-09 22:25
 * @since 2016-05-09 22:25
 */
public class LoadMapCmd extends CmdBase {
    private int m; // 地图序号 m
    private int n; // 地图序号 n
    private int x; // 地图坐标 x
    private int y; // 地图坐标 y

    public LoadMapCmd(int m, int n, int x, int y) {
        super("loadmap");
        this.m = m;
        this.n = n;
        this.x = x;
        this.y = y;
    }

    public int getM() {
        return m;
    }

    public void setM(int m) {
        this.m = m;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public int execute(CmdProcessor cmdProcessor) {
        GameFrame.getInstance().getGameContainer().getMapScreen().clearOffset();
        GameContainer.getInstance().startChapter(getM(), getN(), getX() + "-" + getY());
        return 0;
    }

    @Override
    public String toString() {
        return "LoadMapCmd{" +
                "m=" + m +
                ", n=" + n +
                ", x=" + x +
                ", y=" + y +
                "} " + super.toString();
    }
}
