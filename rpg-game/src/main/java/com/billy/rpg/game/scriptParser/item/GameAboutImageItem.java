package com.billy.rpg.game.scriptParser.item;

import com.billy.rpg.game.scriptParser.bean.LoaderBean;
import com.billy.rpg.game.scriptParser.loader.image.IImageLoader;
import com.rupeng.game.GameUtils;
import org.apache.commons.io.IOUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.InputStream;
import java.util.List;

public class GameAboutImageItem implements IImageLoader, IItem {
    private Image gameCover;
    private Image gameOver;
    private Image gameArrow;
    private Image gameBalloon;


    @Override
    public List<LoaderBean> load() throws Exception {
        load0();
        return null;
    }

    private void load0() {
        loadGameCover();
        loadGameOver();
        loadGameArrow();
        loadGameBalloon();
    }

    private void loadGameBalloon() {
        String imgPath = GameUtils.mapPath("Images") + "/balloon.png";

        try {
            InputStream is = this.getClass().getResourceAsStream("/Images/balloon.png");
            gameBalloon = ImageIO.read(is);
            IOUtils.closeQuietly(is);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void loadGameArrow() {
        String imgPath = GameUtils.mapPath("Images") + "/arrow.png";

        try {
            InputStream is = this.getClass().getResourceAsStream("/Images/arrow.png");
            gameArrow = ImageIO.read(is);
            IOUtils.closeQuietly(is);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadGameOver() {
        String imgPath = GameUtils.mapPath("Images") + "/gameover.png";

        try {
            InputStream is = this.getClass().getResourceAsStream("/Images/gameover.png");
            gameOver = ImageIO.read(is);
            IOUtils.closeQuietly(is);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadGameCover() {
        String imgPath = GameUtils.mapPath("Images") + "/gamecover.png";

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

    public Image getGameArrow() {
        return gameArrow;
    }

    public Image getGameBalloon() {
        return gameBalloon;
    }

}
