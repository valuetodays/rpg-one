package billy.rpg.game.core.platform.image;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.io.InputStream;

import billy.rpg.game.core.platform.graphics.AndroidGraphics;
import billy.rpg.game.core.platform.graphics.IGameGraphics;

public class AndroidImage implements IGameImage {
    private Bitmap image;


    @Override
    public void setImageData(Object o) {
        if (!(o instanceof Bitmap)) {
            throw new RuntimeException("illegal image format");
        }
        this.image = (Bitmap) o;
    }

    @Override
    public InputStream getImage() {
        return null;
    }

    @Override
    public Object getRealImageObject() {
        return image;
    }

    @Override
    public int getWidth() {
        return image.getWidth();
    }

    @Override
    public int getHeight() {
        return image.getHeight();
    }

    @Override
    public IGameGraphics getGraphics() {
        Canvas canvas = new Canvas();

        IGameGraphics gameGraphics = new AndroidGraphics();
        gameGraphics.setGraphics(canvas);
        return gameGraphics;
    }


}
