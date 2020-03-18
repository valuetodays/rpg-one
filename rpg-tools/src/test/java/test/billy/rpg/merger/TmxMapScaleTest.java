package test.billy.rpg.merger;

import billy.rpg.common.util.MapScaleUtil;
import billy.rpg.resource.map.MapMetaData;
import billy.rpg.resource.map.TmxMapSaverLoader;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class TmxMapScaleTest {

    private MapTransferTest mapTransferTest = new MapTransferTest();

    /**
     * 将伏魔记地图放大三倍并重新保存
     */
    @Test
    public void testScaleMapAndSave() throws Exception {
        File file = new File("E:\\temp-file\\tmx\\1-1.tmx");
        MapMetaData mapMetaData = new TmxMapSaverLoader().load(file.getPath());

        int scale = 3;

        int[][] scaledBgLayer = MapScaleUtil.scaleMap(mapMetaData.getBgLayer(), scale);
        int[][] scaledWalkLayer = MapScaleUtil.scaleMap(mapMetaData.getWalk(), scale);

        List<int[][]> layers = new ArrayList<>();
        layers.add(scaledBgLayer);
        layers.add(scaledWalkLayer);

        mapMetaData.setWidth(mapMetaData.getWidth()*scale);
        mapMetaData.setHeight(mapMetaData.getHeight()*scale);
        mapMetaData.setLayers(layers);

        String dstTmxFilePath = new File(file.getParentFile(), "1-1.s3.tmx").getPath();
        mapTransferTest.writeAsTmxFile(mapMetaData, dstTmxFilePath);
    }

}
