package billy.rpg.game.core.character.walkable.npc;

import billy.rpg.game.core.constants.GameConstant;
import billy.rpg.game.core.container.GameContainer;
import billy.rpg.game.core.screen.MapScreen;
import billy.rpg.game.core.util.WalkUtil;

/**
 * 普通npc
 */
public class CommonNPCWalkableCharacter extends NPCWalkableCharacter {
    private long lastTime = System.currentTimeMillis();
    private long delay = 2000;

    @Override
    public void move(GameContainer gameContainer, MapScreen mapScreen) {
        long now = System.currentTimeMillis();
        if (now - lastTime < delay) {
            return ;
        }
        lastTime = now;
        int i = GameConstant.random.nextInt(4);
        switch (i) {
            case 0:
                decreaseX(gameContainer, mapScreen);
                break;
            case 1:
                decreaseY(gameContainer, mapScreen);
                break;
            case 2:
                increaseX(gameContainer, mapScreen);
                break;
            case 3:
                increaseY(gameContainer, mapScreen);
                break;
        }

        if (i % 2 == 0) {
            super.increaseCurFrame();
        }
    }

    private void increaseY(GameContainer gameContainer, MapScreen mapScreen) {
        increaseCurFrame();
        int nextPosY = posY + 1;
        if (WalkUtil.isWalkable(gameContainer, posX, nextPosY)) {
            posY = nextPosY;
        }
    }

    private void increaseX(GameContainer gameContainer, MapScreen mapScreen) {
        increaseCurFrame();
        int nextPosX = posX + 1;
        if (WalkUtil.isWalkable(gameContainer, nextPosX, posY)) {
            posX = nextPosX;
        }
    }

    private void decreaseY(GameContainer gameContainer, MapScreen mapScreen) {
        increaseCurFrame();
        int nextPosY = posY - 1;
        if (WalkUtil.isWalkable(gameContainer, posX, nextPosY)) {
            posY = nextPosY;
        }
    }

    private void decreaseX(GameContainer gameContainer, MapScreen mapScreen) {
        increaseCurFrame();
        int nextPosX = posX - 1;
        if (WalkUtil.isWalkable(gameContainer, nextPosX, posY)) {
            posX = nextPosX;
        }
    }


}

