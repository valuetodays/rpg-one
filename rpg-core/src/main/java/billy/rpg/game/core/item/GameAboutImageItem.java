package billy.rpg.game.core.item;

import org.apache.commons.io.IOUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.InputStream;

import billy.rpg.game.core.platform.image.IGameImage;
import billy.rpg.game.core.platform.image.IGameImageFactory;

public class GameAboutImageItem {
    private Image gameCover;
    private Image gameOver;
    private Image gameArrowLeft;
    private Image gameArrowRight;
    private Image gameArrowUp;
    private Image gameArrowDown;
    private Image gameBalloon;
    private Image gameTransition;
    private Image gameTransfer;
    private Image gameDlgBg; // 对话框背景图
    private Image gameDlgRoleName; // 对话框角色名称图
    private Image gameMessageBoxBg; // 消息框背景图


    public void load() {
        loadGameCover();
        loadGameOver();
        loadGameArrows();
        loadGameBalloon();
        loadGameTransition();
        loadGameTransfer();
        loadGameDlgBg();
        loadGameDlgRoleName();
        loadGameMessageBoxBg();
    }

    private void loadGameMessageBoxBg() {
        try {
            InputStream is = this.getClass().getResourceAsStream("/Images/messagebox_bg.png");
            gameMessageBoxBg = ImageIO.read(is);
            IOUtils.closeQuietly(is);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadGameDlgRoleName() {
        try {
            InputStream is = this.getClass().getResourceAsStream("/Images/dlg_role_name.png");
            gameDlgRoleName = ImageIO.read(is);
            IOUtils.closeQuietly(is);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadGameDlgBg() {
        try {
            InputStream is = this.getClass().getResourceAsStream("/Images/dlg_bg.png");
            gameDlgBg = ImageIO.read(is);
            IOUtils.closeQuietly(is);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadGameTransfer() {
        try {
            InputStream is = this.getClass().getResourceAsStream("/Images/transfer.png");
            gameTransfer = ImageIO.read(is);
            IOUtils.closeQuietly(is);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadGameTransition() {
        try {
            InputStream is = this.getClass().getResourceAsStream("/Images/transition.png");
            gameTransition = ImageIO.read(is);
            IOUtils.closeQuietly(is);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void loadGameBalloon() {
        try {
            InputStream is = this.getClass().getResourceAsStream("/Images/balloon.png");
            gameBalloon = ImageIO.read(is);
            IOUtils.closeQuietly(is);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void loadGameArrows() {
        try {
            InputStream is = this.getClass().getResourceAsStream("/Images/arrowLeft.png");
            gameArrowLeft = ImageIO.read(is);
            IOUtils.closeQuietly(is);

            is = this.getClass().getResourceAsStream("/Images/arrowRight.png");
            gameArrowUp = ImageIO.read(is);
            IOUtils.closeQuietly(is);
            
            is = this.getClass().getResourceAsStream("/Images/arrowUp.png");
            gameArrowUp = ImageIO.read(is);
            IOUtils.closeQuietly(is);

            is = this.getClass().getResourceAsStream("/Images/arrowDown.png");
            gameArrowDown = ImageIO.read(is);
            IOUtils.closeQuietly(is);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadGameOver() {
        try {
            InputStream is = this.getClass().getResourceAsStream("/Images/gameover.png");
            gameOver = ImageIO.read(is);
            IOUtils.closeQuietly(is);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadGameCover() {

    }

    public IGameImage getGameCover() {
        return IGameImageFactory.getImage("/Images/gamecover.png");
    }

    public Image getGameOver() {
        return gameOver;
    }

    public Image getGameArrowLeft() {
        return gameArrowLeft;
    }

    public Image getGameArrowRight() {
        return gameArrowRight;
    }
    public IGameImage getGameArrowRightNew() {
        return IGameImageFactory.getImage("/Images/arrowRight.png");
    }

    public Image getGameArrowUp() {
        return gameArrowUp;
    }

    public Image getGameArrowDown() {
        return gameArrowDown;
    }

    public Image getGameBalloon() {
        return gameBalloon;
    }

    public Image getGameTransition() {
        return gameTransition;
    }

    public Image getGameTransfer() {
        return gameTransfer;
    }

    public Image getGameDlgBg() {
        return gameDlgBg;
    }

    public Image getGameDlgRoleName() {
        return gameDlgRoleName;
    }

    public Image getGameMessageBoxBg() {
        return gameMessageBoxBg;
    }
}
