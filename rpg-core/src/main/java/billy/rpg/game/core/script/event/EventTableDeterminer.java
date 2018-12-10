package billy.rpg.game.core.script.event;

import billy.rpg.game.core.container.GameContainer;

/**
 * 事件表决定者
 *
 * @author liulei@bshf360.com
 * @since 2017-12-21 14:12
 */
public class EventTableDeterminer extends EventTable {
    private EventTable globalVirtualTable = new GlobalEventTable();

    private static EventTableDeterminer virtualTableDeterminer = new EventTableDeterminer();
    private EventTableDeterminer() {}

    public static EventTableDeterminer getInstance() {
        return virtualTableDeterminer;
    }

    @Override
    public void putEvent(GameContainer gameContainer, String event) {
        if (isGlobalEvent(event)) {
            globalVirtualTable.putEvent(gameContainer, event);
        }
    }

    @Override
    public void delEvent(GameContainer gameContainer, String event) {
        if (isGlobalEvent(event)) {
            globalVirtualTable.delEvent(gameContainer, event);
        }
    }

    /**
     *
     * @param event 事件
     */
    private boolean isGlobalEvent(String event) {
        return Boolean.TRUE;
    }

    @Override
    public boolean existsEvent(GameContainer gameContainer, String event) {
        if (isGlobalEvent(event)) {
            return globalVirtualTable.existsEvent(gameContainer, event);
        }

        return false;
    }

    @Override
    public void printEvents(GameContainer gameContainer) {
        globalVirtualTable.printEvents(gameContainer);
    }
}
