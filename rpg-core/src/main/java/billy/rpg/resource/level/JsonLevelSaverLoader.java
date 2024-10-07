package billy.rpg.resource.level;

import billy.rpg.common.constant.ToolsConstant;
import billy.rpg.common.util.JsonUtil;
import com.alibaba.fastjson.JSON;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;

/**
 * @author lei.liu
 * @since 2018-11-20 18:19:06
 */
public class JsonLevelSaverLoader implements LevelSaverLoader {
    private static final Logger LOG = Logger.getLogger(JsonLevelSaverLoader.class);

    @Override
    public void save(String filepath, LevelMetaData levelMetaData) throws IOException {

        FileUtils.write(new File(filepath), JsonUtil.toPrettyJsonString(levelMetaData));
        LOG.debug("saved file `{"+filepath+"}`.");
    }

    @Override
    public LevelMetaData load(String filepath) throws IOException {
        String s = FileUtils.readFileToString(new File(filepath), ToolsConstant.CHARSET);
        LevelMetaData levelMetaData = JSON.parseObject(s, LevelMetaData.class);
        //
        return levelMetaData;
    }
}
