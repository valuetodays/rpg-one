package billy.rpg.resource.animation;

import java.io.IOException;

public interface AnimationSaverLoader {
    AnimationMetaData load(String aniFilePath) throws IOException;
    void save(String aniFilePath, AnimationMetaData animationMetaData) throws IOException;
}
