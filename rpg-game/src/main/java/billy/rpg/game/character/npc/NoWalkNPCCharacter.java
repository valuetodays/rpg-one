package billy.rpg.game.character.npc;


import billy.rpg.game.character.NPCCharacter;
import billy.rpg.game.screen.MapScreen;

/**
 * 不移动的Npc
 *
 * @author liulei
 * @since 2017-05-18 14:10
 */
public class NoWalkNPCCharacter extends NPCCharacter {

    @Override
    public void move(MapScreen mapScreen) {
        // do nothing
    }
}
