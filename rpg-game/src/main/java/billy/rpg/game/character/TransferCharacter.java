package billy.rpg.game.character;

import billy.rpg.game.GameFrame;
import billy.rpg.game.screen.MapScreen;


public class TransferCharacter extends BaseCharacter {
    private int curFrame;  // 步数

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public void initPos(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
    }

    private void increaseCurFrame() {
        curFrame++;
        if (curFrame == 4) {
            curFrame = 0;
        }
    }

    public int getCurFrame() {
        return curFrame;
    }

    private long lastTime = System.currentTimeMillis();

    /**
     * 传送门的自动切换
     */
    public void move() {
        long now = System.currentTimeMillis();
        if (now - lastTime < 400) {
            return;
        }
        lastTime = now;
        if (GameFrame.getInstance().getCurScreen() instanceof MapScreen) {
            increaseCurFrame();
        }

    }

}

