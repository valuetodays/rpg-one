package billy.rpg.standalone;

import java.awt.*;

/**
 * @author liulei@bshf360.com
 * @since 2017-12-15 16:32
 */
public class Sprite {
    Point drawPos; // 绘制位置
    Image image; // 图像

    public void draw(Graphics g) {
        System.out.println("draw sprite..");
        g.drawImage(image, drawPos.x, drawPos.y, null);
    }

    public Sprite() {
    }

    public Sprite(Point drawPos, Image image) {
        this.drawPos = drawPos;
        this.image = image;
    }
}
