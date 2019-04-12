package game;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.net.URL;

/**
 * 测试音频播放器
 *
 * @author liulei
 * @since 2017-12-21 11:15
 */
public class AudioPlayerTest {

    /**
     * 说明：路径前加上/会导致找不到音频文件
     * "/assets/audio/game_cover.mp3"  --> fail
     * "assets/audio/game_cover.mp3"   --> success
     */
    @Test
    public void testAsyncAudioPlayer() {
        URL resource = Thread.currentThread().getContextClassLoader().getResource("assets/audio/game_cover.mp3");
        Assert.assertNotNull("mp3路径不正确", resource);
        String path = resource.getPath();
        Assert.assertNotNull(path);
        File file = new File(path);
        Assert.assertNotNull(file);
        Assert.assertTrue(file.exists());
//        MP3Player.play(file.getPath());
    }
}
