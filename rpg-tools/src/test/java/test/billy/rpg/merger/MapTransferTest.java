package test.billy.rpg.merger;

import billy.rpg.mapeditor.MapTransfer;
import org.junit.Test;

/**
 * @author liulei@bshf360.com
 * @since 2017-12-22 15:33
 */
public class MapTransferTest {

    @Test
    public void testTransfer() {
        String basePath = ("Z:");

        String ori = basePath + "/伏魔山道.map";
        String dst = basePath + "/伏魔山道_out.map";
        MapTransfer.transfer(ori, dst);
    }
}
