package com.billy.scriptParser.item;

import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import com.billy.scriptParser.bean.LoaderBean;
import com.billy.scriptParser.loader.image.IImageLoader;
import com.rupeng.game.GameUtils;

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
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
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
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
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
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
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
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
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
