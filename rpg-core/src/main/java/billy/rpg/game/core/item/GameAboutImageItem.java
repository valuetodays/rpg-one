package billy.rpg.game.core.item;

import billy.rpg.common.util.AssetsUtil;
import billy.rpg.game.core.platform.image.IGameImage;
import billy.rpg.game.core.platform.image.IGameImageFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;

public class GameAboutImageItem {
    private Image gameCover;
    private Image gameOver;
    private Image gameArrowLeft;
    private Image gameArrowRight;
    private Image gameArrowUp;
    private Image gameArrowDown;
    private Image gameTransition;
    private Image gameTransfer;
    private Image gameDlgBg; // 对话框背景图
    private Image gameDlgRoleName; // 对话框角色名称图
    private Image gameMessageBoxBg; // 消息框背景图
    private Image gameEmotion;


    public void load() {
        loadGameCover();
        loadGameOver();
        loadGameArrows();
        loadGameTransition();
        loadGameTransfer();
        loadGameDlgBg();
        loadGameDlgRoleName();
        loadGameMessageBoxBg();
        loadGameEmotion();
    }

    private void loadGameEmotion() {
        try {
            String resourcePath = AssetsUtil.getResourcePath("/assets/Images/emotion.png");
            gameEmotion = ImageIO.read(new File(resourcePath));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadGameMessageBoxBg() {
        try {
            String resourcePath = AssetsUtil.getResourcePath("/assets/Images/messagebox_bg.png");
            gameMessageBoxBg = ImageIO.read(new File(resourcePath));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadGameDlgRoleName() {
        try {
            String resourcePath = AssetsUtil.getResourcePath("/assets/Images/dlg_role_name.png");
            gameDlgRoleName = ImageIO.read(new File(resourcePath));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadGameDlgBg() {
        try {
            String resourcePath = AssetsUtil.getResourcePath("/assets/Images/dlg_bg.png");
            gameDlgBg = ImageIO.read(new File(resourcePath));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadGameTransfer() {
        try {
            String resourcePath = AssetsUtil.getResourcePath("/assets/Images/transfer.png");
            gameTransfer = ImageIO.read(new File(resourcePath));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadGameTransition() {
        try {
            String resourcePath = AssetsUtil.getResourcePath("/assets/Images/transition.png");
            gameTransition = ImageIO.read(new File(resourcePath));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void loadGameArrows() {
        try {
            String resourcePath = AssetsUtil.getResourcePath("/assets/Images/ArrowLeft.png");
            gameArrowLeft = ImageIO.read(new File(resourcePath));

            resourcePath = AssetsUtil.getResourcePath("/assets/Images/ArrowRight.png");
            gameArrowRight = ImageIO.read(new File(resourcePath));

            resourcePath = AssetsUtil.getResourcePath("/assets/Images/ArrowUp.png");
            gameArrowUp = ImageIO.read(new File(resourcePath));

            resourcePath = AssetsUtil.getResourcePath("/assets/Images/ArrowDown.png");
            gameArrowDown = ImageIO.read(new File(resourcePath));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadGameOver() {
        try {
            String resourcePath = AssetsUtil.getResourcePath("/assets/Images/gameover.png");
            gameOver = ImageIO.read(new File(resourcePath));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadGameCover() {
        try {
            String resourcePath = AssetsUtil.getResourcePath("/assets/Images/gamecover.png");
            gameCover = ImageIO.read(new File(resourcePath));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Image getGameCover() {
        return gameCover;
    }
    public IGameImage getGameCoverNew() {
        return IGameImageFactory.getImage("/assets/Images/gamecover.png");
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
        return IGameImageFactory.getImage("/assets/Images/ArrowRight.png");
    }

    public Image getGameArrowUp() {
        return gameArrowUp;
    }

    public Image getGameArrowDown() {
        return gameArrowDown;
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

    public Image getGameEmotion() {
        return gameEmotion;
    }
}
