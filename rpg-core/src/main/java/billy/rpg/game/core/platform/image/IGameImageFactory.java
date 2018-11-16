package billy.rpg.game.core.platform.image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import billy.rpg.game.core.platform.Platform;
import billy.rpg.game.core.util.CoreUtil;

public class IGameImageFactory {
    public static IGameImage getImage(String path) {
        IGameImage iGameImage = null;
        if (Platform.getType() == Platform.Type.ANDROID) {

        } else if (Platform.getType() == Platform.Type.DESKTOP) {
            try {
                BufferedImage image = ImageIO.read(new File(CoreUtil.getResourcePath(path)));
                iGameImage = new DesktopImage();
                iGameImage.setImageData(image);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (iGameImage == null) {
            throw new NullPointerException("no image: " + path);
        }
        return iGameImage;
    }

    public static IGameImage createImage(int width, int height, int type4byteAbgr) {
        IGameImage iGameImage = null;
        if (Platform.getType() == Platform.Type.ANDROID) {

        } else if (Platform.getType() == Platform.Type.DESKTOP) {
            BufferedImage paint = new BufferedImage(
                    width,
                    height,
                    type4byteAbgr);
            iGameImage = new DesktopImage();
            iGameImage.setImageData(paint);
        }
        if (iGameImage == null) {
            throw new NullPointerException("error createImage");
        }
        return iGameImage;
    }
}
