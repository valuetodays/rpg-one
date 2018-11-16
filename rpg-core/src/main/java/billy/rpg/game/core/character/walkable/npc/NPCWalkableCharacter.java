package billy.rpg.game.core.character.walkable.npc;

import billy.rpg.game.core.character.walkable.WalkableCharacter;
import billy.rpg.game.core.container.GameContainer;

public abstract class NPCWalkableCharacter extends WalkableCharacter {
    private int tileNum; // for npc

    public String toString(GameContainer gameContainer) {
        return "[posX=" + getPosX() + ", posY=" + getPosY() + ", nextPosX="
                + getNextPosX(gameContainer) + ", nextPosY=" + getNextPosY(gameContainer) + "]";
    }


    public boolean isX() {
        return PositionEnum.isX(getDirection());
    }

    public boolean isY() {
        return PositionEnum.isY(getDirection());
    }

    public int getTileNum() {
        return tileNum;
    }

    public void setTileNum(int tileNum) {
        this.tileNum = tileNum;
    }

}

