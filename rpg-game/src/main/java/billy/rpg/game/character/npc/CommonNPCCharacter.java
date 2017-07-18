package billy.rpg.game.character.npc;

import billy.rpg.game.character.NPCCharacter;
import billy.rpg.game.constants.GameConstant;


public class CommonNPCCharacter extends NPCCharacter {

    private long lastTime = System.currentTimeMillis();

    public CommonNPCCharacter() {
    }

    @Override
    public void move() {
        long now = System.currentTimeMillis();
        if (now - lastTime < 500) {
            return ;
        }
        lastTime = now;
        int i = GameConstant.random.nextInt(8 << 2);
        if (i % 7 == 1) {
            decreaseX();
        }
        if (i % 7 == 2) {
            decreaseY();
        }
        if (i % 7 == 3) {
            increaseX();
        }
        if (i % 7 == 3) {
            increaseY();
        }
        if (i % 2 == 0) {
            super.increaseCurFrame();
        }

    }




}

