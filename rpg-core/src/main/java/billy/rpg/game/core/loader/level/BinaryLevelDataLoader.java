package billy.rpg.game.core.loader.level;

import billy.rpg.resource.level.BinaryLevelSaverLoader;
import billy.rpg.resource.level.LevelSaverLoader;

/**
 * @author lei.liu@datatist.com
 * @since 2018-11-20 18:22:47
 */
public class BinaryLevelDataLoader extends LevelDataLoader {
    @Override
    public String getFileDir() {
        return "/assets/level/binary/";
    }

    @Override
    public String getFileExt() {
        return ".lvl";
    }

    @Override
    public LevelSaverLoader getSaverLoader() {
        return new BinaryLevelSaverLoader();
    }
}
