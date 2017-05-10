package test;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.junit.Test;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author liulei
 * @since 2017-05-10 09:28
 */
public class MapSaverTest {
    private static final Logger LOG = Logger.getLogger(MapSaverTest.class);
    private static final String MAP_HEADER = "map header";

        int tileYheight = 15;
        int tileXwidth = 20;
    @Test
    public void testSave() {
        String mapFilePath = "z:/main-test.1.1.map";

        String mapName = "百草地";

        List<int[][]> layers = getLayers();



        File file = new File(mapFilePath);
        FileOutputStream fos = null;
        DataOutputStream dos = null;
        try {
            fos = new FileOutputStream(file);
            dos = new DataOutputStream(fos);
            dos.write(MAP_HEADER.getBytes("utf-8"));
            LOG.debug("MAP_HEADER `"+MAP_HEADER+"` written as utf-8");
            byte[] mapNameBytes = mapName.getBytes("utf-8");
            dos.writeInt(mapNameBytes.length);
            dos.write(mapNameBytes);
            LOG.debug("mapName `"+mapName+"` written as utf-8");

            dos.writeInt(tileYheight);
            LOG.debug("tileYheight `"+tileYheight+"` written");
            dos.writeInt(tileXwidth);
            LOG.debug("tileXwidth `"+tileXwidth+"` written");
            final int layersSize = layers.size();
            dos.writeInt(layersSize);
            LOG.debug("layer'size `"+ layersSize +"` written");

            for (int i = 0; i < layersSize; i++) {
                int[][] layer = layers.get(i);
                for (int m = 0; m < tileXwidth; m++) {
                    for(int n = 0; n < tileYheight; n++) {
                        int layer_data = layer[m][n];
                        int y = layer_data % 100;
                        int x = layer_data / 100;
                        dos.writeInt(x*100 + y);
                        LOG.debug("x/y = `"+x+"/"+y+"`, `"+(x*100+y)+"` written" );
                    }
                }
            }

        } catch (IOException e) {
            LOG.debug("IO异常");
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(dos);
            IOUtils.closeQuietly(fos);
        }


    }

    private List<int[][]> getLayers() {
        int width = tileXwidth;
        int height = tileYheight;
        int[][] layer1 = new int[width][height]; // layer1
        int[][] layer2 = new int[width][height]; // layer2
        int[][] layer3 = new int[width][height]; // layer3
        int[][] layer4 = new int[width][height]; // event
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                layer1[i][j] = 101;
                layer2[i][j] = 202;
                layer3[i][j] = 303;
                layer4[i][j] = -1;
            }
        }
        List<int[][]> layers = new ArrayList<>();
        layers.add(layer1);
        layers.add(layer2);
        layers.add(layer3);
        layers.add(layer4);

        return layers;
    }
}
