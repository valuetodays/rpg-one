package game.imageoperation;

import javax.swing.*;
import java.awt.*;

/**
 * 相对长度的进度条
 *
 * @author lei.liu@datatist.com
 * @since 2018-11-08 17:21:30
 */
public class RelativeProgressFrame extends JFrame {
    private static final long serialVersionUID = 3599269994527144637L;

    public static void main(String[] args) {
        new RelativeProgressFrame();
    }

    public RelativeProgressFrame() {
        this.setTitle("相对长度的进度条");
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
        Graphics2D g2d = (Graphics2D)g;
        g2d.setFont(new Font("宋体", Font.BOLD, 24));
        g2d.drawString(getTitle(), 100, 50);

        g2d.drawRect(100, 100, 500, 300);
        int[] gray = new int[]{100, 100, 87, 75, 62, 50};
        int[] active = new int[]{100, 87, 75, 62, 50, 37};
        Color[] colors = new Color[]{Color.BLUE, Color.cyan, Color.GREEN, Color.RED, Color.ORANGE, Color.MAGENTA};
        for (int i = 0; i < gray.length; i++) {
            g2d.setColor(Color.GRAY);
            g2d.fillRect(100, 100 + i*30, gray[i] * 5, 20);
            g2d.setColor(colors[i]);
            g2d.fillRect(100, 100 + i*30, active[i] * 5, 20);
        }
        // 附加一个渐变矩形
        GradientPaint gradientPaint = new GradientPaint(100, 180, Color.RED, 190, 200, Color.YELLOW);
        g2d.setPaint(gradientPaint);
        g2d.fillRect(100, 100 + gray.length*30, 90, 20);
    }

}
