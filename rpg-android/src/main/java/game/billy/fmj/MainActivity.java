package game.billy.fmj;

import android.app.Activity;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

import java.util.Stack;

import billy.rpg.game.core.GamePanel;
import billy.rpg.game.core.IGameCanvas;
import billy.rpg.game.core.IGameFrame;
import billy.rpg.game.core.constants.GameConstant;
import billy.rpg.game.core.container.GameContainer;
import billy.rpg.game.core.platform.Platform;
import billy.rpg.game.core.platform.graphics.AndroidGraphics;
import billy.rpg.game.core.screen.BaseScreen;
import billy.rpg.game.core.screen.GameCoverScreen;
import billy.rpg.game.core.screen.GameCoverScreenNew;
import billy.rpg.game.core.screen.battle.BattleScreen;
import billy.rpg.game.core.util.CoreUtil;
import billy.rpg.game.core.util.FPSUtil;


public class MainActivity extends Activity implements IGameFrame, Runnable {
    private static final String TAG = MainActivity.class.getSimpleName();

    private GameContainer gameContainer;
    private Stack<BaseScreen> screenStack = new Stack<>();
    private boolean running = true;
    private Thread gameThread;
    private boolean showFPS = false;
    private FPSUtil fpsUtil;
    protected AndroidCanvas andriodCanvas;
    protected AndroidGraphics androidGraphics;
    private View myView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        myView = new MyView(this);
        setContentView(myView);

        Platform.setType(Platform.Type.ANDROID);
        Platform.setContext(this);

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int widthPixels = displayMetrics.widthPixels;
        int heightPixels = displayMetrics.heightPixels;
        Log.i(TAG, "width/height=" + widthPixels + "/" + heightPixels);
        gameContainer = new GameContainer(this);
//        gameContainer.load();
        screenStack.push(new GameCoverScreenNew()); // 进入封面


        running = true;

        andriodCanvas = new AndroidCanvas();
        androidGraphics = new AndroidGraphics();
        gameThread = new Thread(this, "fmj");
        gameThread.start();
        Log.i(TAG, "starts");
    }

    @Override
    public void changeScreen(int code) {

    }

    @Override
    public BaseScreen getCurScreen() {
        return null;
    }

    @Override
    public GameContainer getGameContainer() {
        return gameContainer;
    }

    @Override
    public GamePanel getGamePanel() {
        return null;
    }

    @Override
    public void setTitle(String title) {

    }

    @Override
    public void pushScreen(BaseScreen bs) {

    }

    @Override
    public void popScreen() {

    }

    @Override
    public void change2BattleScreen(BattleScreen battleScreen) {

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

            screenStack.get(0).draw2(gameContainer, andriodCanvas);
            myView.draw(andriodCanvas.getCanvas());
            myView.invalidate();

           /* if (screenStack.size() > 1) {
//                    logger.error("screenStack.size=" + screenStack.size());
            }

            for (i = screenStack.size()-1; i >= 0; i--) {
                BaseScreen baseScreen = screenStack.get(i);
                if (!baseScreen.isPopup()) {
                    break;
                }
            }
            IGameCanvas gameCanvasTemp = andriodCanvas;
            if (gameCanvasTemp != null) {
                if (i < 0) {
                    i = 0;
                }
                for (int j = i; j < screenStack.size(); j++) {
                    BaseScreen baseScreen = screenStack.get(j);
                    baseScreen.draw2(gameContainer, gameCanvasTemp);
                }
            }
            if (fpsUtil != null) {
                fpsUtil.calculate();
//                gameCanvasTemp.drawFPS(this, fpsUtil.getFrameRate());
            }
//            gamePanel.repaint();
            //            } // end of synchronized
*/
            CoreUtil.sleep(GameConstant.TIME_GAMELOOP);
            lastTime = curTime;

        }
    }


}
