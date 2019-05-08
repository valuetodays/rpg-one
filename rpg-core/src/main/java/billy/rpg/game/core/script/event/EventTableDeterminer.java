package billy.rpg.game.core.script.event;

/**
 * 事件表决定者
 *
 * @author liulei@bshf360.com
 * @since 2017-12-21 14:12
 */
public class EventTableDeterminer extends EventTable {
    // 全局事件
    private EventTable globalVirtualTable = new GlobalEventTable();

    private static EventTableDeterminer virtualTableDeterminer = new EventTableDeterminer();
    private EventTableDeterminer() {}

    public static EventTableDeterminer getInstance() {
        return virtualTableDeterminer;
    }

    @Override
    public void putEvent(String event) {
        if (isGlobalEvent(event)) {
            globalVirtualTable.putEvent(event);
        }
    }

    @Override
    public void delEvent(String event) {
        if (isGlobalEvent(event)) {
            globalVirtualTable.delEvent(event);
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
    public boolean existsEvent(String event) {
        if (isGlobalEvent(event)) {
            return globalVirtualTable.existsEvent(event);
        }

        return false;
    }

    @Override
    public void printEvents() {
        globalVirtualTable.printEvents();
    }
}
