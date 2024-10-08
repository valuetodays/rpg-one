package billy.rpg.game.core.loader.animation;

import billy.rpg.resource.animation.AnimationSaverLoader;
import billy.rpg.resource.animation.JsonAnimationSaverLoader;

public class JsonAnimationDataLoader extends AnimationDataLoader {
    @Override
    public String getFileDir() {
        return "/assets/animation/json/";
    }

    @Override
    public String getFileExt() {
        return ".jani";
    }

    @Override
    public AnimationSaverLoader getSaverLoader() {
        return new JsonAnimationSaverLoader();
    }
}
