package billy.rpg.game.core.item;

import billy.rpg.common.util.AssetsUtil;
import billy.rpg.game.core.platform.image.IGameImage;
import billy.rpg.game.core.platform.image.IGameImageFactory;
import java.awt.Image;

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
        gameEmotion = AssetsUtil.getResourceAsImage("/assets/Images/emotion.png");
    }

    private void loadGameMessageBoxBg() {
        gameMessageBoxBg = AssetsUtil.getResourceAsImage("/assets/Images/messagebox_bg.png");
    }

    private void loadGameDlgRoleName() {
        gameDlgRoleName = AssetsUtil.getResourceAsImage("/assets/Images/dlg_role_name.png");
    }

    private void loadGameDlgBg() {
        gameDlgBg = AssetsUtil.getResourceAsImage("/assets/Images/dlg_bg.png");
    }

    private void loadGameTransfer() {
        gameTransfer = AssetsUtil.getResourceAsImage("/assets/Images/transfer.png");
    }

    private void loadGameTransition() {
        gameTransition = AssetsUtil.getResourceAsImage("/assets/Images/transition.png");
    }

    private void loadGameArrows() {
        gameArrowLeft = AssetsUtil.getResourceAsImage("/assets/Images/ArrowLeft.png");
        gameArrowRight = AssetsUtil.getResourceAsImage("/assets/Images/ArrowRight.png");
        gameArrowUp = AssetsUtil.getResourceAsImage("/assets/Images/ArrowUp.png");
        gameArrowDown = AssetsUtil.getResourceAsImage("/assets/Images/ArrowDown.png");
    }

    private void loadGameOver() {
        gameOver = AssetsUtil.getResourceAsImage("/assets/Images/gameover.png");
    }

    private void loadGameCover() {
        gameCover = AssetsUtil.getResourceAsImage("/assets/Images/gamecover.png");
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
