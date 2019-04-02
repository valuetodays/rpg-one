package billy.rpg.game.core.command;

import billy.rpg.game.core.GameContainerTestBase;
import billy.rpg.game.core.command.parser.CommandParser;
import billy.rpg.game.core.command.parser.support.JlineCommandParser;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;

/**
 * @author lei.liu
 * @since 2019-04-02 17:20
 */
public class AddGoodsCmdTest extends GameContainerTestBase {

    @Test
    public void init() {
        String str = "addgoods 2002";
        CommandParser jlineCommandParser = new JlineCommandParser();
        CmdBase cmdBase = jlineCommandParser.parse(null, 0, str);
        Assert.assertNotNull(cmdBase);
        Assert.assertEquals(cmdBase.getClass(), AddGoodsCmd.class);
        AddGoodsCmd addGoodsCmd = (AddGoodsCmd) cmdBase;
        Assert.assertEquals(2002, addGoodsCmd.getNumber());
        Assert.assertEquals(1, addGoodsCmd.getArgumentSize());
        Assert.assertEquals("addgoods goodsId", addGoodsCmd.getUsage());
    }

    @Test(expected = NullPointerException.class)
    public void execute() {
        int goodsId = 2002;
        AddGoodsCmd addGoodsCmd = new AddGoodsCmd();
        addGoodsCmd.initCommand(1, "", Collections.singletonList(String.valueOf(goodsId)));
        addGoodsCmd.execute(gameContainer, null);
    }

}