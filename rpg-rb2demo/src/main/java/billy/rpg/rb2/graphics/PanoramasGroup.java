package billy.rpg.rb2.graphics;

import billy.rpg.rb2.RB2Panel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.List;

/**
 * @author liulei@bshf360.com
 * @since 2017-12-18 12:40
 */
public class PanoramasGroup {
    private BufferedImage panoramas;
    private BufferedImage panoramas2;
    private BufferedImage panoramas3;
    private BufferedImage panoramas4;

    /**
     * 按顺序显示的图形
     * 依次是远景 全景 近景
     */
    public List<BufferedImage> toShowList() {
        return Arrays.asList(panoramas3, panoramas, panoramas2);
    }

    public BufferedImage getPanoramas() {
        return panoramas;
    }

    public void setPanoramas(BufferedImage panoramas) {
        this.panoramas = panoramas;
    }

    public BufferedImage getPanoramas2() {
        return panoramas2;
    }

    public void setPanoramas2(BufferedImage panoramas2) {
        this.panoramas2 = panoramas2;
    }

    public BufferedImage getPanoramas3() {
        return panoramas3;
    }

    public void setPanoramas3(BufferedImage panoramas3) {
        this.panoramas3 = panoramas3;
    }

    public BufferedImage getPanoramas4() {
        return panoramas4;
    }

    public void setPanoramas4(BufferedImage panoramas4) {
        this.panoramas4 = panoramas4;
    }

    public BufferedImage getPanoramasWalk() {
        return getPanoramas4();
    }

    public void draw(Graphics g, int offsetX, int offsetY) {
        BufferedImage panoramas3 = getPanoramas3();
        BufferedImage panoramas = getPanoramas();

        g.drawImage(panoramas3, 0, 0, RB2Panel.PANEL_WIDTH, RB2Panel.PANEL_HEIGHT,
                offsetX, offsetY, offsetX + RB2Panel.PANEL_WIDTH, offsetY + RB2Panel.PANEL_HEIGHT, null);
        g.drawImage(panoramas, 0, 0, RB2Panel.PANEL_WIDTH, RB2Panel.PANEL_HEIGHT,
                offsetX, offsetY, offsetX + RB2Panel.PANEL_WIDTH, offsetY + RB2Panel.PANEL_HEIGHT, null);
    }

    public void draw2(Graphics g, int offsetX, int offsetY) {
        BufferedImage panoramas2 = getPanoramas2();
        g.drawImage(panoramas2, 0, 0, RB2Panel.PANEL_WIDTH, RB2Panel.PANEL_HEIGHT,
                offsetX, offsetY, offsetX + RB2Panel.PANEL_WIDTH, offsetY + RB2Panel.PANEL_HEIGHT, null);
    }


}
