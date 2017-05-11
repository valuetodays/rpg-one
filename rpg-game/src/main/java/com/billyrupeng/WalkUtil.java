package com.billyrupeng;

import com.billy.scriptParser.container.GameContainer;

public class WalkUtil {
    private static final int WALK_YES = 1;
    private static final int WALK_NO = -1;
    
    /**
     * 1 ok, -1 not
     * @param x pos x
     * @param y pos y
     */
    public static boolean isWalkable(int x, int y) {
        int[][] flag = GameContainer.getInstance().getActiveMap().getWalk();
        return (flag[x][y] == WALK_YES);
    }

}
