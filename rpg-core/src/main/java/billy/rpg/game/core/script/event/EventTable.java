package billy.rpg.game.core.script.event;

import billy.rpg.game.core.container.GameContainer;

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
    public abstract void putEvent(GameContainer gameContainer, String event);

    /**
     * 删除一个事件
     * @param event 事件
     */
    public abstract void delEvent(GameContainer gameContainer, String event);

    /**
     * 是否存在指定事件
     * @param event 事件
     */
    public abstract boolean existsEvent(GameContainer gameContainer, String event);

    /**
     * 打印变量
     */
    public abstract void printEvents(GameContainer gameContainer);

}
