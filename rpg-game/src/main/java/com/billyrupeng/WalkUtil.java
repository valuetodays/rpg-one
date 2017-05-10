package com.billyrupeng;

import com.billy.scriptParser.container.GameContainer;

public class WalkUtil {
    public static final int WALK_YES = 1;
    public static final int WALK_NO = -1;
    
    /**
     * 1 ok, -1 not
     * @param x pos x
     * @param y pos y
     */
    public static boolean isWalkable(int x, int y) {
        int[][] flag = GameContainer.getInstance().getActiveMap().getFlag();
        // why flag[y][x]?? , i donot know
//        System.out.println(x + "/" + y + "=" + flagNum);
//        return (flagNum%2 == WALK_YES);
        return (flag[y][x] == WALK_YES);
    }

}
