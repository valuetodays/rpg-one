package billy.rpg.resource.map;

import billy.rpg.common.exception.UnFinishException;
import org.apache.commons.lang.StringUtils;
import org.mapeditor.core.*;
import org.mapeditor.io.TMXMapReader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author lei.liu@datatist.com
 * @since 2018-10-16 17:47:20
 */
public class TmxMapSaverLoader implements MapSaverLoader {
    enum Layer {
        bg, walk
    }

    @Override
    public void save(String filepath, MapMetaData mapMetaData) throws IOException {
        throw new UnFinishException("create map by Tiled please");
    }

    @Override
    public MapMetaData load(String filepath) throws IOException {
        try {
            File file = new File(filepath);
            Map mapTmx = new TMXMapReader().readMap(file.getPath());
            int width = mapTmx.getWidth();
            int height = mapTmx.getHeight();

            List<int[][]> layers = new ArrayList<>();
            layers.add(parseAsLayerData(mapTmx, Layer.bg));
            layers.add(parseAsLayerData(mapTmx, Layer.walk));
            Properties properties = mapTmx.getProperties();
            String name = properties.getProperty("name");

            MapMetaData mapMetaData = new MapMetaData();
            mapMetaData.setName(name);
            mapMetaData.setHeight(height);
            mapMetaData.setWidth(width);
            mapMetaData.setLayers(layers);
            String tilebmpFile = mapTmx.getTileSets().get(0).getTilebmpFile();
            mapMetaData.setTileId(new File(tilebmpFile).getName());
            mapMetaData.setMapId(file.getName().replace(".tmx", ""));
            return mapMetaData;
        } catch (Exception e) {
            e.printStackTrace();
            throw new IOException(e.getMessage());
        }
    }


    private int[][] parseAsLayerData(Map mapTmx, Layer layer) {
        List<MapLayer> layers = mapTmx.getLayers();
        Optional<MapLayer> dataOptional = layers.stream().filter(e -> StringUtils.equals(e.getName(), layer.name())).findFirst();
        if (!dataOptional.isPresent()) {
            throw new IllegalArgumentException("数据无效，缺少" + layer.name());
        }
        int[][] layerData = convertAsLayer(dataOptional.get(), mapTmx.getWidth(), mapTmx.getHeight());
        if (layerData == null) {
            throw new IllegalArgumentException("地图数据无效，缺少" + layer.name());
        }
        return layerData;
    }

    private int[][] convertAsLayer(MapLayer mapLayer, int width, int height) {
        if (mapLayer instanceof TileLayer) {
            int[][] map = new int[width][height];
            TileLayer tileLayer = (TileLayer) mapLayer;
            for (int j = 0; j < height; j++) {
                for (int i = 0; i < width; i++) {
                    Tile tileAt = tileLayer.getTileAt(i, j);
                    int id = Optional.ofNullable(tileAt).map(e -> e.getId()).orElse(0);
                    map[i][j] = id;
                }
            }
            return map;
        }
        return null;
    }
}
