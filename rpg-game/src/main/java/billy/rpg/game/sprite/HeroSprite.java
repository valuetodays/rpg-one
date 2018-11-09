package billy.rpg.game.sprite;

import java.awt.image.BufferedImage;
import java.util.List;

public class HeroSprite {
    public static class Key {
        private final int x;
        private final int y;
        private final BufferedImage image;
        private final int show;
        private final int nShow;

        public Key(int x, int y, BufferedImage image, int show, int nShow) {
            this.x = x;
            this.y = y;
            this.image = image;
            this.show = show;
            this.nShow = nShow;
        }

        public int getX() {
            return this.x;
        }

        public int getY() {
            return this.y;
        }

        public BufferedImage getImage() {
            return this.image;
        }

        public int getShow() {
            return this.show;
        }

        public int getNShow() {
            return this.nShow;
        }

    }

    private List<Key> keyList;

    public List<Key> getKeyList() {
        return this.keyList;
    }

    public void setKeyList(List<Key> keyList) {
        this.keyList = keyList;
    }

}