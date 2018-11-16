package billy.rpg.game.core.platform.graphics;

import java.awt.Font;

import billy.rpg.game.core.platform.image.IGameImage;

public interface IGameGraphics {
    void setGraphics(Object object);

    void drawRect(int x, int y, int width, int height);

    void drawString(String text, int x, int y);

    void drawImage(IGameImage image, int x, int y);

    void dispose();

    void setFont(Font font);
}
