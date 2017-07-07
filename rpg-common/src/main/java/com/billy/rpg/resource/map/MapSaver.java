package com.billy.rpg.resource.map;

import com.billy.rpg.common.constant.MapFileConstant;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * save the map into file
 *
 * @author liulei
 * @since 2017-05-06 13:30
 */
public class MapSaver {
    private static final Logger LOG = Logger.getLogger(MapSaver.class);


    private static final String MAP_HEADER = MapFileConstant.MAP_HEADER;

    /**
     * save map to specified file
     *
     *
     * @param mapFilePath map filepath
     * @param mapMetaData data to save
     */
    public static void save(String mapFilePath, MapMetaData mapMetaData) {
        String tileId = mapMetaData.getTileId();
        String mapName = mapMetaData.getMapName();
        List<int[][]> layers = mapMetaData.getLayers();
        int height = mapMetaData.getHeight();
        int width = mapMetaData.getWidth();

        File file = new File(mapFilePath);
        FileOutputStream fos = null;
        DataOutputStream dos = null;
        try {
            fos = new FileOutputStream(file);
            dos = new DataOutputStream(fos);
            dos.write(MAP_HEADER.getBytes("utf-8"));
            LOG.debug("MAP_HEADER `"+MAP_HEADER+"` written as utf-8");

            byte[] tileIdBytes = tileId.getBytes("utf-8");
            dos.writeInt(tileIdBytes.length);
            dos.write(tileIdBytes);
            LOG.debug("tileId `"+tileId+"` written as utf-8");

            byte[] mapNameBytes = mapName.getBytes("utf-8");
            dos.writeInt(mapNameBytes.length);
            dos.write(mapNameBytes);
            LOG.debug("mapName `"+mapName+"` written as utf-8");

            dos.writeInt(height);
            LOG.debug("height `"+height+"` written");
            dos.writeInt(width);
            LOG.debug("width `"+width+"` written");
            final int layersSize = layers.size();
            dos.writeInt(layersSize);
            LOG.debug("layer'size `"+ layersSize +"` written");

            for (int i = 0; i < layersSize; i++) {
                int[][] layer = layers.get(i);
                for (int m = 0; m < width; m++) {
                    for(int n = 0; n < height; n++) {
                        int layer_data = layer[m][n];
                        dos.writeInt(layer_data);
                        //LOG.debug("`layer"+(i+1)+" "+(layer_data)+"` written" );
                    }
                }
            }

        } catch (IOException e) {
            LOG.debug("IO exception:" + e.getMessage(), e);
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(dos);
            IOUtils.closeQuietly(fos);
        }
        LOG.debug("saved file `{"+mapFilePath+"}`.");
    }


}
