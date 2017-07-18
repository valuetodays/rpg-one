package billy.rpg.game.loader;

import billy.rpg.game.constants.AnimationConstant;
import billy.rpg.resource.animation.AnimationLoader;
import billy.rpg.resource.animation.AnimationMetaData;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.URL;
import java.util.*;

/**
 * @author liulei@bshf360.com
 * @since 2017-07-10 11:06
 */
public class AnimationDataLoader {
    private static final Logger LOG = Logger.getLogger(AnimationDataLoader.class);

    private Map<Integer, AnimationMetaData> animationMap = new HashMap<>();

    public void load() throws Exception {
        List<String> aniFilePaths = load0();
        if (aniFilePaths == null) {
            throw new RuntimeException("No ani file(s) found.");
        }

        LOG.debug("read data from " + aniFilePaths);

        for (String aniFilePath : aniFilePaths) {
            AnimationMetaData amd = AnimationLoader.load(aniFilePath);
            animationMap.put(amd.getNumber(), amd);
        } // end of for
    }


    private List<String> load0() {
        Enumeration<URL> urls = null;
        List<String> list = new ArrayList<>();
        try {
            urls = Thread.currentThread().getContextClassLoader().getResources("");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        while (urls.hasMoreElements()) {
            URL url = urls.nextElement();
            String protocol = url.getProtocol();
            if ("file".equals(protocol)) {
                String filepath = url.getPath();
                String packagename = filepath + "animation/";
                File file = new File(packagename);
                if (!file.exists()) {
                    continue;
                }
                File[] listFiles = file.listFiles(filterMap());

                for (File f : listFiles) {
                    String tmp = f.getPath();
                    LOG.debug("map loaded:" + f.getPath());
                    list.add(tmp);
                }
            }
        }

        if (list.isEmpty()) {
            return null;
        }

        return list;
    }


    /**
     * 过滤指定的ani文件
     */
    private static FileFilter filterMap() {
        FileFilter filter = new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                //  we want the file [1: '.ani' ]
                if (pathname.getPath().endsWith(AnimationConstant.EXT)) {
                    return true;
                }
                return false;
            }
        };

        return filter;
    }

    public Map<Integer, AnimationMetaData> getAnimationMap() {
        return Collections.unmodifiableMap(animationMap);
    }
}
