package billy.rpg.game.core.screen.battle;

import billy.rpg.game.core.DesktopCanvas;
import billy.rpg.game.core.buff.Buff;
import billy.rpg.game.core.buff.util.BuffUtil;
import billy.rpg.game.core.character.fightable.Fightable;
import billy.rpg.game.core.constants.GameConstant;
import billy.rpg.game.core.container.GameContainer;
import billy.rpg.game.core.screen.AnimationScreen;
import billy.rpg.resource.skill.SkillMetaData;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

/**
 *
 * 独立出此类去处理技能的攻击、施加我方buff、施加敌方buff、回复生命等逻辑
 *
 * @author lei.liu@datatist.com
 * @since 2018-12-11 14:13:38
 */
public class BattleSkillActionDeterminer {

    /**

     */
    public void onDraw(GameContainer gameContainer, DesktopCanvas desktopCanvas, SkillMetaData skillMetaData, List<Integer> dmgs, int dmgLeft, int dmgTop) {
        int type = skillMetaData.getType();
        if (SkillMetaData.TYPE_ATTACK == type) {
            BufferedImage paint = new BufferedImage(
                    GameConstant.GAME_WIDTH,
                    GameConstant.GAME_HEIGHT,
                    BufferedImage.TYPE_4BYTE_ABGR);
            Graphics g = paint.getGraphics();
            g.setFont(gameContainer.getGameConfig().getDamageFont());
            g.setColor(gameContainer.getGameConfig().getDamageSkillFontColor());
            for (int i = 0; i < dmgs.size(); i++) { // 群攻显示扣除的血量
                Integer dmg = dmgs.get(i);
                g.drawString("-" + dmg, dmgLeft + 100 * i, dmgTop); //
            }
            g.dispose();
            desktopCanvas.drawBitmap(gameContainer.getGameFrame(), paint, 0, 0);
        } else if (SkillMetaData.TYPE_ADD_BUFF_TO_OUR == type) {
            Buff buff = BuffUtil.skillToBuff(skillMetaData);
            BufferedImage paint = new BufferedImage(
                    GameConstant.GAME_WIDTH,
                    GameConstant.GAME_HEIGHT,
                    BufferedImage.TYPE_4BYTE_ABGR);
            Graphics g = paint.getGraphics();
            g.setFont(gameContainer.getGameConfig().getDamageFont());
            g.setColor(gameContainer.getGameConfig().getDamageSkillFontColor());
            for (int i = 0; i < dmgs.size(); i++) {
                g.drawString("+" + buff.getName() + buff, dmgLeft + 100 * i, dmgTop); //
            }
            g.dispose();
            desktopCanvas.drawBitmap(gameContainer.getGameFrame(), paint, 0, 0);
        }
    }

    public AnimationScreen determineAnimationScreen(GameContainer gameContainer, SkillMetaData skillMetaData, int targetIndex, BattleScreen battleScreen, List<Fightable> targets) {
        AnimationScreen animationScreen = null;
        int type = skillMetaData.getType();
        if (SkillMetaData.TYPE_ATTACK == type) {
            if (targetIndex == -1) {
                animationScreen = new AnimationScreen(gameContainer, skillMetaData.getAnimationId(),
                        300, 80, battleScreen);
            } else {
                Fightable chosenMonsterBattle = targets.get(targetIndex);
                animationScreen = new AnimationScreen(gameContainer, skillMetaData.getAnimationId(),
                        chosenMonsterBattle.getLeft() - chosenMonsterBattle.getWidth() / 2,
                        chosenMonsterBattle.getTop(), battleScreen);
            }
        } else if (SkillMetaData.TYPE_ADD_BUFF_TO_OUR == type) {
            if (targetIndex == -1) {
                animationScreen = new AnimationScreen(gameContainer, skillMetaData.getAnimationId(),
                        300, 380, battleScreen);
            } else {
                Fightable chosenHeroBattle = targets.get(targetIndex);
                animationScreen = new AnimationScreen(gameContainer, skillMetaData.getAnimationId(),
                        chosenHeroBattle.getLeft() - chosenHeroBattle.getWidth() / 2,
                        chosenHeroBattle.getTop(), battleScreen);
            }
        }
        return animationScreen;
    }
}
