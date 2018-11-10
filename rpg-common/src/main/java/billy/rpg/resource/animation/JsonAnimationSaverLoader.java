package billy.rpg.resource.animation;

import billy.rpg.common.constant.ToolsConstant;
import com.alibaba.fastjson.JSON;
import org.apache.commons.io.FileUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class JsonAnimationSaverLoader implements AnimationSaverLoader {
    @Override
    public AnimationMetaData load(String aniFilePath) throws IOException {
        String s = FileUtils.readFileToString(new File(aniFilePath), ToolsConstant.CHARSET);
        AnimationMetaData metaData = JSON.parseObject(s, AnimationMetaData.class);
        URL resource = getClass().getResource("/animation/" + metaData.getNumber());
        List<BufferedImage> images = IntStream.range(0, metaData.getImageCount()).mapToObj(e -> {
            try {
                String path = resource.getPath() + "/" + e + ".png";
                return ImageIO.read(new File(path));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            return null;
        }).collect(Collectors.toList());
        metaData.setImages(images);
        return metaData;
    }

    @Override
    public void save(String aniFilePath, AnimationMetaData animationMetaData) {
        throw new RuntimeException("未完成");
    }
}
