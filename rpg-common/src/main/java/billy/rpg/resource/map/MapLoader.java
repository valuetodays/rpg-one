package billy.rpg.resource.map;

import billy.rpg.common.constant.MapFileConstant;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * load map the generated by MapSaver
 *
 * @author liulei
 * @since 2017-05-06 13:30
 */
public class MapLoader {
    private static final Logger LOG = Logger.getLogger(MapLoader.class);

    private static final String MAP_HEADER = MapFileConstant.MAP_HEADER;

    /**
     *
     * @param mapFilePath map filepath
     */
    public static MapMetaData load(String mapFilePath) {
        File file = new File(mapFilePath);
        FileInputStream fis = null;
        DataInputStream dis = null;
        List<int[][]> layers = new ArrayList<>();
        MapMetaData mapMetaData = new MapMetaData();

        try {
            fis = new FileInputStream(file);
            dis = new DataInputStream(fis);
            byte[] bMapHead = new byte[MAP_HEADER.getBytes("utf-8").length];
            dis.read(bMapHead, 0, bMapHead.length);
            String mapHeadUtf8 = new String(bMapHead, "utf-8");
            LOG.debug("MAP_HEADER `"+mapHeadUtf8+"` read");

            int tileIdBytesLength = dis.readInt();
            byte[] tileIdBytes = new byte[tileIdBytesLength];
            dis.read(tileIdBytes, 0, tileIdBytesLength);
            String tileId = new String(tileIdBytes, "utf-8");
            LOG.debug("tileId `" + tileId + "` read as utf-8");
            mapMetaData.setTileId(tileId);

            int mapNameBytesLength = dis.readInt();
            byte[] mapNameBytes = new byte[mapNameBytesLength];
            dis.read(mapNameBytes, 0, mapNameBytesLength);
            String mapName = new String(mapNameBytes, "utf-8");
            LOG.debug("mapName `"+mapName+"` read as utf-8");
            mapMetaData.setName(mapName);

            int height = dis.readInt();
            LOG.debug("height `"+height+"` read");
            mapMetaData.setHeight(height);
            int width = dis.readInt();
            LOG.debug("width `"+width+"` read");
            mapMetaData.setWidth(width);
            final int layersSize = dis.readInt();

            for (int i = 0; i < layersSize; i++) {
                int[][] layer = new int[width][height];
                for (int m = 0; m < width; m++) {
                    for(int n = 0; n < height; n++) {
                        int readN = dis.readInt();
                        layer[m][n] = readN;
                        //LOG.debug("readN `" + readN + "` read");
                    }
                }
                layers.add(layer);
            }
            mapMetaData.setMapId(file.getName());

        } catch (IOException e) {
            LOG.debug("IO exception: " + e.getMessage(), e);
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(dis);
            IOUtils.closeQuietly(fis);
        }

        mapMetaData.setLayers(layers);

        LOG.debug("loaded map file ["+mapFilePath+"].");
        return mapMetaData;
    }


}
