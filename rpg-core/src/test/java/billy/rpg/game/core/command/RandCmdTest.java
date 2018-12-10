package billy.rpg.game.core.command;

import billy.rpg.game.core.GameContainerBaseTest;
import billy.rpg.game.core.script.variable.VariableDeterminer;
import org.junit.Test;

import java.util.Arrays;

/**
 * @author lei.liu@datatist.com
 * @since 2018-12-10 15:35:13
 */
public class RandCmdTest extends GameContainerBaseTest {
    @Test
    public void test() {
        String key = "var1";
        for (int i = 0; i < 10; i++) {
            RandCmd randCmd = new RandCmd();
            randCmd.initCommand(1, "", Arrays.asList(key, "10", "20"));
            randCmd.execute(gameContainer, null);
            System.out.println(VariableDeterminer.getInstance().get(key));
        }

    }

}