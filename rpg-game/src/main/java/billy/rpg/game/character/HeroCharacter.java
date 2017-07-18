package billy.rpg.game.character;

import billy.rpg.game.util.WalkUtil;

import java.util.Random;


public class HeroCharacter extends BaseCharacter {
    private int curFrame;  // 步数
    private int direction; // 方向


    public void setHeight(int height) {
        this.height = height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getNextPosX() {
        return posX + (direction == DIRECTION_LEFT ? -1 : (direction == DIRECTION_RIGHT ? 1 : 0 ));
    }

    public int getNextPosY() {
        return posY + (direction == DIRECTION_UP ? -1 : (direction == DIRECTION_DOWN ? 1 : 0 ));
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
        if (direction == DIRECTION_RIGHT) {
            increaseCurFrame();
        int nextPosX = posX + 1;
        if ((nextPosX <= width - 1) && WalkUtil.isWalkable(posX + 1, posY)) {
            posX++;
        }
        }
        direction = DIRECTION_RIGHT;
    }

    public void decreaseX() {
        if (direction == DIRECTION_LEFT) {
            increaseCurFrame();
        int nextPosX = posX - 1;
        if (nextPosX >= 0 && WalkUtil.isWalkable(posX - 1, posY)) {
            posX--;
        }
        }
        direction = DIRECTION_LEFT;
    }

    public void increaseY() {
        if (direction == DIRECTION_DOWN) {
            increaseCurFrame();
            int nextPosY = posY + 1;
            if (nextPosY <= height - 1 && WalkUtil.isWalkable(posX, posY + 1)) {
                posY++;
            }
        }
        direction = DIRECTION_DOWN;
    }

    public void decreaseY() {
        if (direction == DIRECTION_UP) {
            increaseCurFrame();
        int nextPosY = posY - 1;
        if (nextPosY >= 0 && WalkUtil.isWalkable(posX, posY - 1)) {
            posY--;
        }
        }
        direction = DIRECTION_UP;
    }

    @Override
    public String toString() {
        return "[h=" + height + ", w=" + width + ", posX=" + posX + ", posY=" + posY + ", nextPosX="
                + getNextPosX() + ", nextPosY=" + getNextPosY() + "dir=" + direction + "]";
    }

    public int getDirection() {
        return direction;
    }

    public boolean isX() {
        return (direction == DIRECTION_LEFT || direction == DIRECTION_RIGHT);
    }

    public boolean isY() {
        return (direction == DIRECTION_UP || direction == DIRECTION_DOWN);
    }

    private static Random random = new Random();
    private long lastTime = System.currentTimeMillis();


    @Override
    public void move() {
        long now = System.currentTimeMillis();
        if (now - lastTime < 500) {
            return ;
        }
        lastTime = now;
        int i = random.nextInt(8 << 2);
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

