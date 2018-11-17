package game.billy.fmj;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.Stack;

import billy.rpg.game.core.IGameCanvas;
import billy.rpg.game.core.container.GameContainer;
import billy.rpg.game.core.platform.image.AndroidImage;
import billy.rpg.game.core.platform.image.IGameImage;
import billy.rpg.game.core.screen.BaseScreen;
import billy.rpg.game.core.screen.GameCoverScreenNew;

public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback, Runnable {
    private GameContainer gameContainer;
    private Stack<BaseScreen> screenStack = new Stack<>();

    private Paint paint;
    private boolean running;
    private Thread thread;
    private SurfaceHolder sfh;
    private int screenW;
    private int screenH;

    public MySurfaceView(Context context, GameContainer gameContainer) {
        super(context);
        sfh = this.getHolder();
        sfh.addCallback(this);
        setFocusable(true);
        paint = new Paint();
        paint.setTextSize(50);
        paint.setColor(Color.RED);
        setFocusable(true);
        this.gameContainer = gameContainer;
    }

    private AndroidCanvas gameCanvas = new AndroidCanvas();

    @Override
    public void run() {
        long curTime = System.currentTimeMillis();
        long lastTime = curTime;
        while (running) {
            curTime = System.currentTimeMillis();
            screenStack.get(0).update(gameContainer, curTime - lastTime);
            screenStack.get(0).draw2(gameContainer, gameCanvas);
            draw(gameCanvas);
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                //
            }

            lastTime = curTime;
        }

    }

    private void draw(AndroidCanvas gameCanvas) {
        Canvas canvas = null;
        try {
            canvas = sfh.lockCanvas();
            if (canvas != null) {
                canvas.drawColor(Color.WHITE);
                canvas.drawBitmap(gameCanvas.getBitmap(), 0f, 0f, paint);
                canvas.drawText("game,,,", 100, 300, paint);
            }
        } catch (Exception e) {
            //
        } finally {
            if (canvas != null) {
                sfh.unlockCanvasAndPost(canvas);
            }
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        screenW = this.getWidth();
        screenH = this.getHeight();
        running = true;

//        gameContainer.load();
        screenStack.push(new GameCoverScreenNew()); // 进入封面

        thread = new Thread(this, "gameView");
        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        running = false;
    }
}
