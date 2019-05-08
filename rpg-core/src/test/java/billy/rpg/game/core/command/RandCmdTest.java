package billy.rpg.game.core.command;

import billy.rpg.game.core.GameContainerTestBase;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

/**
 * @author lei.liu
 * @since 2018-12-10 15:35:13
 */
public class RandCmdTest extends GameContainerTestBase {
    @Test
    public void test() {
        String key = "var1";
        for (int i = 0; i < 10; i++) {
            RandCmd randCmd = new RandCmd();
            randCmd.initCommand(1, "", Arrays.asList(key, "10", "20"));
            randCmd.execute(gameContainer, null);
            Assert.assertTrue(randCmd.getRandValue() >= 10);
            Assert.assertTrue(randCmd.getRandValue() <= 20);
        }

    }

}