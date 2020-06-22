package billy.rpg.game.core.config;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.PropertyPlaceholderHelper;

/**
 * @author lei.liu
 * @since 2019-04-24 17:56
 */
public final class ParamHolder {

    private static final Map<String, String> map = new HashMap<>();

    private static final PropertyPlaceholderHelper PROPERTY_PLACEHOLDER_HELPER =
            new PropertyPlaceholderHelper("${", "}");

    private ParamHolder() {

        StringBuilder sbDebugMsg = new StringBuilder();
        sbDebugMsg.append("****************************\n");
        // 【步骤一】读取配置文件至Map中
        Properties properties = new Properties();
        try {
            properties.load(new InputStreamReader(
                    this.getClass().getResourceAsStream("/game-config.properties"),
                    StandardCharsets.UTF_8)
            );
        } catch (IOException e) {
            e.printStackTrace();
        }

        Map<String, String> bundleMap = properties.keySet().stream()
                .collect(Collectors.toMap(k -> (String)k,
                k -> properties.getProperty((String)k)));

        // 【步骤二】处理value含有通配符的情况
        final Properties propertiesForPlaceHolder = new Properties();
        propertiesForPlaceHolder.putAll(bundleMap); // 添加系统变量
        Map<String, String> mapWithoutPlaceHolder = bundleMap.entrySet().stream()
            .collect(Collectors.toMap(Map.Entry::getKey, kv -> PROPERTY_PLACEHOLDER_HELPER.replacePlaceholders(kv.getValue(), propertiesForPlaceHolder)
        ));

        // 【步骤三】将map中的key中的_去除并将下一个字符转大写
        Map<String, String> mapWithoutUnderScoreInKey = mapWithoutPlaceHolder.entrySet().stream()
            .collect(Collectors.toMap(kv -> camelStyle(kv.getKey()), Map.Entry::getValue));

        Map<String, String> mapAll = new HashMap<>();
        mapAll.putAll(mapWithoutPlaceHolder);
        mapAll.putAll(mapWithoutUnderScoreInKey);

        sbDebugMsg.append("** ===all parameters===\n");
        for (Map.Entry<String, String> entry : mapAll.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            sbDebugMsg.append("* ").append(key).append("=").append(value).append("\n");
        }
        sbDebugMsg.append("****************************\n");
        System.out.println(sbDebugMsg);

        map.putAll(mapAll);
    }

    public static Map<String, String> getMap() {
        return map;
    }

    static {
        new ParamHolder();
    }

    private static String capitaliseFirst(final String s) {
        if (s == null) {
            return null;
        }
        if (s.length() == 0) {
            return s;
        }
        StringBuilder cap = new StringBuilder(s.substring(0, 1).toUpperCase());
        if (s.length() > 1) {
            cap.append(s.substring(1));
        }
        return cap.toString();
    }

    private static String camelStyle(String text) {
        StringBuilder camelString = new StringBuilder();
        String[] split = StringUtils.split(text.toLowerCase(), "_");
        String s = null;
        for (int i = 0; i < split.length; i++) {
            s = split[i];
            if (i > 0) {
                s = capitaliseFirst(s);
            }
            camelString.append(s);
        }

        return camelString.toString();
    }
}

