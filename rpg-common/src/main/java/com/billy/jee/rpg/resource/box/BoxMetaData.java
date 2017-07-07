package com.billy.jee.rpg.resource.box;

import java.awt.*;

/**
 *
 * @author liulei
 * @since 2017-05-16 19:14
 */
public class BoxMetaData {
    private Image openImage;
    private Image closedImage;

    public Image getOpenImage() {
        return openImage;
    }

    public void setOpenImage(Image openImage) {
        this.openImage = openImage;
    }

    public Image getClosedImage() {
        return closedImage;
    }

    public void setClosedImage(Image closedImage) {
        this.closedImage = closedImage;
    }
}
