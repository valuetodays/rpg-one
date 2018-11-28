package billy.rpg.game.core.loader.animation;

import billy.rpg.common.util.AssetsUtil;
import billy.rpg.resource.animation.AnimationMetaData;
import billy.rpg.resource.animation.AnimationSaverLoader;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public abstract class AnimationDataLoader {
    public abstract String getFileDir();
    public abstract String getFileExt();
    public abstract AnimationSaverLoader getSaverLoader();

    private Map<Integer, AnimationMetaData> animationMap = new HashMap<>();

    public void load() throws IOException {
        final String dir = getFileDir();
        String resourcePath = AssetsUtil.getResourcePath(dir);
        File aniFilePath = new File(resourcePath);
        // 只取一层文件夹
        File[] aniFilePaths = aniFilePath.listFiles(pathname -> pathname.getName().endsWith(getFileExt()));
        if (aniFilePaths == null) {
            throw new RuntimeException("No ani file(s) found.");
        }

        for (File aniFilePathFile : aniFilePaths) {
            AnimationMetaData amd = getSaverLoader().load(aniFilePathFile.getPath());
            animationMap.put(amd.getNumber(), amd);
        } // end of for
    }

    public Map<Integer, AnimationMetaData> getAnimationMap() {
        return Collections.unmodifiableMap(animationMap);
    }
}
