package billy.rpg.game.core.platform.image;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;

import billy.rpg.game.core.platform.graphics.DesktopGraphics;
import billy.rpg.game.core.platform.graphics.IGameGraphics;

public class DesktopImage implements IGameImage {
    private BufferedImage image;


    @Override
    public void setImageData(Object o) {
        if (!(o instanceof BufferedImage)) {
            throw new RuntimeException("illegal image format: " + o.getClass().getName());
        }
        this.image = (BufferedImage) o;
    }

    @Override
    public InputStream getImage() {
        try {
            ByteArrayOutputStream bs = new ByteArrayOutputStream();
            ImageOutputStream imOut = ImageIO.createImageOutputStream(bs);
            ImageIO.write(image, "png", imOut);
            InputStream is = new ByteArrayInputStream(bs.toByteArray());
            return is;
        } catch (IOException e) {
            e.printStackTrace();
        }

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
        Graphics graphics = image.getGraphics();
        IGameGraphics gameGraphics = new DesktopGraphics();
        gameGraphics.setGraphics(graphics);
        return gameGraphics;
    }


}
