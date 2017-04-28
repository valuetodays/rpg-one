package com.billy.rpg.mapeditor;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

/**
 *
 * @author liulei
 * @date 2017-04-28 17:44
 */
public class ImageUtil {

    public static BufferedImage getBufferedImage(ImageIcon icon){
        int width = icon.getIconWidth();
        int height = icon.getIconHeight();
        ImageObserver observer = icon.getImageObserver();
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics gc = bufferedImage.createGraphics();
        gc.drawImage(icon.getImage(), 0, 0, observer);
        return bufferedImage;
    }
}
