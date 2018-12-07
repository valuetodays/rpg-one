package game.imageoperation;

import billy.rpg.common.util.ImageUtil;

/**
 * 黑白效果
 *
 * @author liulei@bshf360.com
 * @since 2018-01-13 18:07
 */
public class BlackWhiteImageOperationFrame extends ImageOperationBaseFrame {
    private static final long serialVersionUID = 624414065421747049L;

    public static void main(String[] args) {
        new BlackWhiteImageOperationFrame();
    }

    public BlackWhiteImageOperationFrame() {
        super("黑白效果");// 设置 标题
    }

    public void transForm() {
        imageTarget = ImageUtil.blackWhiteImage(imageOrig);
        repaint();
    }

}
