package billy.rpg.game.constants;

import java.awt.*;
import java.util.Random;

public interface GameConstant {

    /**
     * 游戏标题
     */
    String GAME_TITLE = "伏魔记";

    /**
     * 游戏tile宽度（像素）
     */
    int GAME_TILE_WIDTH     = 32;
    /**
     * 游戏tile高度（像素）
     */
    int GAME_TILE_HEIGHT    = 32;
    /**
     * 游戏窗口客户区宽度（像素）
     */
    int GAME_WIDTH          = 640;
    /**
     * 游戏窗口客户区高度（像素）
     */
    int GAME_HEIGHT         = 480;
    /**
     * 一屏纵向tile个数
     */
    int Game_TILE_X_NUM     = GAME_WIDTH / GAME_TILE_WIDTH;
    /**
     * 一屏横向tile个数
     */
    int Game_TILE_Y_NUM     = GAME_HEIGHT / GAME_TILE_HEIGHT;
    /**
     * 游戏主循环更新时间(ms)
     */
    long TIME_GAMELOOP      = 25;
    /**
     * 游戏在windows桌面的左上角的左坐标
     */
    int GAME_WINDOW_LEFT    = 400;
    /**
     * 游戏在windows桌面的左上角的上坐标
     */
    int GAME_WINDOW_TOP     = 100;
    /**
     * 游戏放大缩小倍数
     */
    int SCALE = 1;

    /**
     * 对话框中一行有多少个字
     */
    int WORDS_NUM_PER_LINE = 20;
    Font FONT_DLG_MSG = new Font("楷体", Font.BOLD, 24);
    Font FONT_ROLENAME_IN_DLG = new Font("楷体", Font.BOLD, 18);
    Font FONT_BATTLE = new Font("楷体", Font.BOLD, 18);
    Font FONT_DAMAGE = new Font("楷体", Font.BOLD, 28);

    /**
     * 游戏中使用的随机数生成器
     */
    Random random = new Random();

}
