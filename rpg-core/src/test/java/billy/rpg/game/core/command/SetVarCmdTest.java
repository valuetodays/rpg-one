package billy.rpg.game.core.command;

import billy.rpg.game.core.GameContainerTestBase;
import billy.rpg.game.core.script.variable.VariableDeterminer;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class SetVarCmdTest extends GameContainerTestBase {

    @Test
    public void test() {
        VariableDeterminer.getInstance().print();
        String key = "age";
        int value = 10;
        SetVarCmd setVarCmd = new SetVarCmd();
        setVarCmd.initCommand(1, "", Arrays.asList(key, "" + value));
        setVarCmd.execute(gameContainer, null);
        VariableDeterminer.getInstance().print();
        Assert.assertTrue(VariableDeterminer.getInstance().get(key) == value);
    }
}