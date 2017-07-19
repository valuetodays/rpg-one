package billy.rpg.game.screen.battle;

import billy.rpg.game.GameCanvas;
import billy.rpg.game.GameFrame;
import billy.rpg.game.character.battle.HeroBattle;
import billy.rpg.game.constants.GameConstant;
import billy.rpg.game.screen.BaseScreen;
import billy.rpg.game.util.KeyUtil;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

/**
 * @author liulei@bshf360.com
 * @since 2017-07-19 10:37
 */
@Deprecated
public class ChooseHeroScreen extends BaseScreen {
    private int heroIndex; // TODO 貌似早期的fc封神榜、吞食天地不支持玩家选择已方人员乱序行动吧？

    private BattleScreen parentScreen;
    protected java.util.List<HeroBattle> heroBattleList;
    public ChooseHeroScreen(BattleScreen battleScreen, List<HeroBattle> heroBattleList) {
        parentScreen = battleScreen;
        this.heroBattleList = heroBattleList;
    }


    @Override
    public void update(long delta) {

    }

    @Override
    public void draw(GameCanvas gameCanvas) {
        BufferedImage paint = new BufferedImage(
                GameConstant.GAME_WIDTH,
                GameConstant.GAME_HEIGHT,
                BufferedImage.TYPE_4BYTE_ABGR);
        Graphics g = paint.getGraphics();

        // 选择哪个hero要进行行动
        if (1 == 1) {
            Image gameArrowDown = GameFrame.getInstance().getGameContainer().getGameAboutItem().getGameArrowDown();
            HeroBattle heroBattle = heroBattleList.get(heroIndex);
            g.drawImage(gameArrowDown,
                    heroBattle.getLeft() + heroBattle.getWidth() / 2,
                    heroBattle.getTop(), null);
        }
        gameCanvas.drawBitmap(paint, 0, 0);
    }

    @Override
    public void onKeyDown(int key) {

    }

    @Override
    public void onKeyUp(int key) {
        if (KeyUtil.isEsc(key)) {

        } else if (KeyUtil.isEnter(key)) {

        }
/*
        if (KeyUtil.isLeft(key)) {
            heroIndex--;
            if (heroIndex < 0) {
                heroIndex = heroBattleList.size()-1;
            }
        } else if (KeyUtil.isRight(key)) {
            heroIndex++;
            if (heroIndex > heroBattleList.size()-1) {
                heroIndex = 0;
            }
        }*/
    }

    @Override
    public boolean isPopup() {
        return true;
    }
}
