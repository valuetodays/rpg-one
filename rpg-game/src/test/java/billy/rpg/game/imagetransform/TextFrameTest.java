package billy.rpg.game.imagetransform;

import billy.rpg.common.util.TextUtil;

import javax.swing.*;
import java.awt.*;

/**
 * 相对长度的进度条
 *
 * @author lei.liu@datatist.com
 * @since 2018-11-08 17:21:30
 */
public class TextFrameTest extends JFrame {
    private static final long serialVersionUID = 3599269994527144637L;

    public static void main(String[] args) {
        new TextFrameTest();
    }

    public TextFrameTest() {
        this.setTitle("文本打印测试");
        this.setLocation(100, 200);
        this.setSize(900, 500);// 设置宽高
        this.setLocationRelativeTo(null);// 自动适配到屏幕中间
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);// 设置关闭模式
        this.setResizable(false);
        this.setVisible(true);// 设置可见
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setFont(new Font("宋体", Font.BOLD, 24));
        g.drawString(getTitle(), 100, 0);
        g.setColor(Color.MAGENTA);
        TextUtil.drawStringWithBaseline(g, "hello, world", 200, 100);
        TextUtil.drawStringWithAscent(g, "hello, world", 200, 150);
        TextUtil.drawStringWithHeight(g, "hello, world", 200, 200);
        TextUtil.drawStringWithRect(g, "hello, world", 200, 250);
        int x = 200;
        int y = 300;
        Point point = new Point(x, y);
        for (int i = 0; i < 5; i++) {
            point = TextUtil.drawString(g, "hello, world", x, point.y);
        }
    }

}
