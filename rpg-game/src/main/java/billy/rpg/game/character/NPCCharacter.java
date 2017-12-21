package billy.rpg.game.character;

public abstract class NPCCharacter extends BaseCharacter {
    private int tileNum; // for npc

    @Override
    public String toString() {
        return "[posX=" + getPosX() + ", posY=" + getPosY() + ", nextPosX="
                + getNextPosX() + ", nextPosY=" + getNextPosY() + "]";
    }


    public boolean isX() {
        return (getDirection() == DIRECTION_LEFT || getDirection() == DIRECTION_RIGHT);
    }

    public boolean isY() {
        return (getDirection() == DIRECTION_UP || getDirection() == DIRECTION_DOWN);
    }

    public int getTileNum() {
        return tileNum;
    }

    public void setTileNum(int tileNum) {
        this.tileNum = tileNum;
    }

}

