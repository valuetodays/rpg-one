package billy.rpg.game.core.util;


import billy.rpg.game.core.character.walkable.BoxWalkableCharacter;
import billy.rpg.game.core.character.walkable.HeroWalkableCharacter;
import billy.rpg.game.core.character.walkable.npc.NPCWalkableCharacter;
import billy.rpg.game.core.container.GameContainer;
import billy.rpg.game.core.item.ScriptItem;
import billy.rpg.game.core.screen.MapScreen;
import billy.rpg.resource.map.MapMetaData;

import java.util.List;

/**
 * 判断下一步是否可行走
 */
public class WalkUtil {
    private static final int WALK_YES = 2;
    public static final int WALK_NO = 0;
    
    /**
     * 判断顺序是
     * <ul>
     *     <li>传入的x,y不合法，不可行走</li>
     *     <li>case 1. 行走层位置不可行走，不可行走</li>
     *     <li>case 2. 该位置有宝箱，不可行走</li>
     *     <li>case 4. 该位置有npc，不可行走 [这个判断是防止hero踩到npc或是npc踩到npc]</li>
     *     <li>case 5. 该位置有hero，不可行走 [这个判断是防止npc踩到hero]</li>
     * </ul>
     * @param x pos x
     * @param y pos y
     * @return true ok, false not
     */
    public static boolean isWalkable(GameContainer gameContainer, int x, int y) {
        MapMetaData activeMap = gameContainer.getActiveMap();
        if (x < 0 || x >= activeMap.getWidth()
            || y < 0 || y >= activeMap.getHeight() ) {
            return false;
        }
        int[][] flag = activeMap.getWalk();
        if (flag[x][y] == WALK_NO) {
            return false;  // case 1
        }
        {
            // case 2
            ScriptItem activeFileItem = gameContainer.getActiveScriptItem();
            List<BoxWalkableCharacter> boxes = activeFileItem.getBoxes();
            for (BoxWalkableCharacter box : boxes) {
                int posX = box.getPosX();
                int posY = box.getPosY();
                if (x == posX && y == posY) {
                    return false;
                }
            }

            // case 4
            List<NPCWalkableCharacter> npcs = activeFileItem.getNpcs();
            for (NPCWalkableCharacter npc : npcs) {
                int posX = npc.getPosX();
                int posY = npc.getPosY();
                if (x == posX && y == posY) {
                    return false;
                }
            }
        }

        // case 5
        {
            MapScreen mapScreen = gameContainer.getMapScreen();
            HeroWalkableCharacter hero = gameContainer.getActiveScriptItem().getHero();
            int offsetTileX = mapScreen.getOffsetTileX();
            int offsetTileY = mapScreen.getOffsetTileY();
            int posX = hero.getPosX();
            int posY = hero.getPosY();
            if (x == offsetTileX + posX && y == offsetTileY + posY) {
                return false;
            }
        }

        return true;
    }

}
