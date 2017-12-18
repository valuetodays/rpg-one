package billy.rpg.rb2;

import billy.rpg.rb2.graphics.GraphicsHolder;

/**
 * @author liulei@bshf360.com
 * @since 2017-12-18 16:09
 */
public class GameReourceHolder {
    private static GameReourceHolder gameReourceHolder = new GameReourceHolder();
    private GraphicsHolder graphicsHolder = new GraphicsHolder();

    private GameReourceHolder() {
        graphicsHolder.init();
        graphicsHolder.getPanoramasHolder().changeTo("beginning");
    }

    public static GameReourceHolder getInstance() {
        return gameReourceHolder;
    }

    public GraphicsHolder getGraphicsHolder() {
        return graphicsHolder;
    }
}
