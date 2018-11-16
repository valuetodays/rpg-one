package billy.rpg.game.core.platform.graphics;

import java.awt.Graphics;
import java.io.InputStream;

import billy.rpg.game.core.platform.Platform;

public class IGameGraphicsFactory {

    public static IGameGraphics getGraphics(Object image) {
        IGameGraphics gameGraphics = null;
        if (Platform.getType() == Platform.Type.ANDROID) {

        } else if (Platform.getType() == Platform.Type.DESKTOP) {
            gameGraphics = new DesktopGraphics();
        }
        if (gameGraphics == null) {
            throw new NullPointerException("no graphics");
        }
        return gameGraphics;
    }
}
