package billy.rpg.game.core.command;

import billy.rpg.game.core.GameContainerBaseTest;
import billy.rpg.game.core.script.variable.VariableDeterminer;
import org.junit.Test;

import java.util.Arrays;

public class SetVarCmdTest extends GameContainerBaseTest {

    @Test
    public void test() {
        VariableDeterminer.getInstance().print();
        SetVarCmd setVarCmd = new SetVarCmd();
        setVarCmd.initCommand(1, "", Arrays.asList("aaa", "10"));
        setVarCmd.execute(gameContainer, null);
        VariableDeterminer.getInstance().print();
    }
}