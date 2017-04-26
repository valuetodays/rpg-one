package com.billyrupeng;



import java.util.ListIterator;
import java.util.Stack;

import org.apache.log4j.Logger;

import com.billy.constants.GameConstant;
import com.billy.map.MainMap;
import com.billy.scriptParser.container.GameContainer;
import com.billy.scriptParser.item.ScriptItem;
import com.rupeng.game.GameCore;


/**
 * Run this game by ui
 * 
 * @author liulei-frx
 * 
 * @since 2016-12-07 10:26:29
 */
public class Main implements Runnable {
    private static final Logger LOG = Logger.getLogger(Main.class);
    private Stack<Object> screenStack; 
    private boolean running;
    
    
    public Main() {
        screenStack = new Stack<Object>();
        running = true;
    }
    
    
    public static void main(String[] args) {
        GameContainer.getInstance().load();
        
        Main m = new Main();
        GameCore.start(m);
//        new Thread(m, "game").start();
    }
    
    @Override
    public void run() {
        GameCore.setGameTitle("GAMETITLE");
        GameCore.setGameSize(GameConstant.GAME_WIDTH, GameConstant.GAME_HEIGHT);
        LOG.info("game starts");

        while ( running ) {
            synchronized (screenStack) {
                int key = GameCore.getPressedKeyCode();
                if (-1 == key) {
                    continue;
                }
                
                ListIterator<Object> iter = screenStack.listIterator(screenStack.size());
                // 找到第一个全屏窗口
                while (iter.hasPrevious()) {
                    Object tmp = iter.previous();
                    System.out.println(tmp);
//                    if (!tmp.isPopup()) {
//                        break;
//                    }
                }
                
                ScriptItem active = GameContainer.getInstance().getActiveFileItem();
                active.initWidthAndHeight(active.getHeight(), active.getWidth());
                active.checkTrigger();
                MainMap mainMap = active.getMm();
                GameCore.setGameTitle(mainMap.toString());
                if (KeyUtil.isEsc(key)) {
                    // TODO popup menu
                    GameCore.showMenu();
                    break;
                }
                if (KeyUtil.isLeft(key)) {
                    mainMap.decreaseX();
//                    GameContainer.getInstance().getDialogBean().setText(null);
                }
                if (KeyUtil.isRight(key)) {
                    mainMap.increaseX();
//                    GameContainer.getInstance().getDialogBean().setText(null);
                }
                if (KeyUtil.isUp(key)) {
                    mainMap.decreaseY();
//                    GameContainer.getInstance().getDialogBean().setText(null);
                }
                if (KeyUtil.isDown(key)) {
                    mainMap.increaseY();
//                    GameContainer.getInstance().getDialogBean().setText(null);
                }
                
            }
        }
        
        
        
        GameCore.pause(3000);
    }

}
