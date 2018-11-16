package billy.rpg.game.core.util;

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
     * @param resourcePath resourcePath
     */
    public static String getResourcePath(String resourcePath) {
        if ((resourcePath) == null) {
            throw new RuntimeException("audioPath is null or empty");
        }
        if (resourcePath.startsWith("/")) {
            resourcePath = resourcePath.substring(1);
        }
        URL resource = Thread.currentThread().getContextClassLoader().getResource(resourcePath);
        if (resource == null) {
            throw new RuntimeException("no audio: " + resourcePath);
        }
        return resource.getPath();
    }
}
