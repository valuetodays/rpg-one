package com.billy.rpg.resource.box;

import org.apache.commons.io.IOUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @author liulei
 * @since 2017-05-16 19:11
 */
public class BoxImageLoader {
    private BoxMetaData boxMetaData;

    public BoxMetaData loadBoxes() {
        boxMetaData = new BoxMetaData();
        //
        final String npcPath = "/Images/character/box/";

        try {
            InputStream closedIs = BoxImageLoader.class.getResourceAsStream(npcPath + "box_closed.png");
            Image closeImg = ImageIO.read(closedIs);
            IOUtils.closeQuietly(closedIs);
            boxMetaData.setClosedImage(closeImg);

            InputStream openIs = BoxImageLoader.class.getResourceAsStream(npcPath + "box_open.png");
            Image openImg = ImageIO.read(openIs);
            IOUtils.closeQuietly(openIs);
            boxMetaData.setOpenImage(openImg);
        } catch (IOException e) {
            e.printStackTrace();
            boxMetaData = null;
        }

        if (boxMetaData == null) {
            throw new RuntimeException("加载box出错");
        }

        return boxMetaData;
    }

    public BufferedImage getImageOf(int tileNum) {
        if (tileNum == getOpenImageNum()) {
            return (BufferedImage) boxMetaData.getOpenImage();
        } else if (tileNum == getClosedImageNum()) {
            return (BufferedImage) boxMetaData.getClosedImage();
        }

        throw new RuntimeException("box编号错误");
    }

    private int getOpenImageNum() {
        return 0xee; // 238
    }
    private int getClosedImageNum() {
        return 0xed; // 237
    }

    public String getOpenImageName() {
        return "box_open.png";
    }
    public String getClosedImageName() {
        return "box_open.png";
    }

    public BoxMetaData getBoxMetaData() {
        return boxMetaData;
    }


}
