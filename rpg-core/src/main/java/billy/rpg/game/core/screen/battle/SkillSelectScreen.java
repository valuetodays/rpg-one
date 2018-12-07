package billy.rpg.game.core.screen.battle;

import billy.rpg.game.core.DesktopCanvas;
import billy.rpg.game.core.constants.GameConstant;
import billy.rpg.game.core.container.GameContainer;
import billy.rpg.game.core.screen.BaseScreen;
import billy.rpg.game.core.screen.MessageBoxScreen;
import billy.rpg.game.core.util.KeyUtil;
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

    public SkillSelectScreen(GameContainer gameContainer, BattleUIScreen battleUIScreen, BattleOptionScreen battleOptionScreen) {
        this.battleUIScreen = battleUIScreen;
        this.battleOptionScreen = battleOptionScreen;

        // 技能列表
        int index = getBattleUIScreen().getActiveHeroIndex();
        this.skillList = gameContainer.getGameData().getSkillsOf(gameContainer, index);
    }

    public BattleUIScreen getBattleUIScreen() {
        return battleUIScreen;
    }

    @Override
    public void update(GameContainer gameContainer, long delta) {

    }

    @Override
    public void draw(GameContainer gameContainer, DesktopCanvas desktopCanvas) {
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
        Image gameArrowRight =gameContainer.getGameAboutItem().getGameArrowRight();
        g.drawImage(gameArrowRight, 15, 15 + skillIndex * 20, null);
        for (int i = 0; i < skillList.size(); i++) {
            SkillMetaData smd = skillList.get(i);
            g.drawString(smd.getName() + " [消耗" + smd.getConsume() +"]", 30, 30 + i * 25);
        }
        g.drawString(skillList.get(skillIndex).getDesc(), 35, 230);

        desktopCanvas.drawBitmap(gameContainer.getGameFrame(), paint, 0, 0);
    }

    @Override
    public void onKeyDown(GameContainer gameContainer, int key) {

    }

    @Override
    public void onKeyUp(GameContainer gameContainer, int key) {
        if (KeyUtil.isEsc(key)) {
            getBattleUIScreen().getParentScreen().pop();
        } else if (KeyUtil.isEnter(key)) {
            int skillId = skillList.get(skillIndex).getNumber();
            SkillMetaData skillMetaData = gameContainer.getSkillMetaDataOf(skillId);
            final int mp = getBattleUIScreen().getActiveHero().getRoleMetaData().getMp();
            final int consume = skillMetaData.getConsume();
            if (mp < consume) {
                final BaseScreen bs = new MessageBoxScreen("mp不足，不能施放技能" + skillMetaData.getName(),
                        getBattleUIScreen().getParentScreen());
                getBattleUIScreen().getParentScreen().push(bs);
                return;
            }
            int targetType = skillMetaData.getTargetType();
            logger.debug("targetType: " + targetType);
            if (SkillMetaData.TARGET_TYPE_SINGLE == targetType) {
                MonsterSelectScreen chooseMonsterScreen = new MonsterSelectScreen(getBattleUIScreen(),
                        battleOptionScreen, skillId);
                getBattleUIScreen().getParentScreen().push(chooseMonsterScreen);
            } else {
            }
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
