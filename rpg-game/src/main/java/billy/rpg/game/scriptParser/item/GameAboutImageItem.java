package billy.rpg.game.scriptParser.item;

import org.apache.commons.io.IOUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.InputStream;

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
    private Image gameDlgBg;
    private Image gameDlgRoleName;


    public void load() {
        loadGameCover();
        loadGameOver();
        loadGameArrows();
        loadGameBalloon();
        loadGameTransition();
        loadGameTransfer();
        loadGameDlgBg();
        loadGameDlgRoleName();
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
            gameArrowRight = ImageIO.read(is);
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
        try {
            InputStream is = this.getClass().getResourceAsStream("/Images/gamecover.png");
            gameCover = ImageIO.read(is);
            IOUtils.closeQuietly(is);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public Image getGameCover() {
        return gameCover;
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
}
