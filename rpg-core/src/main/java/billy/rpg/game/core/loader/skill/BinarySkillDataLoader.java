package billy.rpg.game.core.loader.skill;

import billy.rpg.resource.skill.BinarySaverLoader;
import billy.rpg.resource.skill.SkillSaverLoader;

public class BinarySkillDataLoader extends SkillDataLoader {
    @Override
    public String getFileDir() {
        return "/assets/skill/binary/";
    }

    @Override
    public String getFileExt() {
        return ".skl";
    }

    @Override
    public SkillSaverLoader getSaverLoader() {
        return new BinarySaverLoader();
    }
}
