package billy.rpg.game.core.platform.image;

import java.io.InputStream;

import billy.rpg.game.core.platform.graphics.IGameGraphics;

public interface IGameImage {
    void setImageData(Object o);

    InputStream getImage();

    Object getRealImageObject();

    int getWidth();
    int getHeight();

    IGameGraphics getGraphics();
}
