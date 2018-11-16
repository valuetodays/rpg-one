package game;

import org.junit.Assert;
import org.junit.Test;

import java.net.URL;

import billy.rpg.game.core.util.MP3Player;

/**
 * 测试音频播放器
 *
 * @author liulei@bshf360.com
 * @since 2017-12-21 11:15
 */
public class AudioPlayerTest {

    /**
     * 说明：路径前加上/会导致找不到音频文件
     * "/audio/game_cover.mp3"  --> fail
     * "audio/game_cover.mp3"   --> success
     */
    @Test
    public void testAsyncAudioPlayer() {
        URL resource = Thread.currentThread().getContextClassLoader().getResource("audio/game_cover.mp3");
        Assert.assertTrue("mp3路径不正确", resource != null);
        MP3Player.play(resource.getPath());
    }
}
