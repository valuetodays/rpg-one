package billy.rpg.game.screen.system;

import billy.rpg.game.GameCanvas;
import billy.rpg.game.GameData;
import billy.rpg.game.GameFrame;
import billy.rpg.game.character.ex.character.HeroCharacter;
import billy.rpg.game.character.ex.equipable.Equipable;
import billy.rpg.game.character.ex.fightable.Fightable;
import billy.rpg.game.constants.GameConstant;
import billy.rpg.game.equip.weapon.WeaponEquip;
import billy.rpg.game.screen.BaseScreen;
import billy.rpg.game.util.KeyUtil;
import billy.rpg.resource.role.RoleMetaData;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author liulei@bshf360.com
 * @since 2017-09-04 10:53
 */
public class AttributeScreen extends BaseScreen {
    private final SystemScreen systemScreen;

    public AttributeScreen(SystemScreen systemScreen) {
        this.systemScreen = systemScreen;
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

        GameData gameData = GameFrame.getInstance().getGameData();
        g.setFont(new Font("楷体", Font.BOLD, 18));
        g.setColor(Color.YELLOW);

        java.util.List<HeroCharacter> heroList = gameData.getHeroList();
        for (int i = 0; i < heroList.size(); i++) {
            HeroCharacter heroCharacter = heroList.get(i);
            Fightable fightable = heroCharacter.getFightable();
            Equipable weapon = heroCharacter.getEquipables().getWeapon();
            WeaponEquip weaponEquip = (WeaponEquip) weapon.getEquip();
            int attackValueInEquip = weaponEquip.getAttack();

            RoleMetaData roleMetaData = fightable.getRoleMetaData();
            g.drawString(roleMetaData.getName() + " Lv " + roleMetaData.getLevel(), 210, 70 + i*100);
            g.drawString("hp: " + roleMetaData.getHp() + "/" + roleMetaData.getMaxHp(), 180, 90 + i*100);
            g.drawString("mp: " + roleMetaData.getMp() + "/" + roleMetaData.getMaxMp(), 180, 110 + i*100);
            g.drawString("attack: " + roleMetaData.getAttack() + "(+"+attackValueInEquip+")", 180, 130 + i*100);
            g.drawString("defend: " + roleMetaData.getDefend(), 180, 150 + i*100);
            g.drawImage(roleMetaData.getImage(), 320, 70 + i*100, null);

        }

        gameCanvas.drawBitmap(paint, 0, 0);
    }

    @Override
    public void onKeyDown(int key) {

    }

    @Override
    public void onKeyUp(int key) {
        if (KeyUtil.isEsc(key)) {
            systemScreen.pop();
        }
    }
}
