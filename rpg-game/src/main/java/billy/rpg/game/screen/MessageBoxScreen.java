package billy.rpg.game.screen;

import billy.rpg.game.GameCanvas;
import billy.rpg.game.GameFrame;
import billy.rpg.game.constants.GameConstant;
import billy.rpg.game.container.GameContainer;
import billy.rpg.game.screen.battle.BattleScreen;
import org.apache.commons.lang.StringUtils;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * show messagebox, and it will disappear in {@link #delay} ms, can cancel by key down
 * @author liulei-home
 * @since 2016-12-23 02:11
 *
 */
public class MessageBoxScreen extends BaseScreen {
    private String msg;
    /**
     * the last time of this messagebox
     */
    private int delay;
    private int cnt = 0;
    private final BaseScreen ownerScreen;
    
    public MessageBoxScreen(String msg) {
        this(msg, null, 2000);
    }

    public MessageBoxScreen(String msg, BaseScreen ownerScreen) {
        this(msg, ownerScreen, 2000);
    }
    public MessageBoxScreen(String msg, BaseScreen ownerScreen, int delay) {
        this.msg = msg;
        this.delay = delay;
        this.ownerScreen = ownerScreen;
    }

    @Override
    public void update(long delta) {
        cnt += delta;
        if (cnt > delay) {
            popScreen();
        }
    }


    @Override
    public void draw(GameCanvas gameCanvas) {
        if (StringUtils.isEmpty(msg)) {
            return ;
        }
        
        // 创建一个缓冲区
        BufferedImage paint = new BufferedImage(
                GameConstant.GAME_WIDTH, 
                GameConstant.GAME_HEIGHT, 
                BufferedImage.TYPE_4BYTE_ABGR);

        // 得到缓冲区的画笔
        Graphics g = paint.getGraphics();
        Image gameMessageBoxBg = GameContainer.getInstance().getGameAboutItem().getGameMessageBoxBg();
        g.drawImage(gameMessageBoxBg, 160, 140, null);
        g.setColor(new Color(64, 64, 64, 128));
        //g.fillRoundRect(30, 50, 200, 45, 20, 20);
        g.setFont(new Font("黑体", Font.BOLD, 14));
        g.setColor(Color.WHITE);
        g.drawString(msg, 200, 180);

        g.dispose();
        gameCanvas.drawBitmap(paint, 0, 0);
    }

    @Override
    public void onKeyDown(int key) {
    }

    @Override
    public void onKeyUp(int key) {
        popScreen();
    }

    private void popScreen() {
        if (ownerScreen instanceof BattleScreen) { // 战斗场景中的播放动画，就把战斗场景弹出一个
            ((BattleScreen)ownerScreen).pop();
        } else {
            GameFrame.getInstance().popScreen();
        }
    }

}
