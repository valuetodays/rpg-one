package billy.rpg.game.core.loader.animation;

import billy.rpg.resource.animation.AnimationSaverLoader;
import billy.rpg.resource.animation.BinaryAnimationSaverLoader;

public class BinaryAnimationDataLoader extends AnimationDataLoader {
    @Override
    public String getFileDir() {
        return "/assets/animation/binary/";
    }

    @Override
    public String getFileExt() {
        return ".ani";
    }

    @Override
    public AnimationSaverLoader getSaverLoader() {
        return new BinaryAnimationSaverLoader();
    }
}
