package billy.rpg.game.character.ex.walkable.npc;

import billy.rpg.game.constants.GameConstant;
import billy.rpg.game.screen.MapScreen;
import billy.rpg.game.util.WalkUtil;

/**
 * 普通npc
 */
public class CommonNPCWalkableCharacter extends NPCWalkableCharacter {
    private long lastTime = System.currentTimeMillis();
    private long delay = 2000;

    @Override
    public void move(MapScreen mapScreen) {
        long now = System.currentTimeMillis();
        if (now - lastTime < delay) {
            return ;
        }
        lastTime = now;
        int i = GameConstant.random.nextInt(4);
        switch (i) {
            case 0:
                decreaseX(mapScreen);
                break;
            case 1:
                decreaseY(mapScreen);
                break;
            case 2:
                increaseX(mapScreen);
                break;
            case 3:
                increaseY(mapScreen);
                break;
        }

        if (i % 2 == 0) {
            super.increaseCurFrame();
        }
    }

    private void increaseY(MapScreen mapScreen) {
        increaseCurFrame();
        int nextPosY = posY + 1;
        if (WalkUtil.isWalkable(posX, nextPosY)) {
            posY = nextPosY;
        }
    }

    private void increaseX(MapScreen mapScreen) {
        increaseCurFrame();
        int nextPosX = posX + 1;
        if (WalkUtil.isWalkable(nextPosX, posY)) {
            posX = nextPosX;
        }
    }

    private void decreaseY(MapScreen mapScreen) {
        increaseCurFrame();
        int nextPosY = posY - 1;
        if (WalkUtil.isWalkable(posX, nextPosY)) {
            posY = nextPosY;
        }
    }

    private void decreaseX(MapScreen mapScreen) {
        increaseCurFrame();
        int nextPosX = posX - 1;
        if (WalkUtil.isWalkable(nextPosX, posY)) {
            posX = nextPosX;
        }
    }


}

