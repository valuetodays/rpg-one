package test.billy.rpg.common;

import com.billy.rpg.resource.box.BoxImageLoader;
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
     * 将图片转成字节数据保存到文件中
     */
    @Test
    public void saveImage2ByteArrToFile() throws IOException {
        final String npcPath = "/Images/character/box/";
        InputStream closedIs = BoxImageLoader.class.getResourceAsStream(npcPath + "box_closed.png");
        BufferedImage bufferedImage = ImageIO.read(closedIs);
        IOUtils.closeQuietly(closedIs);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "PNG", out);
        byte[] b = out.toByteArray();
        IOUtils.closeQuietly(out);

        File file = new File("z:/image_byte_arr");
        FileOutputStream fos = null;
        DataOutputStream dos = null;
        fos = new FileOutputStream(file);
        dos = new DataOutputStream(fos);
        dos.writeInt(b.length);
        dos.write(b);

        IOUtils.closeQuietly(dos);
        IOUtils.closeQuietly(fos);
    }


    /**
     * 从文件中读出字节数组转成BufferedImage
     */
    public void readImage2ByteArrFromFile() throws IOException {

        File file = new File("z:/image_byte_arr");
        FileInputStream fis = null;
        DataInputStream dis = null;
        fis = new FileInputStream(file);
        dis = new DataInputStream(fis);

        int length = dis.readInt();
        byte[] bytes = new byte[length];
        dis.read(bytes);
        IOUtils.closeQuietly(dis);
        IOUtils.closeQuietly(fis);

        ByteArrayInputStream in = new ByteArrayInputStream(bytes);    //将b作为输入流；
        BufferedImage dst = ImageIO.read(in);
        IOUtils.closeQuietly(in);
        Assert.assertNotNull(dst);
    }

}
