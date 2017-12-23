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
        String ori = "C:/tmp/map/三清宫.map";
        String dst = "C:/tmp/map/三清宫_out.map";
        MapTransfer.transfer(ori, dst);

    }
}
