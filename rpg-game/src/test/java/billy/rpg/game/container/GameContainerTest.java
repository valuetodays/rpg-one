package billy.rpg.game.container;

import billy.rpg.game.cmd.CmdBase;
import billy.rpg.game.loader.ScriptDataLoader;
import billy.rpg.game.resource.item.ScriptItem;
import org.apache.log4j.Logger;
import org.junit.Test;

import java.util.List;

/**
 * @author liulei@bshf360.com
 * @since 2017-07-24 13:44
 */
public class GameContainerTest {
    private static Logger LOG = Logger.getLogger(GameContainerTest.class);

    @Test
    public void testLoad() {
        GameContainer instance = GameContainer.getInstance();
        instance.load();
        List<ScriptItem> scriptItemList = instance.getScriptItemList();
        List<CmdBase> cmdList = scriptItemList.get(0).cmdList;
        for (CmdBase cmdBase : cmdList) {
            LOG.debug(cmdBase);
        }

    }

    @Test
    public void testLoadScript() throws Exception {
        ScriptDataLoader sl = new ScriptDataLoader();
        List<ScriptItem> slLoad = sl.load();
        for (ScriptItem scriptItem : slLoad) {
            LOG.debug(scriptItem);
        }
    }

}
