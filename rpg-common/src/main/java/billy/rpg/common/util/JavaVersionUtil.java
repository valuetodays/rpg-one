package billy.rpg.common.util;

/**
 * validation of java version
 *
 * @author liulei
 * @since 2017-05-31 20:05
 */
public class JavaVersionUtil {
    private JavaVersionUtil() {}

    private static int getJavaVersion() {
        int version = 0;

        try {
            Class.forName("java.lang.Override");
            version = 5;
            Class.forName("java.awt.Desktop");
            version = 6;
            Class.forName("java.util.concurrent.LinkedTransferQueue");
            version = 7;
            Class.forName("java.time.Clock");
            version = 8;
        } catch (Exception e) {
            // fall through
        }

        return version;
    }


    public static void validateJava() {
        int jdkVersion = getJavaVersion();
        if (jdkVersion < 7) {
            throw new RuntimeException("The java you use is too old, please upgrade to 1.7 or greater.");
        }
    }

}
