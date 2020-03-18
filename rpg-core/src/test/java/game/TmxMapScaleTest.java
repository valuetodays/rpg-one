package game;

import org.junit.Test;
import org.mapeditor.core.Map;
import org.mapeditor.core.MapLayer;
import org.mapeditor.io.TMXMapReader;

import java.io.File;
import java.util.List;

public class TmxMapScaleTest {
    @Test
    public void teste() throws Exception {
        File file = new File("E:\\temp-file\\1-1.tmx");
        Map mapTmx = new TMXMapReader().readMap(file.getPath());
        int width = mapTmx.getWidth();
        int height = mapTmx.getHeight();

        List<MapLayer> layers = mapTmx.getLayers();
        System.out.println(layers);
    }
}
