package billy.rpg.resource.animation;

import billy.rpg.common.constant.ToolsConstant;
import billy.rpg.common.exception.UnimplementationException;
import com.alibaba.fastjson.JSON;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class JsonAnimationSaverLoader implements AnimationSaverLoader {
    private final Logger logger = Logger.getLogger(getClass());

    @Override
    public AnimationMetaData load(String aniFilePath) throws IOException {
        String s = FileUtils.readFileToString(new File(aniFilePath), ToolsConstant.CHARSET);
        AnimationMetaData metaData = JSON.parseObject(s, AnimationMetaData.class);
        URL resource = getClass().getResource("/animation/" + metaData.getNumber());
        List<BufferedImage> images = IntStream.range(0, metaData.getImageCount()).mapToObj(e -> {
            String path = null;
            try {
                path = resource.getPath() + "/" + e + ".png";
                return ImageIO.read(new File(path));
            } catch (IOException e1) {
                logger.error("exception when read file: " + path, e1);
            }
            return null;
        }).collect(Collectors.toList());
        metaData.setImages(images);
        return metaData;
    }

    @Override
    public void save(String aniFilePath, AnimationMetaData animationMetaData) {
        throw new UnimplementationException();
    }
}
