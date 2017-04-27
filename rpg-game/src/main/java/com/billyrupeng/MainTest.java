package com.billyrupeng;

import com.billy.constants.GameConstant;
import com.rupeng.game.GameCore;


/**
 * Run this game by ui
 * 
 * @author liulei-frx
 * 
 * @since 2016-12-07 10:26:29
 */
public class MainTest implements Runnable {
    
    public static void main(String[] args) {
        
        MainTest m = new MainTest();
        GameCore.start(m);
    }
    
    @Override
    public void run() {

//        GameCore.pause(1000);
        GameCore.setGameTitle("GAMETITLE");
        GameCore.setGameSize(GameConstant.GAME_WIDTH, GameConstant.GAME_HEIGHT);

//        GameCore.createImage(3, "tile1.jpg");
//        GameCore.setImagePosition(3, 200, 200);
        GameCore.createSprite(1, "skill");
        GameCore.setSpritePosition(1, 200, 200);
        GameCore.playSpriteAnimate(1, "1", true);
        GameCore.pause(4000);
        System.out.println(1);
//        
    }

}
