package billy.rpg.game.util;

import org.apache.commons.lang.StringUtils;

import java.net.URL;

/**
 * @author liulei@bshf360.com
 * @since 2017-07-07 13:57
 */
public class CoreUtil {
    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取音频文件的路径
     * @param audioPath audioPath
     */
    public static String getAudioPath(String audioPath) {
        if (StringUtils.isEmpty(audioPath)) {
            throw new RuntimeException("audioPath is null or empty");
        }
        if (audioPath.startsWith("/")) {
            audioPath = audioPath.substring(1);
        }
        URL resource = Thread.currentThread().getContextClassLoader().getResource(audioPath);
        if (resource == null) {
            throw new RuntimeException("no audio: " + audioPath);
        }
        return resource.getPath();
    }
}
