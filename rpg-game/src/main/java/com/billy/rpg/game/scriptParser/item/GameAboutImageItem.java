package com.billy.rpg.game.scriptParser.item;

import com.billy.rpg.game.scriptParser.bean.LoaderBean;
import com.billy.rpg.game.scriptParser.loader.image.IImageLoader;
import com.rupeng.game.GameUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
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
            File file = new File(imgPath);
            FileInputStream fileInputStream = new FileInputStream(file);
            gameBalloon = ImageIO.read(fileInputStream);
            fileInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void loadGameArrow() {
        String imgPath = GameUtils.mapPath("Images") + "/arrow.png";

        try {
            File file = new File(imgPath);
            FileInputStream fileInputStream = new FileInputStream(file);
            gameArrow = ImageIO.read(fileInputStream);
            fileInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadGameOver() {
        String imgPath = GameUtils.mapPath("Images") + "/gameover.png";

        try {
            File file = new File(imgPath);
            FileInputStream fileInputStream = new FileInputStream(file);
            gameOver = ImageIO.read(fileInputStream);
            fileInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadGameCover() {
        String imgPath = GameUtils.mapPath("Images") + "/gamecover.png";

        try {
            File file = new File(imgPath);
            FileInputStream fileInputStream = new FileInputStream(file);
            gameCover = ImageIO.read(fileInputStream);
            fileInputStream.close();
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
