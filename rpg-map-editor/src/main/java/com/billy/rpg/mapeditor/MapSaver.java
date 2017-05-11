package com.billy.rpg.mapeditor;

import com.billy.jee.rpg.common.constant.MapFileConstant;
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
    private MapEditorFrame mapEditorFrame;

    public MapSaver(MapEditorFrame mapEditorFrame) {
        this.mapEditorFrame = mapEditorFrame;
    }

    private static final String MAP_HEADER = MapFileConstant.MAP_HEADER;

    /**
     * map file data structure
     *
     * 10 bytes map header
     * 4 bytes map name length (in byte)
     * N bytes map name
     * 4 bytes height
     * 4 bytes width
     * 4 bytes layers size
     * N bytes width*height*4 4-layers map data
     *
     * @param mapFilePath map filepath
     */
    public void save(String mapFilePath) {

        String mapName = mapEditorFrame.getMapEditorPanel().getMapName();
        MapEditorPanel mapEditorPanel = mapEditorFrame.getMapEditorPanel();
        MapAreaPanel mapArea = mapEditorPanel.getMapArea();
        List<int[][]> layers = mapArea.getLayers();
        int tileYheight = mapArea.getTileYheight();
        int tileXwidth = mapArea.getTileXwidth();

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
                        dos.writeInt(layer_data);

                        LOG.debug("`layer"+(i+1)+" "+(layer_data)+"` written" );
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
        LOG.debug("saved file `{"+mapFilePath+"}`.");
    }

    public static void main(String[] args) {
        String mapFilePath = "Z:/dsddsf.map";
        File file = new File(mapFilePath);

        FileOutputStream fos = null;
        DataOutputStream dos = null;
        try {
            fos = new FileOutputStream(file);
            dos = new DataOutputStream(fos);
            dos.write(MAP_HEADER.getBytes("utf-8"));
//            Integer tileYheight = -1;
//            dos.writeInt(tileYheight);

//            LOG.debug("tileYheight `"+tileYheight+"` written");


        } catch (IOException e) {
            LOG.debug("IO异常");
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(dos);
            IOUtils.closeQuietly(fos);
        }
    }
}
