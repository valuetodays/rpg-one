package billy.rpg.game;

import billy.rpg.common.util.JavaVersionUtil;
import billy.rpg.game.core.GameCanvas;
import billy.rpg.game.core.GameData;
import billy.rpg.game.core.GamePanel;
import billy.rpg.game.core.IGameFrame;
import billy.rpg.game.core.constants.GameConstant;
import billy.rpg.game.core.container.GameContainer;
import billy.rpg.game.core.screen.BaseScreen;
import billy.rpg.game.core.screen.GameCoverScreen;
import billy.rpg.game.core.screen.ProducerScreen;
import billy.rpg.game.core.screen.TransitionScreen;
import billy.rpg.game.core.screen.battle.BattleScreen;
import billy.rpg.game.core.screen.system.SystemUIScreen;
import billy.rpg.game.core.util.CoreUtil;
import billy.rpg.game.core.util.FPSUtil;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.util.Stack;


/**
 * Run this game by ui
 * 
 * @author liulei-frx
 * 
 * @since 2016-12-07 10:26:29
 */
public class GameFrame extends JFrame implements IGameFrame, Runnable {
    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger(GameFrame.class);


    private static GameFrame instance;
    private final Stack<BaseScreen> screenStack = new Stack<>();
    private GameCanvas gameCanvas;
    private BufferStrategy bufferStrategy;
    private boolean running;
    private GamePanel gamePanel;
    private GameData gameData;
    private GameContainer gameContainer;
    private Thread gameThread;
    private FPSUtil fpsUtil;
    private boolean showFPS = true;

    public static void main(String[] args) {
        JavaVersionUtil.validateJava();

        GameFrame game = new GameFrame();
        game.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                game.onWindowClosing();
            }
        });

        SwingUtilities.invokeLater(() -> game.createAndShowGUI());
    }

    private void onWindowClosing() {
        running = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }


    @Override
    public void run() {
        if (showFPS) {
            fpsUtil = new FPSUtil();
            fpsUtil.init();
        }
        long curTime = System.currentTimeMillis();
        long lastTime = curTime;
        int i = 0;

        while ( running ) {
//            synchronized (screenStack) {
            curTime = System.currentTimeMillis();
            screenStack.peek().update(gameContainer, curTime - lastTime);
            if (screenStack.size() > 1) {
//                    logger.error("screenStack.size=" + screenStack.size());
            }

            for (i = screenStack.size()-1; i >= 0; i--) {
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
                    baseScreen.draw(gameContainer, gameCanvasTemp);
                }
            }
            if (fpsUtil != null) {
                fpsUtil.calculate();
                gameCanvas.drawFPS(this, fpsUtil.getFrameRate());
            }
            gamePanel.repaint();
            //            } // end of synchronized

            CoreUtil.sleep(GameConstant.TIME_GAMELOOP);
            lastTime = curTime;
        }
    }

    public void createAndShowGUI() {
        gamePanel = new GamePanel(this);
        this.add(gamePanel);
        gameData = new GameData();

        setTitle(GameConstant.GAME_TITLE);
        setLocation(GameConstant.GAME_WINDOW_LEFT, GameConstant.GAME_WINDOW_TOP);
        String gameIconPath = this.getClass().getClassLoader().getResource("").getPath() + "/Game.png";
        setIconImage(Toolkit.getDefaultToolkit().getImage(gameIconPath));
        setResizable(false);
//        setAlwaysOnTop(true);
        addListener();//键盘监听
        instance = this;
        gameContainer = new GameContainer(this);
        gameContainer.load();

        screenStack.push(new GameCoverScreen()); // 进入封面
//        int[] monsterIds = new int[]{51, 51};
//        screenStack.push(new BattleScreen(monsterIds)); // 进入战斗界面

//        screenStack.push(new MapScreen());
///        screenStack.push(new AnimationScreen(0)); // show animation

        pack();
        setVisible(true);

        running = true;
        gameCanvas = new GameCanvas();
        gameThread = new Thread(this, "fmj");
        gameThread.start();
        LOG.info("game starts");


    }

    public static GameFrame getInstance() {
        return instance;
    }
    
    @Override
    public GameContainer getGameContainer() {
        return gameContainer;
    }

    public GameCanvas getGameCanvas() {
        return gameCanvas;
    }

    @Override
    public void pushScreen(final BaseScreen screen) {
//        if (getCurScreen().isEnd()) {
            screenStack.push(screen);
//        }
    }

    @Override
    public void popScreen() {
        screenStack.pop();
    }

    @Override
    public BaseScreen getCurScreen() {
        return screenStack.peek();
    }

    @Override
    public GamePanel getGamePanel() {
        return gamePanel;
    }

    @Override
    public void changeScreen(int screenCode) {
        BaseScreen tmp = null;
        switch (screenCode) {
        case 1:
            tmp = getGameContainer().getMapScreen();
            break;
        case 2:
            tmp = new SystemUIScreen();
            break;
        case 3:
            tmp = new ProducerScreen();
            break;
        case 8:
            tmp = new TransitionScreen();
            break;
        case 9:
            // TODO 当玩家被小怪打死后来到该处时，会导致玩家的hp是负数的。
            // 解决办法是在此处 重新从文件中加载玩家和其它数据
            tmp = new GameCoverScreen();
            break;
        }
        if (tmp != null) {
            synchronized (screenStack) {
                screenStack.clear();
                screenStack.push(tmp);
            }
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
                if (null != screenStack && screenStack.peek() != null) {
                    screenStack.peek().onKeyUp(gameContainer, key);
                }
//                }
            }
            @Override
            public void keyPressed(KeyEvent e) {
                if (null == screenStack || screenStack.isEmpty()) {
                    return;
                }
                int key = c(e.getKeyCode());
//                synchronized (screenStack) {
                    screenStack.peek().onKeyDown(gameContainer, key);
//                }
            }
            
            int c(int c) {
                return c;
            }
        });
    }


    /**
     * switch to battle screen
     *
     * @param battleScreen battle screen
     */
    @Override
    public void change2BattleScreen(BattleScreen battleScreen) {
        synchronized (screenStack) {
            screenStack.clear();
            screenStack.push(battleScreen);
        }
    }

    public GameData getGameData() {
        return gameData;
    }
}
