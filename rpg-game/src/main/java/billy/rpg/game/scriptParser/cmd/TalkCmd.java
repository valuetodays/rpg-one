package billy.rpg.game.scriptParser.cmd;

/**
 * 命令 - 触发器
 * @author <a href="http://blog.sina.com.cn/valuetodays">liulei-home</a>
 * @since 2016-12-08 15:37
 */
public class TalkCmd extends CmdBase {

    private int num; // npc number
    private String triggerName; // 触发器名称

    public TalkCmd(int num, String triggerName) {
        super("talk");
        this.num = num;
        this.triggerName = triggerName;
    }

    public int getNum() {
        return num;
    }
    public void setNum(int num) {
        this.num = num;
    }
    public String getTriggerName() {
        return triggerName;
    }
    public void setTriggerName(String triggerName) {
        this.triggerName = triggerName;
    }
    
}
