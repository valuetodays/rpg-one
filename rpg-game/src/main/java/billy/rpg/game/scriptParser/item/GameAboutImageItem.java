package billy.rpg.game.scriptParser.item;

import billy.rpg.game.scriptParser.bean.LoaderBean;
import billy.rpg.game.scriptParser.loader.image.IImageLoader;
import org.apache.commons.io.IOUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.InputStream;
import java.util.List;

public class GameAboutImageItem implements IImageLoader, IItem {
    private Image gameCover;
    private Image gameOver;
    private Image gameArrowLeft;
    private Image gameArrowRight;
    private Image gameArrowUp;
    private Image gameArrowDown;
    private Image gameBalloon;
    private Image gameTransition;


    @Override
    public List<LoaderBean> load() throws Exception {
        load0();
        return null;
    }

    private void load0() {
        loadGameCover();
        loadGameOver();
        loadGameArrows();
        loadGameBalloon();
        loadGameTransition();
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
}
