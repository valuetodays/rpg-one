package billy.rpg.game.loader;

import billy.rpg.game.constants.MapConstant;
import billy.rpg.resource.map.MapLoader;
import billy.rpg.resource.map.MapMetaData;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.URL;
import java.util.*;


/**
 * to load map
 *
 * @author liulei-frx
 * 
 * @since 2016-11-30 09:13:28
 */
public class MapDataLoader {
    private static final Logger LOG = Logger.getLogger(MapDataLoader.class);

    // this field name can not be mapMap....
    private Map<String, MapMetaData> mapCollections = new HashMap<>();
    
    public void load() throws Exception {
        List<String> mapFilepaths = load0();
        if (mapFilepaths == null) {
            LOG.warn("No maps found. System exit.");
            throw new RuntimeException("No maps found.");
        }
        
        LOG.debug(mapFilepaths);

        for (String map : mapFilepaths) {
            MapMetaData mapMetaData = MapLoader.load(map);
            mapCollections.put(mapMetaData.getMapId(), mapMetaData);
        }
        
    }

    public Map<String, MapMetaData> getMapCollections() {
        return Collections.unmodifiableMap(mapCollections);
    }

    /*
    private MapDataLoaderBean toMapBean(final MapMetaData mapMetaData) {
        List<int[][]> layers = mapMetaData.getLayers();
        MapDataLoaderBean mapDataLoaderBean = new MapDataLoaderBean();
        mapDataLoaderBean.setName(mapMetaData.getMapName());
        mapDataLoaderBean.setWidth(mapMetaData.getWidth());
        mapDataLoaderBean.setHeight(mapMetaData.getHeight());
        mapDataLoaderBean.setBgLayer(layers.get(0));
        mapDataLoaderBean.setNpcLayer(layers.get(1));
        mapDataLoaderBean.setFgLayer(layers.get(2));
        mapDataLoaderBean.setWalk(layers.get(3));
        mapDataLoaderBean.setEvent(layers.get(4));
//        mapDataLoaderBean.initMapId(file.getName()); TODO
        mapDataLoaderBean.setTileId(mapMetaData.getTileId());
        mapDataLoaderBean.setMapId(mapMetaData.getMapId());

        return mapDataLoaderBean;
    }*/

    private List<String> load0() {
        Enumeration<URL> urls = null;
        List<String> list = new ArrayList<>();
        try {
            urls = Thread.currentThread().getContextClassLoader().getResources("");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        
        while (urls.hasMoreElements()) {
            URL url = urls.nextElement();
            String protocol = url.getProtocol();
            if ("file".equals(protocol)) {
                String filepath = url.getPath();
                String packagename = filepath + "map/";
                File file = new File(packagename);
                if (!file.exists()) {
                    continue;
                }
                File[] listFiles = file.listFiles(filterMap());

                for (File f : listFiles) {
                    String tmp = f.getPath();
                    LOG.debug("map loaded:" + f.getPath());
                    list.add(tmp);
                }
            }
        }

        if (list.isEmpty()) {
            return null;
        }
        
        return list;
    }

    /**
     * 过滤指定的地图文件
     *
     */
    private static FileFilter filterMap() {
        FileFilter filter = new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                //  we want the file  [1: file, 2: '.map' ]
                if (pathname.isFile()
                        && pathname.getPath().endsWith(MapConstant.MAP_EXT)
                        ) {
                    return true;
                }
                return false;
            }
        };

        return filter;
    }
    

}
