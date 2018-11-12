package billy.rpg.common.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * @author lei.liu@datatist.com
 * @since 2018-11-12 18:14:39
 */
public class JsonUtil {
    private JsonUtil(){};
    public static String toPrettyJsonString(Object object) {
        return JSON.toJSONString(object, SerializerFeature.PrettyFormat, SerializerFeature.DisableCircularReferenceDetect);
    }
}
