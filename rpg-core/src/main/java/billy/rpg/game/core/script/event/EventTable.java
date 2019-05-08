package billy.rpg.game.core.script.event;

/**
 * 事件表
 *
 * @author liulei@bshf360.com
 * @since 2017-12-21 14:08
 */
public abstract class EventTable {
    /**
     * 存放一个事件
     * @param event 事件
     */
    public abstract void putEvent(String event);

    /**
     * 删除一个事件
     * @param event 事件
     */
    public abstract void delEvent(String event);

    /**
     * 是否存在指定事件
     * @param event 事件
     */
    public abstract boolean existsEvent(String event);

    /**
     * 打印变量
     */
    public abstract void printEvents();

}
