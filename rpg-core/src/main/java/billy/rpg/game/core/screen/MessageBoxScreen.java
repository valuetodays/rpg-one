package billy.rpg.game.core.screen;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

import billy.rpg.game.core.DesktopCanvas;
import billy.rpg.game.core.constants.GameConstant;
import billy.rpg.game.core.container.GameContainer;
import billy.rpg.game.core.screen.battle.BattleScreen;
import billy.rpg.game.core.screen.window.MessageBoxWindow;
import org.apache.commons.lang.StringUtils;

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
    private MessageBoxWindow messageBoxWindow = new MessageBoxWindow();

    public static MessageBoxScreen of(String msg) {
        return new MessageBoxScreen(msg);
    }

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
    public void update(GameContainer gameContainer, long delta) {
        cnt += delta;
        if (cnt > delay) {
            popScreen(gameContainer);
        }
    }


    @Override
    public void draw(GameContainer gameContainer, DesktopCanvas desktopCanvas) {
        if (StringUtils.isEmpty(msg)) {
            return ;
        }
        
        BufferedImage paint = new BufferedImage(GameConstant.GAME_WIDTH, GameConstant.GAME_HEIGHT, BufferedImage.TYPE_4BYTE_ABGR);
        Graphics g = paint.getGraphics();
        Image gameMessageBoxBg = gameContainer.getGameAboutItem().getGameMessageBoxBg();
        g.drawImage(gameMessageBoxBg, 160, 140, null);
        g.setColor(new Color(64, 64, 64, 128));
        g.setFont(GameConstant.FONT_14);
        g.setColor(Color.WHITE);
        g.drawString(msg, 200, 180);
        g.dispose();

        desktopCanvas.drawBitmap(gameContainer.getGameFrame(), paint, 0, 0);
    }

    @Override
    public void onKeyDown(GameContainer gameContainer, int key) {
        popScreen(gameContainer);
    }

    @Override
    public void onKeyUp(GameContainer gameContainer, int key) {
    }

    private void popScreen(GameContainer gameContainer) {
        if (ownerScreen instanceof BattleScreen) { // 战斗场景中的播放动画，就把战斗场景弹出一个
            ((BattleScreen)ownerScreen).pop();
        } else {
            gameContainer.getGameFrame().popScreen();
        }
    }

}
