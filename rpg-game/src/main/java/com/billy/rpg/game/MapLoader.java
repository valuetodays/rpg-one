package com.billy.rpg.game;

import com.billy.scriptParser.bean.MapDataLoaderBean;
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

    private static final String MAP_HEADER = "map header";

    /**
     *
     * @param mapFilePath map filepath
     */
    public MapDataLoaderBean load(String mapFilePath) {
        File file = new File(mapFilePath);
        FileInputStream fis = null;
        DataInputStream dis = null;
        List<int[][]> layers = new ArrayList<>();
        MapDataLoaderBean mapDataLoaderBean = new MapDataLoaderBean();

        try {
            fis = new FileInputStream(file);
            dis = new DataInputStream(fis);
            byte[] bMapHead = new byte[MAP_HEADER.getBytes("utf-8").length];
            dis.read(bMapHead, 0, bMapHead.length);
            String mapHeadUtf8 = new String(bMapHead, "utf-8");
            LOG.debug("MAP_HEADER `"+mapHeadUtf8+"` read");
            int mapNameBytesLength = dis.readInt();
            byte[] mapNameBytes = new byte[mapNameBytesLength];
            dis.read(mapNameBytes, 0, mapNameBytesLength);
            String mapName = new String(mapNameBytes, "utf-8");
            LOG.debug("mapName `"+mapName+"` read as utf-8");
            mapDataLoaderBean.setName(mapName);

            int tileYheight = dis.readInt();
            LOG.debug("tileYheight `"+tileYheight+"` read");
            mapDataLoaderBean.setHeight(tileYheight);
            int tileXwidth = dis.readInt();
            LOG.debug("tileXwidth `"+tileXwidth+"` read");
            mapDataLoaderBean.setWidth(tileXwidth);
            final int layersSize = dis.readInt();

            for (int i = 0; i < layersSize; i++) {
                int[][] layer = new int[tileXwidth][tileYheight];
                for (int m = 0; m < tileXwidth; m++) {
                    for(int n = 0; n < tileYheight; n++) {
                        int readN = dis.readInt();
                        layer[m][n] = readN;
                        LOG.debug("readN `" + readN + "` read");
                    }
                }
                layers.add(layer);
            }

        } catch (IOException e) {
            LOG.debug("IO异常");
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(dis);
            IOUtils.closeQuietly(fis);
        }

        mapDataLoaderBean.setLayer1(layers.get(0));
        mapDataLoaderBean.setLayer2(layers.get(1));
        mapDataLoaderBean.setLayer3(layers.get(2));
        mapDataLoaderBean.setFlag(layers.get(3));
        mapDataLoaderBean.initMapId(file.getName());

        LOG.debug("map file `"+mapFilePath+"` loaded");
        return mapDataLoaderBean;
    }

    public static void main(String[] args) {
        String mapFilePath = "z:/main-test.1.1.map";
        MapLoader mapLoader = new MapLoader();
        mapLoader.load(mapFilePath);
    }
}
