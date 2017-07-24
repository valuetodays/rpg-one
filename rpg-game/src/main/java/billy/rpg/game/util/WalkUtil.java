package billy.rpg.game.util;


import billy.rpg.game.GameFrame;
import billy.rpg.game.character.HeroCharacter;
import billy.rpg.game.character.NPCCharacter;
import billy.rpg.game.item.ScriptItem;

import java.util.List;

/**
 * 判断下一步是否可行走
 */
public class WalkUtil {
    private static final int WALK_YES = 1;
    private static final int WALK_NO = -1;
    
    /**
     * 判断顺序是
     * <ul>
     *     <li>case 1. 行走层位置不可行走，不可行走</li>
     *     <li>case 2. 事件层位置是传送门，不可行走</li>
     *     <li>case 3. 事件层位置是宝箱，不可行走</li>
     *     <li>case 4. 该位置有npc，不可行走 [这个判断是防止hero踩到npc或是npc踩到npc]</li>
     *     <li>case 5. 该位置有hero，不可行走 [这个判断是防止npc踩到hero]</li>
     * </ul>
     * @param x pos x
     * @param y pos y
     * @return true ok, false not
     */
    public static boolean isWalkable(int x, int y) {
        int[][] flag = GameFrame.getInstance().getGameContainer().getActiveMap().getWalk();
        if (flag[x][y] == WALK_NO) {
            return false;  // case 1
        }

        {
            int[][] event = GameFrame.getInstance().getGameContainer().getActiveMap().getEvent();
            int tileNum = event[x][y];
            if (tileNum <= 0xef && tileNum >= 0xef) { // transfer
                return false; // case 2
            }
            if (tileNum == 0xed || tileNum == 0xee) { // box
                return false;  // case 3
            }
        }

        // case 4
        {
            ScriptItem activeFileItem = GameFrame.getInstance().getGameContainer().getActiveFileItem();
            List<NPCCharacter> npcs = activeFileItem.getNpcs();
            for (NPCCharacter npc : npcs) {
                int posX = npc.getPosX();
                int posY = npc.getPosY();
                if (x == posX && y == posY) {
                    return false;
                }
            }
        }

        // case 5
        {
            HeroCharacter hero = GameFrame.getInstance().getGameContainer().getActiveFileItem().getHero();
            int posX = hero.getPosX();
            int posY = hero.getPosY();
            if (x == posX && y == posY) {
                return false;
            }
        }

        return true;
    }

}
