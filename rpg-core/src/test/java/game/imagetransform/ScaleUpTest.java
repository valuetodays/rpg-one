package game.imagetransform;

import java.awt.RenderingHints;

import billy.rpg.common.util.ImageUtil;

/**
 * @author lei.liu@datatist.com
 * @since 2018-11-08 10:54:14
 */
public class ScaleUpTest extends ImageOperationBaseFrame {
    private static final long serialVersionUID = -6925513451188025986L;

    public static void main(String[] args) {
        new ScaleUpTest();
    }

    public ScaleUpTest() {
        super("图像放大");// 设置 标题
    }

    public void transForm() {
        imageTarget = ImageUtil.scaleUpImage(imageOrig, 2.3f, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        repaint();
    }
}
