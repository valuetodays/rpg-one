package test.billy.jee.rpg.common;

import com.billy.jee.rpg.common.BoxImageLoader;
import org.apache.commons.io.IOUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 将byte[]转成BufferedImage，中间不再生成文件
 *
 * @author liulei
 * @since 2017-05-26 11:43
 */
public class ImageTest extends JFrame {
    private BufferedImage src;
    private BufferedImage dst;

    private ImageTest () {
        setVisible(true);
        setLocation(300, 200);
        getContentPane().setPreferredSize(new Dimension(300, 300));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        pack();
        try {
            image2ByteArr();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new ImageTest();

    }


    public void image2ByteArr() throws IOException {
        final String npcPath = "/Images/character/box/";
        InputStream closedIs = BoxImageLoader.class.getResourceAsStream(npcPath + "box_closed.png");
        src = (BufferedImage) ImageIO.read(closedIs);
        IOUtils.closeQuietly(closedIs);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ImageIO.write(src, "JPG", out);
        byte[] b = out.toByteArray();


        ByteArrayInputStream in = new ByteArrayInputStream(b);    //将b作为输入流；
        dst = ImageIO.read(in);     //将in作为输入流，读取图片存入image中，而这里in可以为ByteArrayInputStream();
        System.out.println(dst.equals(src));
        repaint();
    }

    public void paint(Graphics g) {
        super.paint(g);

        if (src != null) {
            g.drawImage(src, 100,50,null);    //image为BufferedImage类型
        }
        if (dst != null) {
            g.drawImage(dst, 200, 50, null);    //image为BufferedImage类型
        }

    }

}
