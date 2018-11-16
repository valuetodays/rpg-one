package billy.rpg.game.core.character.walkable;

import billy.rpg.game.core.container.GameContainer;
import billy.rpg.game.core.screen.MapScreen;

public class BoxWalkableCharacter extends WalkableCharacter {
    private int tileNum;

    public BoxWalkableCharacter(int tileNum) {
        this.tileNum = tileNum;
    }

    /**
     * 宝箱的不可移动
     */
    @Override
    public void move(GameContainer gameContainer, MapScreen mapScreen) {    }


    @Override
    public int getPosY() {
        return super.getPosY();
    }

    public int getTileNum() {
        return tileNum;
    }

}

