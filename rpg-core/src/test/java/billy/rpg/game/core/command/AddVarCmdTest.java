package billy.rpg.game.core.command;

import billy.rpg.game.core.GameContainerBaseTest;
import billy.rpg.game.core.script.variable.VariableDeterminer;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

/**
 * @author lei.liu@datatist.com
 * @since 2018-12-10 14:01:40
 */
public class AddVarCmdTest extends GameContainerBaseTest {

    @Test
    public void test() {
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