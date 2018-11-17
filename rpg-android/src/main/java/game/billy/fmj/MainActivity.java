package game.billy.fmj;

import android.app.Activity;
import android.graphics.Bitmap;
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
import billy.rpg.game.core.platform.image.IGameImage;
import billy.rpg.game.core.platform.image.IGameImageFactory;
import billy.rpg.game.core.screen.BaseScreen;
import billy.rpg.game.core.screen.GameCoverScreen;
import billy.rpg.game.core.screen.GameCoverScreenNew;
import billy.rpg.game.core.screen.battle.BattleScreen;
import billy.rpg.game.core.util.CoreUtil;
import billy.rpg.game.core.util.FPSUtil;


public class MainActivity extends Activity implements IGameFrame {
    private static final String TAG = MainActivity.class.getSimpleName();


    protected AndroidCanvas andriodCanvas;
    protected AndroidGraphics androidGraphics;
    private View myView;
    private GameContainer gameContainer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        gameContainer = new GameContainer(this);
        myView = new MySurfaceView(this, gameContainer);
        setContentView(myView);

        Platform.setType(Platform.Type.ANDROID);
        Platform.setContext(this);



        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int widthPixels = displayMetrics.widthPixels;
        int heightPixels = displayMetrics.heightPixels;
        Log.i(TAG, "width/height=" + widthPixels + "/" + heightPixels);

        andriodCanvas = new AndroidCanvas();
        androidGraphics = new AndroidGraphics();
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


}
