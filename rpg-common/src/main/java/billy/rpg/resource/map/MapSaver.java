package billy.rpg.resource.map;

/**
 * @author liulei@bshf360.com
 * @since 2017-05-06 13:30
 */
public abstract class MapSaver {

    /**
     * save map to specified file
     *
     *
     * @param mapFilePath map filepath
     * @param mapMetaData data to save
     */
    public abstract void save(String mapFilePath, MapMetaData mapMetaData);
}
