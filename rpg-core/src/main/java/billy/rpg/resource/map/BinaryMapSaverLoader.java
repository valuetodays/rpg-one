package billy.rpg.resource.map;

import billy.rpg.common.constant.ToolsConstant;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lei.liu@datatist.com
 * @since 2018-10-16 17:31:57
 */
@Deprecated
public class BinaryMapSaverLoader implements MapSaverLoader {
    private static final Logger LOG = Logger.getLogger(BinaryMapSaverLoader.class);
    private static final String MAP_HEADER = ToolsConstant.MAGIC_MAP;
    private static final String CHARSET = ToolsConstant.CHARSET;

    @Override
    public void save(String mapFilePath, MapMetaData mapMetaData) throws IOException {
        String tileId = mapMetaData.getTileId();
        String mapName = mapMetaData.getName();
        List<int[][]> layers = mapMetaData.getLayers();
        int height = mapMetaData.getHeight();
        int width = mapMetaData.getWidth();

        File file = new File(mapFilePath);
        FileOutputStream fos = null;
        DataOutputStream dos = null;
        try {
            fos = new FileOutputStream(file);
            dos = new DataOutputStream(fos);
            dos.write(MAP_HEADER.getBytes(CHARSET));
            LOG.debug("MAP_HEADER `"+MAP_HEADER+"` written as " + CHARSET);

            byte[] tileIdBytes = tileId.getBytes(CHARSET);
            dos.writeInt(tileIdBytes.length);
            dos.write(tileIdBytes);
            LOG.debug("tileId `"+tileId+"` written as " + CHARSET);

            byte[] mapNameBytes = mapName.getBytes(CHARSET);
            dos.writeInt(mapNameBytes.length);
            dos.write(mapNameBytes);
            LOG.debug("mapName `"+mapName+"` written as " + CHARSET);

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

    @Override
    public MapMetaData load(String mapFilePath) throws IOException {
        File file = new File(mapFilePath);
        FileInputStream fis = null;
        DataInputStream dis = null;
        List<int[][]> layers = new ArrayList<>();
        MapMetaData mapMetaData = new MapMetaData();

        try {
            fis = new FileInputStream(file);
            dis = new DataInputStream(fis);
            byte[] bMapHead = new byte[MAP_HEADER.getBytes(CHARSET).length];
            dis.read(bMapHead, 0, bMapHead.length);
            String mapHeadUtf8 = new String(bMapHead, CHARSET);
            LOG.debug("MAP_HEADER `"+mapHeadUtf8+"` read");

            int tileIdBytesLength = dis.readInt();
            byte[] tileIdBytes = new byte[tileIdBytesLength];
            dis.read(tileIdBytes, 0, tileIdBytesLength);
            String tileId = new String(tileIdBytes, CHARSET);
            LOG.debug("tileId `" + tileId + "` read as " + CHARSET);
            mapMetaData.setTileId(tileId);

            int mapNameBytesLength = dis.readInt();
            byte[] mapNameBytes = new byte[mapNameBytesLength];
            dis.read(mapNameBytes, 0, mapNameBytesLength);
            String mapName = new String(mapNameBytes, CHARSET);
            LOG.debug("mapName `"+mapName+"` read as " + CHARSET);
            mapMetaData.setName(mapName);

            int height = dis.readInt();
            LOG.debug("height `"+height+"` read");
            if (height < 15) {
                throw new RuntimeException("error: ["+mapFilePath+"] map height less than 15.");
            }
            mapMetaData.setHeight(height);
            int width = dis.readInt();
            LOG.debug("width `"+width+"` read");
            if (width < 20) {
                throw new RuntimeException("error: ["+mapFilePath+"] map width less than 20.");
            }
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
