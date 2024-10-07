package billy.rpg.game.core.loader.map;

import billy.rpg.resource.map.JsonMapSaverLoader;
import billy.rpg.resource.map.MapSaverLoader;

/**
 * @author lei.liu
 * @since 2018-10-16 17:43:01
 */
public class JsonMapDataLoader extends MapDataLoader {
    @Override
    public String getFileDir() {
        return "/assets/map/json/";
    }

    @Override
    public String getFileExt() {
        return ".map.json";
    }

    @Override
    public MapSaverLoader getSaverLoader() {
        return new JsonMapSaverLoader();
    }

}
