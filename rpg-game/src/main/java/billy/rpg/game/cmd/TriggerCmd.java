package billy.rpg.game.cmd;

/**
 * 命令 - 触发器
 * @author <a href="http://blog.sina.com.cn/valuetodays">liulei-home</a>
 * @date 2016-05-09 22:31
 * @since 2016-05-09 22:31
 */
public class TriggerCmd extends CmdBase {

    private int x; // 地图坐标 x
    private int y; // 地图坐标 y
    private String triggerName; // 触发器名称

    public TriggerCmd() {
        super("trigger");
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

    public String getTriggerName() {
        return triggerName;
    }

    public void setTriggerName(String triggerName) {
        this.triggerName = triggerName;
    }
}
