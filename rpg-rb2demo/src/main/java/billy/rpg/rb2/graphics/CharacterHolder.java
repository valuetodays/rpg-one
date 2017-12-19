package billy.rpg.rb2.graphics;

import java.awt.image.BufferedImage;

/**
 * @author liulei@bshf360.com
 * @since 2017-12-18 13:37
 */
public class CharacterHolder {
    private Player player = new Player();

    public void init() {
        player.init();
    }

    public Player getPlayer() {
        return player;
    }

    public BufferedImage getPlayerImage() {
        return player.getBiaoshi14();
    }

}
