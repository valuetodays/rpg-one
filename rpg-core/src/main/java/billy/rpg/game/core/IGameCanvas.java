package billy.rpg.game.core;

import billy.rpg.game.core.platform.image.IGameImage;

public interface IGameCanvas {
    void drawBitmap(IGameFrame gameFrame, IGameImage gameImage, int left, int top);
}
