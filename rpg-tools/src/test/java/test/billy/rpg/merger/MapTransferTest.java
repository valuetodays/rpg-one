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
        String basePaht = ("Z:");

        String ori = basePaht + "/后山浮桥.map";
        String dst = basePaht + "/后山浮桥_out.map";
        MapTransfer.transfer(ori, dst);
    }
}
