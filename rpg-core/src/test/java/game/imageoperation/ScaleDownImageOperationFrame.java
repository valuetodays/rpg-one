package game.imageoperation;

import billy.rpg.common.util.ImageUtil;

import java.awt.*;

/**
 * @author lei.liu
 * @since 2018-11-08 10:54:14
 */
public class ScaleDownImageOperationFrame extends ImageOperationBaseFrame {
    private static final long serialVersionUID = -6925513451188025986L;

    public static void main(String[] args) {
        new ScaleDownImageOperationFrame();
    }

    public ScaleDownImageOperationFrame() {
        super("图像缩小");// 设置 标题
    }

    public void transForm() {
        imageTarget = ImageUtil.scaleDownImage(imageOrig, 2, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        repaint();
    }
}
