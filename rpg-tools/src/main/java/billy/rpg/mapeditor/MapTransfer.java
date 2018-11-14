package billy.rpg.mapeditor;

import billy.rpg.common.constant.ToolsConstant;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import java.io.*;

/**
 * 将a rpg的地图文件转换成本项目使用的
 *
 * @author liulei@bshf360.com
 * @since 2017-12-22 15:33
 */
public class MapTransfer {
    private static Logger LOG = Logger.getLogger(MapTransfer.class);

    private static final String MAP_HEADER = ToolsConstant.MAGIC_MAP;
    private static final String CHARSET = ToolsConstant.CHARSET;

    public static void transfer(String ori, String dst) {
        LOG.debug("starts");

        File oriFile = new File(ori);
        File dstFile = new File(dst);
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(oriFile);

            int fileLen = fis.available();
            byte[] bytes = new byte[fileLen];
            fis.read(bytes);

            int type = bytes[0];
            LOG.debug("type: " + type);
            int index = bytes[1];
            LOG.debug("index: " + index);
            int tileIndex = bytes[2];
            LOG.debug("tileIndex: " + tileIndex);
            String nameGBK = null;
            try {
                int i = 0;
                while (bytes[3 + i] != 0x00)
                    ++i;
                nameGBK = new String(bytes, 3, i, "GBK");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            LOG.debug("nameGBK: " + nameGBK);
            int width = bytes[0x10];
            LOG.debug("width: " + width);
            int height = bytes[0x11];
            LOG.debug("height: " + height);
            int mapDataLen = width*height*2;
            byte[] mapData = new byte[mapDataLen];
            System.arraycopy(bytes, 0x12, mapData, 0, mapDataLen);

            FileOutputStream fos = null;
            DataOutputStream dos = null;
            fos = new FileOutputStream(dstFile);
            dos = new DataOutputStream(fos);

            dos.write(MAP_HEADER.getBytes(CHARSET));
            LOG.debug("MAP_HEADER `"+MAP_HEADER+"` written as " + CHARSET);
            String tileId = "tile"+tileIndex+".png";
            byte[] tileIdBytes = tileId.getBytes(CHARSET);
            dos.writeInt(tileIdBytes.length);
            dos.write(tileIdBytes);
            LOG.debug("tileId `"+tileId+"` written as " + CHARSET);

            byte[] mapNameBytes = nameGBK.getBytes(CHARSET);
            dos.writeInt(mapNameBytes.length);
            dos.write(mapNameBytes);
            LOG.debug("mapName `"+nameGBK+"` written as " + CHARSET);
            int finalHeight = height < 15 ? 15 : height;
            dos.writeInt(finalHeight);
            LOG.debug("height `"+finalHeight+"` written");
            dos.writeInt(width);
            LOG.debug("width `"+width+"` written");

            int layersSize = 2;
            dos.writeInt(layersSize);
            LOG.debug("layer'size `"+ layersSize +"` written");

            // bg layer
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    int temp = y * width + x;
                    int tileIndexValue = mapData[temp * 2] & 0x7f;
                    dos.writeInt(tileIndexValue);
                }
                // 如下是处理地图高度不超过15的，如伏魔山道
                if (height < 15) {
                    // 填充剩余的bg layer
                    //for (int xPadding = 0; xPadding < width; xPadding++) {
                        for (int yPadding = height; yPadding < 15; yPadding++) {
                            dos.writeInt(1);
                        }
                    //}
                }
            }
/*
            // npc layer
            for (int w = 0; w < width; w++) {
                for (int h = 0; h < height; h++) {
                    dos.writeInt(MapEditorConstant.NPC_NONE);
                }
                if (height < 15) {
                    // 填充剩余的npc layer
                    //for (int xPadding = 0; xPadding < width; xPadding++) {
                        for (int yPadding = height; yPadding < 15; yPadding++) {
                            dos.writeInt(MapEditorConstant.NPC_NONE);
                        }
                    //}
                }
            }*/

/*
            // fg layer
            for (int w = 0; w < width; w++) {
                for (int h = 0; h < height; h++) {
                    dos.writeInt(-1);
                }
                if (height < 15) {
                    // 填充剩余的fg layer
                    //for (int xPadding = 0; xPadding < width; xPadding++) {
                        for (int yPadding = height; yPadding < 15; yPadding++) {
                            dos.writeInt(-1);
                        }
                   // }
                }
            }*/

            // walk layer
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    int temp = y * width + x;
                    int walkable = mapData[temp*2] & 0x80;
                    if (walkable != 0) {
                        dos.writeInt(1);
                    } else {
                        dos.writeInt(-1);
                    }
                }
                if (height < 15) {
                    // 填充剩余的walk layer
                    //for (int xPadding = 0; xPadding < width; xPadding++) {
                        for (int yPadding = height; yPadding < 15; yPadding++) {
                            dos.writeInt(-1);
                        }
                   // }
                }
            }
/*
            // event number
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    int temp = y * width + x;
                    int eventNum = mapData[temp*2 + 1] & 0xFF;
                    dos.writeInt(eventNum);
                }
                if (height < 15) {
                    // 填充剩余的event number
                    //for (int xPadding = 0; xPadding < width; xPadding++) {
                        for (int yPadding = height; yPadding < 15; yPadding++) {
                            dos.writeInt(-1);
                        }
                    //}
                }
            }*/
            IOUtils.closeQuietly(dos);
            IOUtils.closeQuietly(fos);
        } catch (IOException e) {
            LOG.debug("IO exception: " + e.getMessage(), e);
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(fis);
        }

        LOG.debug("ends");
    }


}
