package billy.rpg.mapeditor;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * 将a rpg的地图文件转换成本项目使用的
 *
 * @author liulei@bshf360.com
 * @since 2017-12-22 15:33
 */
public class MapTransfer {
    private static Logger LOG = Logger.getLogger(MapTransfer.class);

    public static void transfer(String ori, String dst) {
        LOG.debug("starts");

        File oriFile = new File(ori);
        FileInputStream fis = null;
        DataInputStream dis = null;
        try {
            fis = new FileInputStream(oriFile);
            dis = new DataInputStream(fis);

            int type = dis.read();
            LOG.debug("type: " + type);
            int index = dis.read();
            LOG.debug("index: " + index);
            int tileNum = dis.read();
            LOG.debug("tileNmu: " + tileNum);
            int nameBytesLen = (0x0c - 0x03);
            byte[] nameBytes = new byte[nameBytesLen];
            dis.read(nameBytes);
            String nameGBK = new String(nameBytes, "GBK");
            LOG.debug("nameGBK: " + nameGBK);


        } catch (IOException e) {
            LOG.debug("IO exception: " + e.getMessage(), e);
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(dis);
            IOUtils.closeQuietly(fis);
        }

        LOG.debug("ends");
    }
}
