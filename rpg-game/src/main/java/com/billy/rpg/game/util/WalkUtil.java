package com.billy.rpg.game.util;

import com.billy.rpg.game.scriptParser.container.GameContainer;

/**
 * 判断下一步是否可行走
 */
public class WalkUtil {
    private static final int WALK_YES = 1;
    private static final int WALK_NO = -1;
    
    /**
     * 判断顺序是
     * <ul>
     *     <li>行走层位置不可行走，不可行走</li>
     *     <li>事件层位置是传送门，不可行走</li>
     *     <li> TODO 该位置有npc，不可行走 </li>
     * </ul>
     * 1 ok, -1 not
     * @param x pos x
     * @param y pos y
     */
    public static boolean isWalkable(int x, int y) {
        int[][] flag = GameContainer.getInstance().getActiveMap().getWalk();
        if (flag[x][y] == WALK_NO) {
            return false;
        }

        int[][] event = GameContainer.getInstance().getActiveMap().getEvent();
        int tileNum = event[x][y];
        if (tileNum <= 0xff && tileNum >= 0xef) { // transfer
            return false;
        }
        return true;
    }

}
