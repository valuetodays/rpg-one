package billy.rpg.game.screen;

import billy.rpg.game.GameCanvas;
import billy.rpg.game.GameFrame;
import billy.rpg.game.callback.ConfirmCallback;
import billy.rpg.game.constants.GameConstant;
import billy.rpg.game.container.GameContainer;
import billy.rpg.game.screen.system.SystemUIScreen;
import billy.rpg.game.util.KeyUtil;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author lei.liu@datatist.com
 * @since 2018-11-05 14:21:28
 */
public class ConfirmScreen extends BaseScreen {
    public static enum ConfirmOption {
        YES,
        NO;
    }

    private String msg;
    private boolean isYes = true;
    private final BaseScreen ownerScreen;
    private ConfirmCallback confirmCallback;


    public ConfirmScreen(String msg) {
        this(msg, null, null);
    }

    public ConfirmScreen(String msg, BaseScreen ownerScreen, ConfirmCallback confirmCallback) {
        this.msg = msg;
        this.ownerScreen = ownerScreen;
        this.confirmCallback = confirmCallback;
    }

    @Override
    public void update(long delta) {

    }

    @Override
    public void draw(GameCanvas gameCanvas) {
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
        g.drawString(msg, 200, 160);

        g.drawString("是", 200, 180);
        g.drawString("否", 300, 180);
        Image gameArrow = GameFrame.getInstance().getGameContainer().getGameAboutItem().getGameArrowRight();
        g.drawImage(gameArrow, isYes ? 180 : 280, 170, null);
        g.dispose();

        gameCanvas.drawBitmap(paint, 0, 0);
    }

    @Override
    public void onKeyDown(int key) {

    }

    @Override
    public void onKeyUp(int key) {
        if (KeyUtil.isEnter(key)) {
            popScreen();
            if (isYes) {
                if (confirmCallback != null) {
                    confirmCallback.onOK();
                }
            } else {
                if (confirmCallback != null) {
                    confirmCallback.onCancel();
                }
            }

        } else if (KeyUtil.isLeft(key) || KeyUtil.isRight(key)) {
            isYes = !isYes;
        } else if (KeyUtil.isEsc(key)) {
            popScreen();
        }
    }

    private void popScreen() {
        if (ownerScreen instanceof SystemUIScreen) {
            ((SystemUIScreen)ownerScreen).pop();
        } else {
            System.out.println("unknown ownerScreen:" + ownerScreen.getClass().getName());
        }
    }
}
