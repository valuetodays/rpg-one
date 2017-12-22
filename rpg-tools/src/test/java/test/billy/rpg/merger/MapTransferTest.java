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
        String ori = "z:/map/百草地.map";
        String dst = "z:/map/百草地_out.map";
        MapTransfer.transfer(ori, dst);

    }
}
