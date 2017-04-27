package com.billyrupeng;

import com.billy.scriptParser.container.GameContainer;

public class WalkUtil {
    public static final int WALK_YES = 1;
    public static final int WALK_NO = 0;
    
    /**
     * 
     * @param x
     * @param y
     * @return
     */
    public static boolean isWalkable(int x, int y) {
        int[][] flag = GameContainer.getInstance().getActiveMap().getFlag();
        // why flag[y][x]?? , i donot know
        int flagNum = flag[y][x];
//        System.out.println(x + "/" + y + "=" + flagNum); 
        return (flagNum%2 == WALK_YES);
    }

}
