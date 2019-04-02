package billy.rpg.game.core.command;

import billy.rpg.game.core.GameContainerTestBase;
import billy.rpg.game.core.command.parser.CommandParser;
import billy.rpg.game.core.command.parser.support.JlineCommandParser;
import billy.rpg.game.core.script.variable.VariableDeterminer;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

/**
 * @author lei.liu@datatist.com
 * @since 2018-12-10 14:01:40
 */
public class AddVarCmdTest extends GameContainerTestBase {

    @Test
    public void testCmdString() {
        String str = "addvar coin 1";
        CommandParser jlineCommandParser = new JlineCommandParser();
        CmdBase cmdBase = jlineCommandParser.parse(null, 0, str);
        Assert.assertNotNull(cmdBase);
        Assert.assertEquals(cmdBase.getClass(), AddVarCmd.class);
        AddVarCmd addVarCmd = (AddVarCmd) cmdBase;
        Assert.assertEquals("coin", addVarCmd.getKey());
        Assert.assertEquals(1, addVarCmd.getDelta());
        Assert.assertEquals(2, addVarCmd.getArgumentSize());
        Assert.assertEquals("addvar var 100", addVarCmd.getUsage());
    }

    @Test
    public void testCmd() {
        VariableDeterminer.getInstance().print();
        String key = "age";
        int value = 10;
        SetVarCmd setVarCmd = new SetVarCmd();
        setVarCmd.initCommand(1, "", Arrays.asList(key, "0"));
        setVarCmd.execute(gameContainer, null);

        AddVarCmd addVarCmd = new AddVarCmd();
        addVarCmd.initCommand(1, "", Arrays.asList(key, "" + value));
        addVarCmd.execute(gameContainer, null);
        addVarCmd.initCommand(1, "", Arrays.asList(key, "" + value));
        addVarCmd.execute(gameContainer, null);
        VariableDeterminer.getInstance().print();
        Assert.assertTrue(VariableDeterminer.getInstance().get(key) == value*2);
    }
}