package billy.rpg.game.scriptParser.cmd;

/**
 * 命令 - if
 * @author <a href="http://blog.sina.com.cn/valuetodays">liulei-home</a>
 * @date 2016-05-10 12:31
 * @since 2016-05-10 12:31
 */
public class IfCmd extends CmdBase {
    private String condition;
    private String triggerName;


    public IfCmd(String condition, String triggerName) {
        super("if");
        this.condition = condition;
        this.triggerName = triggerName;
    }



    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getTriggerName() {
        return triggerName;
    }

    public void setTriggerName(String triggerName) {
        this.triggerName = triggerName;
    }
}
