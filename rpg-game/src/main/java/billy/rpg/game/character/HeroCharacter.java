package billy.rpg.game.character;

import billy.rpg.game.screen.MapScreen;


public class HeroCharacter extends BaseCharacter {

    @Override
    public void move(MapScreen mapScreen) {
        // control by player
    }

   @Override
    public String toString() {
        return "[posX=" + getPosX() + ", posY=" + getPosY() + ", nextPosX="
                + getNextPosX() + ", nextPosY=" + getNextPosY() + ",dir=" + getDirection() + "]";
    }

}

