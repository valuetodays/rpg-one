package billy.rpg.resource;

import billy.rpg.common.util.ImageUtil;
import billy.rpg.resource.box.BoxImageLoader;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 *
 * @author liulei
 * @since 2017-05-26 13:18
 */
public class ImageByteArrSaveAndReadTest {

    /**
     * 将图片转成字节数据，取反后保存到文件中
     */
    @Test
    public void saveImage2ByteArrToFile() throws IOException {
        final String npcPath = "/assets/Images/character/box/";
        InputStream closedIs = BoxImageLoader.class.getResourceAsStream(npcPath + "box_closed.png");
        BufferedImage bufferedImage = ImageIO.read(closedIs);
        IOUtils.closeQuietly(closedIs);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "PNG", out);
        byte[] b = out.toByteArray();
        IOUtils.closeQuietly(out);

        File file = new File(System.getProperty("java.io.tmpdir") + "/image_byte_arr");
        FileOutputStream fos = null;
        DataOutputStream dos = null;
        fos = new FileOutputStream(file);
        dos = new DataOutputStream(fos);
        dos.writeInt(b.length);
        byte[] br = ImageUtil.reverseBytes(b);
        dos.write(br);
        dos.flush();

        IOUtils.closeQuietly(dos);
        IOUtils.closeQuietly(fos);
    }


    @Test
    public void readImage2ByteArrFromFile() throws IOException {
        File file = new File(System.getProperty("java.io.tmpdir") + "/image_byte_arr");
        FileInputStream fis = null;
        DataInputStream dis = null;
        fis = new FileInputStream(file);
        dis = new DataInputStream(fis);

        int length = dis.readInt();
        byte[] bytes = new byte[length];
        dis.read(bytes);
        IOUtils.closeQuietly(dis);
        IOUtils.closeQuietly(fis);

        byte[] bytesArr = ImageUtil.reverseBytes(bytes);
        ByteArrayInputStream in = new ByteArrayInputStream(bytesArr);    //将b作为输入流；
        BufferedImage bufferedImage = ImageIO.read(in);
        IOUtils.closeQuietly(in);
        Assert.assertNotNull(bufferedImage);
    }


}
