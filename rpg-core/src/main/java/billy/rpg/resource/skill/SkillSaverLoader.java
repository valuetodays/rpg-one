package billy.rpg.resource.skill;

import java.io.IOException;

public interface SkillSaverLoader {
    /**
     * save skill to file
     * @param filepath filepath
     */
    void save(String filepath, SkillMetaData skillMetaData) throws IOException;

    /**
     *
     * @param filepath filepath
     */
    SkillMetaData load(String filepath) throws IOException;
}
