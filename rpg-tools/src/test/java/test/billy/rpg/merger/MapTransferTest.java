package test.billy.rpg.merger;

import billy.rpg.mapeditor.MapTransfer;
import billy.rpg.resource.map.BinaryMapSaverLoader;
import billy.rpg.resource.map.MapMetaData;
import org.junit.Test;

import java.io.IOException;

/**
 * @author liulei@bshf360.com
 * @since 2017-12-22 15:33
 */
public class MapTransferTest {

    @Test
    public void testTransfer() throws IOException {
        String basePath = ("D:\\tmp\\fmj\\map");

        String ori = basePath + "/百草地.map";
        String dst = basePath + "/百草地_out.map";
        MapTransfer.transfer(ori, dst);
        MapMetaData mapMetaData = new BinaryMapSaverLoader().load(dst);
        int[][] bgLayer = mapMetaData.getBgLayer();
        String tileId = mapMetaData.getTileId();
        int[][] walk = mapMetaData.getWalk();
        int width = mapMetaData.getWidth();
        int height = mapMetaData.getHeight();

        System.out.println(tileId);
        // show map data
        for (int xk = 0; xk < height; xk++) {
            for (int y = 0; y < width; y++) {
                System.out.print(bgLayer[y][xk] + 1 + ", ");
            }
            System.out.println();
        }


    }
}
