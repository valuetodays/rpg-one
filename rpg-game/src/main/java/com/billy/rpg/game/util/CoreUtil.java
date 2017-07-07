package com.billy.rpg.game.util;

/**
 * @author liulei@bshf360.com
 * @since 2017-07-07 13:57
 */
public class CoreUtil {
    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
