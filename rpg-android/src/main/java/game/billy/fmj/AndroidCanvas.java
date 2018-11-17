package game.billy.fmj;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import billy.rpg.game.core.IGameCanvas;
import billy.rpg.game.core.IGameFrame;
import billy.rpg.game.core.constants.GameConstant;
import billy.rpg.game.core.platform.image.IGameImage;


public class AndroidCanvas extends Canvas implements IGameCanvas {
    private Canvas canvas;
    private Bitmap bitmap;

    public AndroidCanvas() {
        bitmap = Bitmap.createBitmap(GameConstant.GAME_WIDTH, GameConstant.GAME_HEIGHT, Bitmap.Config.ARGB_8888);
        this.canvas = new Canvas(bitmap);

    }

    @Override
    public void drawBitmap(IGameFrame gameFrame, IGameImage gameImage, int left, int top) {
        canvas.drawBitmap((Bitmap)gameImage.getRealImageObject(), 1.0f * left, 1.0f * top, null);
    }

    public Bitmap getBitmap() {
        return bitmap;
    }
}
