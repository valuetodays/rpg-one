package billy.rpg.game.core.loader.skill;

import billy.rpg.resource.skill.JsonSaverLoader;
import billy.rpg.resource.skill.SkillSaverLoader;

public class JsonSkillDataLoader extends SkillDataLoader {
    @Override
    public String getFileDir() {
        return "/skill/json/";
    }

    @Override
    public String getFileExt() {
        return ".jskl";
    }

    @Override
    public SkillSaverLoader getSaverLoader() {
        return new JsonSaverLoader();
    }
}
