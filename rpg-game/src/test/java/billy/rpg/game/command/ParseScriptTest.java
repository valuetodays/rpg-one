package billy.rpg.game.command;

import billy.rpg.game.loader.ScriptDataLoader;
import billy.rpg.game.resource.item.ScriptItem;
import org.apache.log4j.Logger;
import org.junit.Test;

import java.util.List;

/**
 * @author lei.liu@datatist.com
 * @since 2018-09-27 18:24:45
 */
public class ParseScriptTest {
    private static Logger LOG = Logger.getLogger(ParseScriptTest.class);

    @Test
    public void testParse() throws Exception {
        ScriptDataLoader sl = new ScriptDataLoader();
        List<ScriptItem> slLoad = sl.load();
        for (ScriptItem scriptItem : slLoad) {
            LOG.debug(scriptItem);
        }
    }
}
