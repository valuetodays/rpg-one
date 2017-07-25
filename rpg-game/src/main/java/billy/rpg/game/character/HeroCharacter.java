package billy.rpg.game.character;

import billy.rpg.game.constants.GameConstant;
import billy.rpg.game.util.WalkUtil;


public class HeroCharacter extends BaseCharacter {
    private int curFrame;  // 步数
    private int direction; // 方向



    public int getNextPosX() {
        return posX + ((direction == DIRECTION_LEFT && posX > 0) ? -1 :
                ((direction == DIRECTION_RIGHT && posX < getWidth() - 1) ? 1 : 0)
        );
    }

    public int getNextPosY() {
        return posY + ((direction == DIRECTION_UP && posY > 0) ? -1 :
                ((direction == DIRECTION_DOWN && posY < getHeight() - 1) ? 1 : 0 )
        );
    }

    public void initPos(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
    }

    private void increaseCurFrame() {
        curFrame++;
        if (curFrame == 3) {
            curFrame = 0;
        }
    }

    public int getCurFrame() {
        return curFrame;
    }

    public int getPosX() {
        return posX;
    }
    public int getPosY() {
        return posY;
    }

    public void increaseX() {
        increaseCurFrame();
        int nextPosX = posX + 1;
        if ((nextPosX <= width - 1) && WalkUtil.isWalkable(nextPosX, posY)) {
            posX++;
        }
        direction = DIRECTION_RIGHT;
    }

    public void decreaseX() {
        increaseCurFrame();
        int nextPosX = posX - 1;
        if (nextPosX >= 0 && WalkUtil.isWalkable(nextPosX, posY)) {
            posX--;
        }

        direction = DIRECTION_LEFT;
    }

    public void increaseY() {
        increaseCurFrame();
        int nextPosY = posY + 1;
        if (nextPosY <= height - 1 && WalkUtil.isWalkable(posX, nextPosY)) {
            posY++;
        }
        direction = DIRECTION_DOWN;
    }

    public void decreaseY() {
        increaseCurFrame();
        int nextPosY = posY - 1;
        if (nextPosY >= 0 && WalkUtil.isWalkable(posX, nextPosY)) {
            posY--;
        }
        direction = DIRECTION_UP;
    }

    @Override
    public String toString() {
        return "[h=" + height + ", w=" + width + ", posX=" + posX + ", posY=" + posY + ", nextPosX="
                + getNextPosX() + ", nextPosY=" + getNextPosY() + ",dir=" + direction + "]";
    }

    public int getDirection() {
        return direction;
    }


    private long lastTime = System.currentTimeMillis();


    @Override
    public void move() {
        long now = System.currentTimeMillis();
        if (now - lastTime < 500) {
            return ;
        }
        lastTime = now;
        int i = GameConstant.random.nextInt(8 << 2);
        if (i % 7 == 1) {
            decreaseX();
        }
        if (i % 7 == 2) {
            decreaseY();
        }
        if (i % 7 == 3) {
            increaseX();
        }
        if (i % 7 == 3) {
            increaseY();
        }
        if (i % 2 == 0) {
            increaseCurFrame();
        }

    }

    public void setDirection(int direction) {
        this.direction = direction;
    }
}

