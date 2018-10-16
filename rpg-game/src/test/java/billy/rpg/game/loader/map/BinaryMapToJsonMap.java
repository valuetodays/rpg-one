package billy.rpg.game.loader.map;

import billy.rpg.common.constant.ToolsConstant;
import billy.rpg.resource.map.MapMetaData;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * @author lei.liu@datatist.com
 * @since 2018-10-16 18:20:24
 */
public class BinaryMapToJsonMap {
    @Test
    public void test() throws IOException {
        MapDataLoader binaryMapDataLoader  = new BinaryMapDataLoader();
        binaryMapDataLoader.load();
        Map<String, MapMetaData> mapCollections = binaryMapDataLoader.getMapCollections();
        System.out.println(mapCollections);
        mapCollections.values().forEach(e -> {
            String s = "d:/tmp/json/" + e.getMapId() + new JsonMapDataLoader().getFileExt();
            try {
                FileUtils.write(new File(s),
                        JSON.toJSONString(e, SerializerFeature.PrettyFormat, SerializerFeature.DisableCircularReferenceDetect),
                        Charset.forName(ToolsConstant.CHARSET));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
    }
}
