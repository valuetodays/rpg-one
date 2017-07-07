package com.billy.rpg.common.util;

import javax.swing.*;
import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.awt.image.ImageObserver;

/**
 *
 * @author liulei
 * @since 2017-04-28 17:44
 */
public class ImageUtil {


    /**
     * gray an image
     * @param srcImg src image
     */
    public static BufferedImage toGray(BufferedImage srcImg){
        return new ColorConvertOp(ColorSpace.getInstance(ColorSpace.CS_GRAY), null).filter(srcImg, null);
    }

    /**
     * icon to bufferedImage
     * @param icon icon
     */
    public static BufferedImage getBufferedImage(ImageIcon icon){
        int width = icon.getIconWidth();
        int height = icon.getIconHeight();
        ImageObserver observer = icon.getImageObserver();
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics gc = bufferedImage.createGraphics();
        gc.drawImage(icon.getImage(), 0, 0, observer);
        return bufferedImage;
    }

    /**
     * bit-reverse operation of a byte array
     * @param b the original array
     * @return reversed array
     */
    public static byte[] reverseBytes(byte[] b) {
        if (b == null) {
            return null;
        }

        int len = b.length;
        byte[] newByte = new byte[len];
        System.arraycopy(b, 0,
                newByte, 0, len);
        for (int i = 0; i < len; i++) {
            newByte[i] = (byte)~b[i];
        }

        return newByte;
    }

}
