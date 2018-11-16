package billy.rpg.game.core.character.walkable.npc;


import billy.rpg.game.core.container.GameContainer;
import billy.rpg.game.core.screen.MapScreen;

/**
 * 不移动的Npc
 *
 * @author liulei
 * @since 2017-05-18 14:10
 */
public class FixedNPCWalkableCharacter extends NPCWalkableCharacter {

    @Override
    public void move(GameContainer gameContainer, MapScreen mapScreen) {
        // do nothing
    }
}
