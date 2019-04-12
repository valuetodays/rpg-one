package game.loader.animation;

import billy.rpg.common.constant.ToolsConstant;
import billy.rpg.common.util.JsonUtil;
import billy.rpg.game.core.loader.animation.AnimationDataLoader;
import billy.rpg.game.core.loader.animation.BinaryAnimationDataLoader;
import billy.rpg.game.core.loader.animation.JsonAnimationDataLoader;
import billy.rpg.resource.animation.AnimationMetaData;
import org.apache.commons.io.FileUtils;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;

public class AnimationTransfer {
    @Test
    @Ignore
    public void testBinaryToJson() throws IOException {
        AnimationDataLoader dataLoader  = new BinaryAnimationDataLoader();
        dataLoader.load();
        Map<Integer, AnimationMetaData> animationMap = dataLoader.getAnimationMap();
        System.out.println(animationMap);
        animationMap.values().forEach(e -> {
            String s = "f:/tmp/aaaa/json/" + e.getNumber() + new JsonAnimationDataLoader().getFileExt();
            try {
                FileUtils.write(new File(s),
                        JsonUtil.toPrettyJsonString(e),
                        Charset.forName(ToolsConstant.CHARSET));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
    }

    @Test
    public void testJsonToBinary() {

    }
}
