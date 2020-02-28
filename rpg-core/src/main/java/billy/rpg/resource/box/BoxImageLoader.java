package billy.rpg.resource.box;

import billy.rpg.common.util.AssetsUtil;
import java.awt.Image;
import java.awt.image.BufferedImage;

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
        final String boxPath = "/assets/Images/character/box/";

        Image closeImg = AssetsUtil.getResourceAsImage(boxPath + "box_closed.png");
        boxMetaData.setClosedImage(closeImg);

        Image openImg = AssetsUtil.getResourceAsImage(boxPath + "box_open.png");
        boxMetaData.setOpenImage(openImg);

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

    public static int getOpenImageNum() {
        return 0xee; // 238
    }
    public static int getClosedImageNum() {
        return 0xed; // 237
    }

    public String getOpenImageName() {
        return "box_open.png";
    }
    public String getClosedImageName() {
        return "box_closed.png";
    }

    public BoxMetaData getBoxMetaData() {
        return boxMetaData;
    }


}
