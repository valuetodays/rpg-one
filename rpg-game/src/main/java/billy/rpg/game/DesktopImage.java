package billy.rpg.game;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;

import billy.rpg.game.core.IGameImage;

public class DesktopImage implements IGameImage {
    private BufferedImage image;

    @Override
    public void setImageData(Object o) {
        if (!(o instanceof BufferedImage)) {
            throw new RuntimeException("illegal image format");
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
}
