package com.billy.constants;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="http://blog.sina.com.cn/valuetodays">liulei-frx</a>
 * @date 2016-05-10 11:30
 * @since 2016-05-10 11:30
 */
public class CmdCodeConstant {
    private static final Map<String, Integer> mapCmdCode = new HashMap<>();
    static {
        String[] cmdName = {"set", "if", "showText",
                "return",
                "loadmap",
                "scenename"};
        for (int i = 0; i < cmdName.length; i++) {
            mapCmdCode.put(cmdName[i].toLowerCase(), i);
        }
    }

    ;

    /**
     * return the numeric code of a command, -1 returned when error occurs.
     * case in-sensitive
     * @param cmdName
     * @return
     */
    @Deprecated
    public static int getCommandCode(String cmdName) {
        String cmdName0 = cmdName.toLowerCase();
        if (mapCmdCode.containsKey(cmdName0)) {
            return mapCmdCode.get(cmdName0);
        }

        return -1;
    }
}
