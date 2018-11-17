package billy.rpg.game.core.platform.graphics;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;


import billy.rpg.game.core.platform.image.IGameImage;

public class AndroidGraphics implements IGameGraphics {
    private Canvas canvas;
    private Paint paint = new Paint();

    @Override
    public void setGraphics(Object object) {
        if (!(object instanceof Canvas)) {
            throw new RuntimeException("illegal canvas format");
        }
        this.canvas = (Canvas) object;
    }


    @Override
    public void drawRect(int x, int y, int width, int height) {

        canvas.drawRect(1.0f * x, 1.0f * y, 1.0f * width, 1.0f *height, paint);
    }

    @Override
    public void drawString(String text, int x, int y) {
        canvas.drawText(text, 1.0f * x, 1.0f * y, paint);
    }

    @Override
    public void drawImage(IGameImage gameImage, int x, int y) {
        canvas.drawBitmap((Bitmap)gameImage.getRealImageObject(), 1.0f * x, 1.0f * y, paint);
    }

    @Override
    public void dispose() {
//        canvas.
    }

    @Override
    public void setFont(String fontFamily, int fontStyle, int fontSize) {
//        canvas.(new Font(fontFamily, fontStyle, fontSize));
    }

    @Override
    public void link(IGameImage gameImage) {
//        gameImage.setImageData(canvas);
    }
}
