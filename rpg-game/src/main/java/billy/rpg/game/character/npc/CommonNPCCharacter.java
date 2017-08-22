package billy.rpg.game.character.npc;

import billy.rpg.game.character.NPCCharacter;
import billy.rpg.game.constants.GameConstant;
import billy.rpg.game.screen.MapScreen;


public class CommonNPCCharacter extends NPCCharacter {
    private long lastTime = System.currentTimeMillis();
    private long delay = 2000;

    public CommonNPCCharacter() {
    }

    @Override
    public void move(MapScreen mapScreen) {
        long now = System.currentTimeMillis();
        if (now - lastTime < delay) {
            return ;
        }
        lastTime = now;
        int i = GameConstant.random.nextInt(4);
        switch (i) {
            case 0:
                decreaseX();
                break;
            case 1:
                decreaseY();
                break;
            case 2:
                increaseX();
                break;
            case 3:
                increaseY();
                break;
        }

        if (i % 2 == 0) {
            super.increaseCurFrame();
        }
    }




}

