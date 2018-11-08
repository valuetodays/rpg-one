package billy.rpg.game.imagetransform;

import billy.rpg.common.util.ImageUtil;

/**
 * 雾化效果
 * 原理: 在图像中引入一定的随机值, 打乱图像中的像素值
 *
 * @author liulei@bshf360.com
 * @since 2018-01-13 18:07
 */
public class ImageTransformFrame3 extends ImageOperationBaseFrame {
    private static final long serialVersionUID = 1L;
    
    public static void main(String[] args) {
        new ImageTransformFrame3();
    }

    public ImageTransformFrame3() {
        super("雾化效果");// 设置 标题
    }

    public void transForm() {
        imageTarget = ImageUtil.fogImage(imageOrig);
        repaint();
    }

}
