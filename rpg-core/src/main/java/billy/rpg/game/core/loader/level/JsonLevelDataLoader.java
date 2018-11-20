package billy.rpg.game.core.loader.level;

import billy.rpg.resource.level.JsonLevelSaverLoader;
import billy.rpg.resource.level.LevelSaverLoader;

/**
 * @author lei.liu@datatist.com
 * @since 2018-11-20 18:22:47
 */
public class JsonLevelDataLoader extends Level2DataLoader {
    @Override
    public String getFileDir() {
        return "/level/json/";
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
