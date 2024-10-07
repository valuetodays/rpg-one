package billy.rpg.game.core.loader.level;

import billy.rpg.resource.level.JsonLevelSaverLoader;
import billy.rpg.resource.level.LevelSaverLoader;

/**
 * @author lei.liu
 * @since 2018-11-20 18:22:47
 */
public class JsonLevelDataLoader extends LevelDataLoader {
    @Override
    public String getFileDir() {
        return "/assets/level/json/";
    }

    @Override
    public String getFileExt() {
        return ".jlvl";
    }

    @Override
    public LevelSaverLoader getSaverLoader() {
        return new JsonLevelSaverLoader();
    }
}
