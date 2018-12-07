import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

/**
 * java 纹理填充特效源码
 *
 * https://download.csdn.net/download/chinafjfzlj/4447852
 */
public class TextureFillFrame extends JFrame {
    private static final long serialVersionUID = -2517163874591558289L;

    public static void main(String args[]) {
        TextureFillFrame frame = new TextureFillFrame();
        frame.setVisible(true);
    }

    public TextureFillFrame() {
        super();
        TextureFillPanel textureFillPanel = new TextureFillPanel();  // 创建图像面板对象
        this.setBounds(200, 160, 308, 230); // 设置窗体大小和位置
        this.add(textureFillPanel); // 在窗体上添加图像面板对象
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // 设置窗体关闭模式
        this.setTitle("纹理填充特效"); // 设置窗体标题
    }

    // 创建面板类
    class TextureFillPanel extends JPanel {
        private static final long serialVersionUID = -3647071187009000234L;

        public void paint(Graphics g) {
            BufferedImage image = new BufferedImage(200, 200,
                    BufferedImage.TYPE_INT_RGB);// 创建缓冲图像对象
            Graphics2D g2 = image.createGraphics();// 获得缓冲图像对象的绘图上下文对象
            g2.setColor(Color.BLUE);// 设置颜色
            g2.fillRect(0,0,90,90);// 绘制填充矩形
//            g2.setColor(Color.RED);// 设置颜色
//            g2.fillOval(95,95,90,90);// 绘制带填充色的圆形
            Rectangle2D rect = new Rectangle2D.Float(10, 10, 20, 20);// 创建Rectangle2D对象
            TexturePaint textPaint = new TexturePaint(image,rect);// 创建纹理填充对象
            Graphics2D graphics2 = (Graphics2D)g;// 转换paint()方法的绘图上下文对象
            graphics2.setPaint(textPaint);// 为绘图上下文对象设置纹理填充对象
            Rectangle2D.Float ellipse2 = new Rectangle2D.Float(45, 25, 200, 140);// 创建矩形对象
            graphics2.fill(ellipse2);// 绘制填充纹理的矩形
        }
    }
}
