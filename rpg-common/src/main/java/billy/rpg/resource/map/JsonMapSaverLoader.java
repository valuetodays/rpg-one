package billy.rpg.resource.map;

import billy.rpg.common.constant.ToolsConstant;
import com.alibaba.fastjson.JSON;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * @author lei.liu@datatist.com
 * @since 2018-10-16 17:47:20
 */
public class JsonMapSaverLoader implements MapSaverLoader0 {
    @Override
    public void save(String filepath, MapMetaData mapMetaData) throws IOException {
        // TODO
        throw new RuntimeException("未完成");
    }

    @Override
    public MapMetaData load(String filepath) throws IOException {
        String s = FileUtils.readFileToString(new File(filepath), ToolsConstant.CHARSET);
        MapMetaData mapMetaData = JSON.parseObject(s, MapMetaData.class);
        //
        return mapMetaData;
    }
}
