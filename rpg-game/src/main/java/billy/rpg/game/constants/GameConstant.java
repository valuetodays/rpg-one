package billy.rpg.game.constants;

import java.awt.*;
import java.util.Random;

public interface GameConstant {

    String GAME_TITLE = "伏魔记";

    int GAME_WIDTH    = 640;
    int GAME_HEIGHT   = 480;
    long TIME_GAMELOOP = 25;
    int GAME_WINDOW_LEFT = 400;
    int GAME_WINDOW_TOP = 100;

    int SCALE = 1; //游戏放大缩小倍数

    /**
     * 对话框中一行有多少个字
     */
    int WORDS_NUM_PER_LINE = 24;
    Font FONT_DLG_MSG = new Font("楷体", Font.BOLD, 24);
    Font FONT_ROLENAME_IN_DLG = new Font("楷体", Font.BOLD, 18);
    Font FONT_BATTLE = new Font("楷体", Font.BOLD, 18);

    Random random = new Random();

}
