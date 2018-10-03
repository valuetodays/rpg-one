package billy.rpg.game.character.ex.walkable;

import billy.rpg.game.screen.MapScreen;

public class BoxWalkableCharacter extends WalkableCharacter {
    private int tileNum;

    public BoxWalkableCharacter(int tileNum) {
        this.tileNum = tileNum;
    }

    /**
     * 宝箱的不可移动
     */
    @Override
    public void move(MapScreen mapScreen) {    }


    @Override
    public int getPosY() {
        return super.getPosY();
    }

    public int getTileNum() {
        return tileNum;
    }

}

