package billy.rpg.game.core.script.event;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author lei.liu
 * @since 2019-05-08 10:20
 */
public class EventTableDeterminerTest {

    @Test
    public void getInstance() {
        EventTableDeterminer instance = EventTableDeterminer.getInstance();
        Assert.assertNotNull(instance);
    }

    @Test
    public void putEvent() {
        String event = "aaa";
        EventTableDeterminer.getInstance().putEvent(event);
        Assert.assertTrue(EventTableDeterminer.getInstance().existsEvent(event));
    }

    @Test
    public void delEvent() {
        String event = "aaa";
        EventTableDeterminer.getInstance().putEvent(event);
        Assert.assertTrue(EventTableDeterminer.getInstance().existsEvent(event));
        EventTableDeterminer.getInstance().delEvent(event);
        Assert.assertFalse(EventTableDeterminer.getInstance().existsEvent(event));
    }

    @Test
    public void existsEvent() {
        putEvent();
    }

    @Test
    public void printEvents() {
        String event = "aaa";
        EventTableDeterminer.getInstance().putEvent(event);
        EventTableDeterminer.getInstance().printEvents();
    }
}