package billy.rpg.resource.map;

import billy.rpg.common.constant.ToolsConstant;
import org.apache.log4j.Logger;

/**
 * TODO
 * @author liulei@bshf360.com
 * @since 2017-11-29 15:08
 */
public class MapXmlSaver extends MapSaver {
    private static final Logger LOG = Logger.getLogger(MapSaverContext.class);
    private static final String MAP_HEADER = ToolsConstant.MAGIC_MAP;
    private static final String CHARSET = ToolsConstant.CHARSET;


    @Override
    public void save(String mapFilePath, MapMetaData mapMetaData) {

    }

}
