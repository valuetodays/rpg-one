package billy.rpg.game.screen.battle;

import billy.rpg.game.GameCanvas;
import billy.rpg.game.GameFrame;
import billy.rpg.game.constants.GameConstant;
import billy.rpg.game.screen.BaseScreen;
import billy.rpg.game.screen.MessageBoxScreen;
import billy.rpg.game.util.KeyUtil;
import billy.rpg.resource.skill.SkillMetaData;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * 战斗中选择技界面
 */
public class SkillSelectScreen extends BaseScreen {

    private BattleUIScreen battleUIScreen;
    private BattleOptionScreen battleOptionScreen;
    private final java.util.List<SkillMetaData> skillList;
    private int skillIndex;

    public SkillSelectScreen(BattleUIScreen battleUIScreen, BattleOptionScreen battleOptionScreen) {
        this.battleUIScreen = battleUIScreen;
        this.battleOptionScreen = battleOptionScreen;

        // 技能列表
        int index = getBattleUIScreen().getActiveHeroIndex();
        this.skillList = GameFrame.getInstance().getGameData().getSkillsOf(index);
    }

    public BattleUIScreen getBattleUIScreen() {
        return battleUIScreen;
    }

    @Override
    public void update(long delta) {

    }

    @Override
    public void draw(GameCanvas gameCanvas) {
        if (getBattleUIScreen().fighting) {
            return;
        }

        BufferedImage paint = new BufferedImage(
                GameConstant.GAME_WIDTH,
                GameConstant.GAME_HEIGHT,
                BufferedImage.TYPE_4BYTE_ABGR);
        Graphics g = paint.getGraphics();
        g.setFont(GameConstant.FONT_BATTLE);
        g.setColor(Color.black);
        g.fillRect(0, 0, paint.getWidth(), 320); // 大小和战斗背景一致为640*320
        g.setColor(Color.YELLOW);
        g.drawRoundRect(10, 10, 620, 200, 5, 5);
        Image gameArrowRight = GameFrame.getInstance().getGameContainer().getGameAboutItem().getGameArrowRight();
        g.drawImage(gameArrowRight, 15, 15 + skillIndex * 20, null);
        for (int i = 0; i < skillList.size(); i++) {
            SkillMetaData smd = skillList.get(i);
            g.drawString(smd.getName() + " [消耗" + smd.getConsume() +"]", 30, 30 + i * 25);
        }
        g.drawString(skillList.get(skillIndex).getDesc(), 35, 230);

        gameCanvas.drawBitmap(paint, 0, 0);
    }

    @Override
    public void onKeyDown(int key) {

    }

    @Override
    public void onKeyUp(int key) {
        if (KeyUtil.isEsc(key)) {
            getBattleUIScreen().getParentScreen().pop();
        } else if (KeyUtil.isEnter(key)) {
            int skillId = skillList.get(skillIndex).getNumber();
            SkillMetaData skillMetaData = GameFrame.getInstance().getGameContainer().getSkillMetaDataOf(skillId);
            final int mp = getBattleUIScreen().getActiveHero().getFightable().getRoleMetaData().getMp();
            final int consume = skillMetaData.getConsume();
            if (mp < consume) {
                final BaseScreen bs = new MessageBoxScreen("mp不足"+consume+"，不能施放技能" + skillMetaData.getName(),
                        getBattleUIScreen().getParentScreen());
                getBattleUIScreen().getParentScreen().push(bs);
                return;
            }
            MonsterSelectScreen chooseMonsterScreen = new MonsterSelectScreen(getBattleUIScreen(),
                    battleOptionScreen, skillId);
            getBattleUIScreen().getParentScreen().push(chooseMonsterScreen);
        } else if (KeyUtil.isUp(key)) {
            skillIndex--;
            if (skillIndex < 0) {
                skillIndex = skillList.size()-1;
            }
        } else if (KeyUtil.isDown(key)) {
            skillIndex++;
            if (skillIndex > skillList.size()-1) {
                skillIndex = 0;
            }
        }
    }


    @Override
    public boolean isPopup() {
        return true;
    }



}
