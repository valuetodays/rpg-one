package billy.rpg.game.core.character.walkable;

import billy.rpg.game.core.container.GameContainer;
import billy.rpg.game.core.screen.MapScreen;

public class BoxWalkableCharacter extends WalkableCharacter {
    private boolean visible;
    private int tileNum;


    /**
     * 宝箱的不可移动
     */
    @Override
    public void move(GameContainer gameContainer, MapScreen mapScreen) {    }


    @Override
    public int getPosY() {
        return super.getPosY();
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isVisible() {
        return visible;
    }

    public int getTileNum() {
        return tileNum;
    }

    public void setTileNum(int tileNum) {
        this.tileNum = tileNum;
    }
}

