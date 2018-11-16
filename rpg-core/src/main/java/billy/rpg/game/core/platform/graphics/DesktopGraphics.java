package billy.rpg.game.core.platform.graphics;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import billy.rpg.common.exception.UnimplementationException;
import billy.rpg.game.core.platform.image.IGameImage;

public class DesktopGraphics implements IGameGraphics {
    private Graphics graphics;

    @Override
    public void setGraphics(Object object) {
        if (!(object instanceof Graphics)) {
            throw new RuntimeException("illegal graphics format");
        }
        this.graphics = (Graphics) object;
    }


    @Override
    public void drawRect(int x, int y, int width, int height) {
        graphics.drawRect(x, y, width, height);
    }

    @Override
    public void drawString(String text, int x, int y) {
        graphics.drawString(text, x, y);
    }

    @Override
    public void drawImage(IGameImage gameImage, int x, int y) {
        graphics.drawImage((BufferedImage)gameImage.getRealImageObject(), x, y, null);
    }

    @Override
    public void dispose() {
        graphics.dispose();
    }

    @Override
    public void setFont(Font font) {
        throw new UnimplementationException();
    }
}
