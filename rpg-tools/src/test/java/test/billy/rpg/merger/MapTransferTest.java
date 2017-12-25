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
        String basePaht = ("C:/Users/bshf-ll/Desktop/aaa");

        String ori = basePaht + "/伏魔洞.map";
        String dst = basePaht + "/伏魔洞_out.map";
        MapTransfer.transfer(ori, dst);
    }
}
