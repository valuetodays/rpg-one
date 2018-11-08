package billy.rpg.game.imagetransform;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author lei.liu@datatist.com
 * @since 2018-11-08 10:56:38
 */
public abstract class ImageOperationBaseFrame extends JFrame {
    private static final long serialVersionUID = -2619187470761918323L;

    protected BufferedImage imageOrig;
    protected BufferedImage imageTarget;
    private String imagePath = "/10.png";

    public ImageOperationBaseFrame(String title) throws HeadlessException {
        this.setTitle(title);
        this.setLocation(100, 200);
        this.setSize(600, 400);// 设置宽高
        this.setLocationRelativeTo(null);// 自动适配到屏幕中间
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// 设置关闭模式
        this.setResizable(false);
        this.setVisible(true);// 设置可见
        try {
            InputStream resourceAsStream = ImageOperationBaseFrame.class.getResourceAsStream(imagePath);
            imageOrig = ImageIO.read(resourceAsStream);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage(), e);
        }

        transForm();
    }


    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setFont(new Font("宋体", Font.BOLD, 24));
        g.drawString(getTitle(), 10, 10);
        g.drawImage(imageOrig, 30, 30, null);
        g.drawImage(imageTarget, 230, 30, null);
    }

    protected abstract void transForm();
}
