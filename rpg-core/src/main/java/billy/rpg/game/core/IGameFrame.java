package billy.rpg.game.core;

import billy.rpg.game.core.constants.ScreenCodeEnum;
import billy.rpg.game.core.container.GameContainer;
import billy.rpg.game.core.screen.BaseScreen;
import billy.rpg.game.core.screen.battle.BattleScreen;
import java.awt.Color;
import java.awt.Font;

public interface IGameFrame {
    void changeScreen(ScreenCodeEnum code);

    BaseScreen getCurScreen();

    GameContainer getGameContainer();

    GamePanel getGamePanel();

    void setTitle(String title);

    void pushScreen(BaseScreen bs);

    void popScreen();

    void change2BattleScreen(BattleScreen battleScreen);

    Color getFPSColor();
    Font getFPSFont();

}
