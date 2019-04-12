package game.loader;

import billy.rpg.common.constant.ToolsConstant;
import billy.rpg.common.util.JsonUtil;
import billy.rpg.game.core.loader.skill.BinarySkillDataLoader;
import billy.rpg.game.core.loader.skill.JsonSkillDataLoader;
import billy.rpg.game.core.loader.skill.SkillDataLoader;
import billy.rpg.resource.skill.SkillMetaData;
import org.apache.commons.io.FileUtils;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;

public class JsonSkillDataLoaderTest {
    @Test
    @Ignore
    public void test() throws IOException {
        SkillDataLoader dataLoader  = new BinarySkillDataLoader();
        dataLoader.load();
        Map<Integer, SkillMetaData> map = dataLoader.getSkillMap();
        map.values().forEach(e -> {
            String s = "f:/tmp/aaaa/json/" + e.getNumber() + new JsonSkillDataLoader().getFileExt();
            try {
                FileUtils.write(new File(s),
                        JsonUtil.toPrettyJsonString(e),
                        Charset.forName(ToolsConstant.CHARSET));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
    }
}
