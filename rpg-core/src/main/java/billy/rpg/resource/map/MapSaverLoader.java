package billy.rpg.resource.map;

import java.io.IOException;

/**
 * @author lei.liu
 * @since 2018-10-16 17:30:33
 */
public interface MapSaverLoader {
    /**
     * save map to file
     * @param filepath filepath
     * @param mapMetaData data
     */
    void save(String filepath, MapMetaData mapMetaData) throws IOException;

    /**
     *
     * @param filepath filepath
     * @return MetaData
     */
    MapMetaData load(String filepath) throws IOException;
}
