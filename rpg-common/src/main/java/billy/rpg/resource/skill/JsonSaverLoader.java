package billy.rpg.resource.skill;

import billy.rpg.common.constant.ToolsConstant;
import billy.rpg.common.util.JsonUtil;
import com.alibaba.fastjson.JSON;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;

public class JsonSaverLoader implements SkillSaverLoader {
    private final Logger logger = Logger.getLogger(getClass());

    @Override
    public void save(String filepath, SkillMetaData skillMetaData) throws IOException {
        FileUtils.write(new File(filepath), JsonUtil.toPrettyJsonString(skillMetaData));
        logger.debug("saved file `{"+filepath+"}`.");
    }

    @Override
    public SkillMetaData load(String filepath) throws IOException {
        String s = FileUtils.readFileToString(new File(filepath), ToolsConstant.CHARSET);
        SkillMetaData skillMetaData = JSON.parseObject(s, SkillMetaData.class);
        //
        return skillMetaData;
    }
}
