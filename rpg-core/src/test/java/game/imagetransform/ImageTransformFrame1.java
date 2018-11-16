package game.imagetransform;

import billy.rpg.common.util.ImageUtil;

/**
 * 底片效果(反色)
 *
 * @author liulei@bshf360.com
 * @since 2018-01-13 16:57
 */
public class ImageTransformFrame1 extends ImageOperationBaseFrame {
    private static final long serialVersionUID = 823121620167083704L;

    public static void main(String[] args) {
        new ImageTransformFrame1();
    }

    public ImageTransformFrame1() {
        super("底片效果");// 设置 标题
    }

    public void transForm() {
        imageTarget = ImageUtil.reverseImage(imageOrig);
        repaint();
    }

}
