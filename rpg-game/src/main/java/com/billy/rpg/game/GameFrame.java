package com.billy.rpg.game;

import com.billy.rpg.game.constants.GameConstant;
import com.billy.rpg.game.scriptParser.container.GameContainer;
import com.billy.rpg.game.screen.*;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Stack;


/**
 * Run this game by ui
 * 
 * @author liulei-frx
 * 
 * @since 2016-12-07 10:26:29
 */
public class GameFrame extends JFrame implements Runnable {
    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger(GameFrame.class);
    private static GameFrame instance;
    private Stack<BaseScreen> screenStack;
    private GameCanvas gameCanvas;
    private boolean running;
    public GamePanel gamePanel;
    private GameContainer gameContainer;
    

    public static void main(String[] args) {
        GameFrame m = new GameFrame();
        
        new Thread(m, "fmj").start();
    }
    
    
    public GameFrame() {
        gameCanvas = new GameCanvas();
        running = true;
        gamePanel = new GamePanel();
        this.add(gamePanel);

        setTitle(GameConstant.GAME_TITLE);
        setLocation(GameConstant.GAME_WINDOW_LEFT, GameConstant.GAME_WINDOW_TOP);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
//        setResizable(false);
//        setAlwaysOnTop(true);
        addListener();//键盘监听
        instance = this;
        gameContainer = GameContainer.getInstance();
        gameContainer.load();

        screenStack = new Stack<>();
        screenStack.push(new GameCoverScreen()); // 进入封面
//        screenStack.push(new MapScreen());
//        screenStack.push(new AnimationScreen(null, null, true));


        pack();
        LOG.info("game starts");
    }
    
    public static GameFrame getInstance() {
        return instance;
    }
    
    public GameContainer getGameContainer() {
        return gameContainer;
    }
    
    public GameCanvas getGameCanvas() {
        return gameCanvas;
    }


    public void pushScreen(final BaseScreen screen) {
//        if (getCurScreen().isEnd()) {
            screenStack.push(screen);
//        }
    }
    
    public void popScreen() {
            screenStack.pop();
    }

    public BaseScreen getCurScreen() {
            return screenStack.peek();
    }

    public void changeScreen(int screenCode) {
//        getInstance().getGameContainer().getActiveMap();
        BaseScreen tmp = null;
        switch (screenCode) {
        case 11:
            tmp = new DialogScreen("你好");
            break;
        case 1:
            tmp = new MapScreen();
            break;
        case 2:
            tmp = new SystemMenuScreen();
            break;
        case 8:
            tmp = new TransitionScreen();
            break;
        }
        if (tmp != null) {
            synchronized (screenStack) {
                screenStack.clear();
                screenStack.push(tmp);
            }
        }
    }
    
    @Override
    public void run() {
        long curTime = System.currentTimeMillis();
        long lastTime = curTime;
        int i = 0;
        
        while ( running ) {
//            synchronized (screenStack) {
                curTime = System.currentTimeMillis();
                screenStack.peek().update(curTime - lastTime);
                if (screenStack.size() > 1) {
//                    LOG.error("screenStack.size=" + screenStack.size());
                }
                lastTime = curTime;
                i = 0;
                for (i = screenStack.size()-1; i >=0 ;i--) {
                    BaseScreen baseScreen = screenStack.get(i);
                    if (!baseScreen.isPopup()) {
                        break;
                    }
                }

                GameCanvas gameCanvasTemp = gameCanvas;
                if (gameCanvasTemp != null) {
                    if (i < 0) {
                        i = 0;
                    }
                    for (int j = i; j < screenStack.size(); j++) {
                        BaseScreen baseScreen = screenStack.get(j);
                        baseScreen.draw(gameCanvasTemp);
                    }
                    gamePanel.repaint();
                }
//            } // end of synchronized
        }
        
        
        try {
            Thread.sleep(GameConstant.TIME_GAMELOOP);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    
    private void addListener() {
        this.addKeyListener(new KeyListener() {//键盘监听
            @Override
            public void keyTyped(KeyEvent e) {
            }
            
            @Override
            public void keyReleased(KeyEvent e) {
                int key = c(e.getKeyCode());
//                synchronized (screenStack) {
                    screenStack.peek().onKeyUp(key);
//                }
            }
            @Override
            public void keyPressed(KeyEvent e) {
                int key = c(e.getKeyCode());
//                synchronized (screenStack) {
                    screenStack.peek().onKeyDown(key);
//                }
            }
            
            int c(int c){
                return c;
            }
        });
    }


    public GamePanel getGamePanel() {
        return gamePanel;
    }
}
