package com.billy.rpg.game.util;

import java.awt.event.KeyEvent;

public class KeyUtil {
    
    public static boolean isUp(int key) {
        if (key == KeyEvent.VK_UP || key == KeyEvent.VK_W) {
            return true;
        }
        
        return false;
    }
    
    public static boolean isDown(int key) {
        if (key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) {
            return true;
        }
        
        return false;
    }
    
    public static boolean isLeft(int key) {
        if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) {
            return true;
        }
        
        return false;
    }
    
    public static boolean isRight(int key) {
        if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) {
            return true;
        }
        
        return false;
    }

    public static boolean isEsc(int key) {
        if (key == KeyEvent.VK_ESCAPE) {
            return true;
        }
        
        return false;
    }

    public static boolean isHome(int key) {
        if (key == KeyEvent.VK_HOME) {
            return true;
        }
        return false;
    }

    public static boolean isEnter(int key) {
        if (key == KeyEvent.VK_ENTER) {
            return true;
        }
        return false;
    }
    

}
