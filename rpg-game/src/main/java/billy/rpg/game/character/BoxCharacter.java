package billy.rpg.game.character;

import billy.rpg.game.screen.MapScreen;

public class BoxCharacter extends BaseCharacter {
    private int tileNum;

    public BoxCharacter(int tileNum) {
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

