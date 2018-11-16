package game.billy.fmj;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import billy.rpg.game.core.IGameCanvas;
import billy.rpg.game.core.IGameFrame;
import billy.rpg.game.core.platform.image.IGameImage;


public class AndroidCanvas extends Canvas implements IGameCanvas {
    private Canvas canvas;

    public AndroidCanvas() {
        this.canvas = new Canvas();
    }

    @Override
    public void drawBitmap(IGameFrame gameFrame, IGameImage gameImage, int left, int top) {
        canvas.drawBitmap((Bitmap)gameImage.getRealImageObject(), 1.0f * left, 1.0f * top, null);
    }

    public Canvas getCanvas() {
        return canvas;
    }
}
