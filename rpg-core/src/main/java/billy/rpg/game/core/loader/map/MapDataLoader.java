package billy.rpg.game.core.loader.map;

import billy.rpg.common.util.AssetsUtil;
import billy.rpg.resource.map.MapMetaData;
import billy.rpg.resource.map.MapSaverLoader;
import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


/**
 * to load map
 *
 * @author liulei-frx
 * 
 * @since 2016-11-30 09:13:28
 */
public abstract class MapDataLoader {
    protected final Logger logger = Logger.getLogger(getClass());

    // this field name can not be mapMap....
    protected Map<String, MapMetaData> mapCollections = new HashMap<>();


    public abstract String getFileDir();
    public abstract String getFileExt();
    public abstract MapSaverLoader getSaverLoader();

    public void load() throws IOException {
        final String dir = getFileDir();
        String resourcePath = AssetsUtil.getResourcePath(dir);

        File file = new File(resourcePath);
        // 只取一层文件夹
        File[] files = file.listFiles(pathname -> pathname.getName().endsWith(getFileExt()));
        if (ArrayUtils.isEmpty(files)) {
            throw new RuntimeException("cannot find " + getFileExt() + " in " + dir);
        }

        for (File tmp : files) {
            logger.debug(tmp);
        }

        for (File mapFile : files) {
            MapMetaData mapMetaData = getSaverLoader().load(mapFile.getPath());
            mapCollections.put(mapMetaData.getMapId(), mapMetaData);
        }
    }

    public Map<String, MapMetaData> getMapCollections() {
        return mapCollections;
    }
}
