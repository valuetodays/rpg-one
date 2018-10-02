package billy.rpg.resource.goods;

import java.io.IOException;

/**
 * @author liulei-home
 * @since 2018-10-03 00:46
 */
public interface GoodsSaverLoader0 {
    /**
     * save goods to file
     * @param filepath filepath
     * @param goodsMetaData data
     */
    void save(String filepath, GoodsMetaData goodsMetaData);

    /**
     *
     * @param filepath filepath
     * @return MetaData
     */
    GoodsMetaData load(String filepath) throws IOException;
}
