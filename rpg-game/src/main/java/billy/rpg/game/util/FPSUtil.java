package billy.rpg.game.util;

/**
 * @author liulei@bshf360.com
 * @since 2017-12-20 12:09
 */
public class FPSUtil {
    private long lastTime;
    private long delta;
    private int frameCount;
    private String frameRate;
    private static final int ONE_SECOND = 1000;

    public void init() {
        lastTime = System.currentTimeMillis();
        frameRate = "FPS 0";
        frameCount = 0;
    }

    public void calculate() {
        long current = System.currentTimeMillis();
        delta += current - lastTime;
        lastTime = current;
        frameCount++;
        if (delta > ONE_SECOND) {
            delta -= ONE_SECOND;
            frameRate = "FPS " + frameCount;
            frameCount = 0;
        }
    }

    public String getFrameRate() {
        return frameRate;
    }
}
