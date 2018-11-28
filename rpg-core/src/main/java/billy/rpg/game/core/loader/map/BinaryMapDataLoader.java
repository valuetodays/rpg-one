package billy.rpg.game.core.loader.map;

import billy.rpg.resource.map.BinaryMapSaverLoader;
import billy.rpg.resource.map.MapSaverLoader;


/**
 * to load map
 *
 * @author liulei-frx
 * 
 * @since 2016-11-30 09:13:28
 */
public class BinaryMapDataLoader extends MapDataLoader {

    @Override
    public String getFileDir() {
        return "/assets/map/binary/";
    }

    @Override
    public String getFileExt() {
        return ".map";
    }

    @Override
    public MapSaverLoader getSaverLoader() {
        return new BinaryMapSaverLoader();
    }

}
