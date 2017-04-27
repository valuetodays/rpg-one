package com.billyrupeng;

import com.billy.constants.GameConstant;
import com.billy.scriptParser.container.GameContainer;
import com.billyrupeng.screen.*;
import com.billyrupeng.timer.AnimationTimer;
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
public class MainFrame extends JFrame implements Runnable {
    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger(MainFrame.class);
    private static MainFrame instance;
    private Stack<BaseScreen> screenStack; 
    private GameCanvas gameCanvas;
    private boolean running;
    public GamePanel gamePanel;
    private GameContainer gameContainer;
    

    public static void main(String[] args) {
        MainFrame m = new MainFrame();
        
        new Thread(m, "fmj").start();
    }
    
    
    public MainFrame() {
        screenStack = new Stack<BaseScreen>();
        screenStack.push(new GameCoverScreen());
//        screenStack.push(new MapScreen());
//        screenStack.push(new AnimationScreen(null, null, true));
        gameCanvas = new GameCanvas();
        running = true;
        gamePanel = new GamePanel();
        this.add(gamePanel);
        
        
        gameContainer = GameContainer.getInstance();
        gameContainer.load();
        LOG.info("game starts");
        
        setTitle("伏魔记");
        setLocation(400, 100);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
//        setResizable(false);
//        setAlwaysOnTop(true);
        addListener();//键盘监听
        instance = this;
        pack();
    }
    
    public static MainFrame getInstance() {
        return instance;
    }
    
    public GameContainer getGameContainer() {
        return gameContainer;
    }
    
    public GameCanvas getGameCanvas() {
        return gameCanvas;
    }

    
    public void addTimer(AnimationTimer at) {

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
        }
        if (tmp != null) {
            screenStack.clear();
            screenStack.push(tmp);
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
    
    
    public void addListener() {
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





}
