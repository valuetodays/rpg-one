package billy.rpg.game.character.ex.walkable.npc;

import billy.rpg.game.character.ex.walkable.WalkableCharacter;

public abstract class NPCWalkableCharacter extends WalkableCharacter {
    private int tileNum; // for npc

    @Override
    public String toString() {
        return "[posX=" + getPosX() + ", posY=" + getPosY() + ", nextPosX="
                + getNextPosX() + ", nextPosY=" + getNextPosY() + "]";
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

