package billy.rpg.game;

import billy.rpg.game.core.platform.image.DesktopImage;
import billy.rpg.game.core.platform.image.IGameImage;

public class DesktopImageFactory {
    private DesktopImageFactory(){}

    public static IGameImage create(Object object) {
        IGameImage image = new DesktopImage();
        image.setImageData(object);
        return image;
    }
}
