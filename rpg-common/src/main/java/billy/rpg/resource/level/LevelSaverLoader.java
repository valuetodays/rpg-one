package billy.rpg.resource.level;

import java.io.IOException;

/**
 * @author lei.liu@datatist.com
 * @since 2018-11-20 18:18:17
 */
public interface LevelSaverLoader {
    /**
     * save level to file
     * @param filepath filepath
     */
    void save(String filepath, LevelMetaData levelMetaData) throws IOException;

    /**
     *
     * @param filepath filepath
     */
    LevelMetaData load(String filepath) throws IOException;
}
