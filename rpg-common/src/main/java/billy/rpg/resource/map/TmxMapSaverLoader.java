package billy.rpg.resource.map;

import billy.rpg.common.exception.UnimplementationException;
import org.mapeditor.core.Map;
import org.mapeditor.core.MapLayer;
import org.mapeditor.core.Tile;
import org.mapeditor.core.TileLayer;
import org.mapeditor.io.TMXMapReader;

import java.io.IOException;
import java.util.Optional;

/**
 * @author lei.liu@datatist.com
 * @since 2018-10-16 17:47:20
 */
public class TmxMapSaverLoader implements MapSaverLoader {
    @Override
    public void save(String filepath, MapMetaData mapMetaData) throws IOException {
        throw new UnimplementationException();
    }

    @Override
    public MapMetaData load(String filepath) throws IOException {
        try {
            Map mapLayers = new TMXMapReader().readMap(filepath);
            int width = mapLayers.getWidth();
            int height = mapLayers.getHeight();
            MapLayer layer = mapLayers.getLayer(0);
            if (layer instanceof TileLayer) {
                Integer map[][] = new Integer[width][height];
                TileLayer tileLayer = (TileLayer) layer;
                for (int j = 0; j < height; j++) {
                    for (int i = 0; i < width; i++) {
                        Tile tileAt = tileLayer.getTileAt(i, j);
//                        System.out.println( i + "," + j + "=" + (tileAt == null));
                        int id = Optional.ofNullable(tileAt).map(e -> e.getId()).orElse(0);
//                        System.out.print(id + ", ");
                        map[j][i] = id;
                    }
                }
            }

            MapMetaData mapMetaData = new MapMetaData();
            mapMetaData.setName("sdfsdfs");
            mapMetaData.setHeight(height);
            mapMetaData.setWidth(width);
            System.out.println("tileIdName:" + mapLayers.getTileSets().get(0).getName());

        } catch (Exception e) {
            throw new IOException(e.getMessage(), e);
        }
        return null;
    }
}
