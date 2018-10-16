package billy.rpg.resource.goods;

import billy.rpg.common.constant.ToolsConstant;
import com.alibaba.fastjson.JSON;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * @author liulei-home
 * @since 2018-10-03 00:45
 */
public class JsonGoodsSaverLoader implements GoodsSaverLoader0 {

    /**
     * save goods to file
     * @param filepath filepath
     * @param goodsMetaData data
     */
    @Override
    public void save(String filepath, GoodsMetaData goodsMetaData) {
        // TODO
        throw new RuntimeException("未完成");
    }

    /**
     *
     * @param filepath filepath
     * @return MetaData
     */
    @Override
    public GoodsMetaData load(String filepath) throws IOException {
        String s = FileUtils.readFileToString(new File(filepath), ToolsConstant.CHARSET);
        GoodsMetaData goodsMetaData = JSON.parseObject(s, GoodsMetaData.class);
        //
        return goodsMetaData;
    }
}
