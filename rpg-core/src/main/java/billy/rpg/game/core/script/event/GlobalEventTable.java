package billy.rpg.game.core.script.event;

import java.util.*;

import billy.rpg.game.core.container.GameContainer;

/**
 * 全局事件表
 */
public class GlobalEventTable extends EventTable {

    private Set<String> globalEvents = new HashSet<>();

    @Override
    public boolean existsEvent(String event) {
        return globalEvents.contains(event);
    }

    @Override
    public void putEvent(String event) {
        globalEvents.add(event);
    }

    @Override
    public void delEvent(String event) {
        globalEvents.remove(event);
    }

    @Override
    public void printEvents() {
        System.out.println("=== globalEvents starts ===");
        for (String v: globalEvents) {
            System.out.println("   " +v);
        }
        System.out.println("=== globalEvents ends ===");
    }

}
