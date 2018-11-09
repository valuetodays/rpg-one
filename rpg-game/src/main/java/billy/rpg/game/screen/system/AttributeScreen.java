package billy.rpg.game.screen.system;

import billy.rpg.common.util.TextUtil;
import billy.rpg.game.GameCanvas;
import billy.rpg.game.GameData;
import billy.rpg.game.GameFrame;
import billy.rpg.game.character.HeroCharacter;
import billy.rpg.game.character.fightable.Fightable;
import billy.rpg.game.constants.GameConstant;
import billy.rpg.game.equip.clothes.ClothesEquip;
import billy.rpg.game.equip.shoe.ShoeEquip;
import billy.rpg.game.equip.weapon.WeaponEquip;
import billy.rpg.game.screen.BaseScreen;
import billy.rpg.game.util.KeyUtil;
import billy.rpg.resource.role.RoleMetaData;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * 属性
 *
 * @author liulei@bshf360.com
 * @since 2017-09-04 10:53
 */
public class AttributeScreen extends BaseScreen {
    private final SystemUIScreen systemScreen;

    public AttributeScreen(SystemUIScreen systemScreen) {
        this.systemScreen = systemScreen;
    }

    @Override
    public void update(long delta) {

    }

    @Override
    public void draw(GameCanvas gameCanvas) {
        BufferedImage paint = new BufferedImage(GameConstant.GAME_WIDTH, GameConstant.GAME_HEIGHT,
                BufferedImage.TYPE_4BYTE_ABGR);
        Graphics g = paint.getGraphics();

        GameData gameData = GameFrame.getInstance().getGameData();
        g.setFont(new Font("楷体", Font.BOLD, 18));
        g.setColor(Color.YELLOW);

        java.util.List<HeroCharacter> heroList = gameData.getHeroList();
        for (int i = 0; i < heroList.size(); i++) {
            HeroCharacter heroCharacter = heroList.get(i);
            Fightable fightable = heroCharacter.getFightable();
            WeaponEquip weaponEquip = (WeaponEquip) heroCharacter.getEquipables().getWeapon().getEquip();
            int attackValueInEquip = weaponEquip.getAttack();
            ClothesEquip clothes = (ClothesEquip) heroCharacter.getEquipables().getClothes().getEquip();
            int defendValueInEquip = clothes.getDefend();
            ShoeEquip shoe = (ShoeEquip) heroCharacter.getEquipables().getShoe().getEquip();
            int speedValueInEquip = shoe.getSpeed();

            RoleMetaData roleMetaData = fightable.getRoleMetaData();
            g.drawString(roleMetaData.getName() + " Lv " + roleMetaData.getLevel(), 210, 70 + i * 100);
            g.drawString("hp: " + roleMetaData.getHp() + "/" + roleMetaData.getMaxHp(), 180, 90 + i * 100);
            g.drawString("mp: " + roleMetaData.getMp() + "/" + roleMetaData.getMaxMp(), 180, 110 + i * 100);
            String attackStr = "attack: " + roleMetaData.getAttack();
            if (attackValueInEquip > 0) {
                attackStr += "(+" + attackValueInEquip + ")";
            }
            g.drawString(attackStr, 180, 130 + i * 100);
            String defendStr = "defend: " + roleMetaData.getDefend();
            if (defendValueInEquip > 0) {
                defendStr += "(+" + defendValueInEquip + ")";
            }
            g.drawString(defendStr, 180, 150 + i * 100);
            String speedStr = "speed: " + roleMetaData.getSpeed();
            if (speedValueInEquip > 0) {
                speedStr += "(+" + speedValueInEquip + ")";
            }
            g.drawString(speedStr, 180, 170 + i * 100);
            g.drawImage(roleMetaData.getImage(), 320, 70 + i * 100, null);

            g.drawString("weapon:" + weaponEquip.getGoods().getName(), 180, 300);
            g.drawString("clothes:" + clothes.getGoods().getName(), 180, 320);
            g.drawString("shoe:" + shoe.getGoods().getName(), 180, 340);
        }

        gameCanvas.drawBitmap(paint, 0, 0);
    }

    private void drawColorStringHorizontal(int x, int y, String prefixText, Color prefixColor, String suffixText, Color suffix) {
        TextUtil.drawStringWithColor();

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
