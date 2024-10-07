package billy.rpg.resource.map;

import billy.rpg.common.constant.ToolsConstant;
import billy.rpg.common.exception.UnFinishException;
import com.alibaba.fastjson.JSON;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * @author lei.liu
 * @since 2018-10-16 17:47:20
 */
@Deprecated
public class JsonMapSaverLoader implements MapSaverLoader {
    @Override
    public void save(String filepath, MapMetaData mapMetaData) throws IOException {
        throw new UnFinishException();
    }

    @Override
    public MapMetaData load(String filepath) throws IOException {
        String s = FileUtils.readFileToString(new File(filepath), ToolsConstant.CHARSET);
        MapMetaData mapMetaData = JSON.parseObject(s, MapMetaData.class);
        //
        return mapMetaData;
    }
}
