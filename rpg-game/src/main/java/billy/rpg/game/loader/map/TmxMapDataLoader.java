package billy.rpg.game.loader.map;

import billy.rpg.resource.map.MapSaverLoader;
import billy.rpg.resource.map.TmxMapSaverLoader;

/**
 * @author lei.liu@datatist.com
 * @since 2018-10-16 17:43:01
 */
public class TmxMapDataLoader extends MapDataLoader {
    @Override
    public String getFileDir() {
        return "/map/tmx/";
    }

    @Override
    public String getFileExt() {
        return ".tmx";
    }

    @Override
    public MapSaverLoader getSaverLoader() {
        return new TmxMapSaverLoader();
    }

}
