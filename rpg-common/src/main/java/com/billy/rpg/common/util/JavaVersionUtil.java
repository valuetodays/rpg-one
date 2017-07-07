package com.billy.rpg.common.util;

/**
 * validation of java version
 *
 * @author liulei
 * @since 2017-05-31 20:05
 */
public class JavaVersionUtil {
    private static int getJavaVersion() {
        try {
            Class.forName("java.time.Clock");
            return 8;
        } catch (Exception e) {
        }

        try {
            Class.forName("java.util.concurrent.LinkedTransferQueue");
            return 7;
        } catch (Exception e) {
        }

        try {
            Class.forName("java.awt.Desktop");
            return 6;
        } catch (Exception e) {
        }

        try {
            Class.forName("java.lang.Override");
            return 5;
        } catch (Exception e) {
        }

        return 0;
    }


    public static void validateJava() {
        int jdkVersion = getJavaVersion();
        if (jdkVersion < 7) {
            throw new RuntimeException("The java you use is too old, please upgrade to 1.7 or greater.");
        }
    }

}
