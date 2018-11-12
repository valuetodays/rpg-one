package billy.rpg.game.screen.system;

import billy.rpg.common.util.TextUtil;
import billy.rpg.game.GameCanvas;
import billy.rpg.game.GameData;
import billy.rpg.game.GameFrame;
import billy.rpg.game.character.HeroCharacter;
import billy.rpg.game.constants.GameConstant;
import billy.rpg.game.equip.clothes.ClothesEquip;
import billy.rpg.game.equip.shoe.ShoeEquip;
import billy.rpg.game.equip.weapon.WeaponEquip;
import billy.rpg.game.screen.BaseScreen;
import billy.rpg.game.util.KeyUtil;
import billy.rpg.resource.role.RoleMetaData;
import billy.rpg.resource.sprite.HeroSprite;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * 属性
 *
 * @author liulei@bshf360.com
 * @since 2017-09-04 10:53
 */
public class AttributeScreen extends BaseScreen {
    private final SystemUIScreen systemScreen;

    private java.util.List<HeroCharacter> heroList;

    public AttributeScreen(SystemUIScreen systemScreen) {
        this.systemScreen = systemScreen;
        GameData gameData = GameFrame.getInstance().getGameData();
        heroList = gameData.getHeroList();
    }

    @Override
    public void update(long delta) {
        for (HeroCharacter heroCharacter : heroList) {
            RoleMetaData roleMetaData = heroCharacter.getRoleMetaData();
            roleMetaData.getSprite().update(delta);
        }
    }

    @Override
    public void draw(GameCanvas gameCanvas) {
        BufferedImage paint = new BufferedImage(GameConstant.GAME_WIDTH, GameConstant.GAME_HEIGHT,
                BufferedImage.TYPE_4BYTE_ABGR);
        Graphics g = paint.getGraphics();

        g.setFont(new Font("楷体", Font.BOLD, 18));
        g.setColor(Color.YELLOW);

        for (int i = 0; i < heroList.size(); i++) {
            HeroCharacter heroCharacter = heroList.get(i);
            WeaponEquip weaponEquip = (WeaponEquip) heroCharacter.getEquipables().getWeapon().getEquip();
            int attackValueInEquip = weaponEquip.getAttack();
            ClothesEquip clothes = (ClothesEquip) heroCharacter.getEquipables().getClothes().getEquip();
            int defendValueInEquip = clothes.getDefend();
            ShoeEquip shoe = (ShoeEquip) heroCharacter.getEquipables().getShoe().getEquip();
            int speedValueInEquip = shoe.getSpeed();

            RoleMetaData roleMetaData = heroCharacter.getRoleMetaData();
            // TODO 如下三个属性使用矩形画出来，并使用渐变色哦
            g.drawString(roleMetaData.getName() + " Lv " + roleMetaData.getLevel(), 210, 70 + i * 100);
            g.drawString("hp: " + roleMetaData.getHp() + "/" + roleMetaData.getMaxHp(), 180, 90 + i * 100);
            g.drawString("mp: " + roleMetaData.getMp() + "/" + roleMetaData.getMaxMp(), 180, 110 + i * 100);
            String attackStr = "attack: " + roleMetaData.getAttack();
            if (attackValueInEquip > 0) {
                String attackSuffix = "(+" + attackValueInEquip + ")";
                drawColorStringHorizontal(g, 180, 130 + i*100, attackStr, g.getColor(), attackSuffix, Color.ORANGE);
            } else {
                drawColorStringHorizontal(g, 180, 130 + i*100, attackStr, g.getColor(), null, null);
            }

            String defendStr = "defend: " + roleMetaData.getDefend();
            if (defendValueInEquip > 0) {
                String defendSuffix = "(+" + defendValueInEquip + ")";
                drawColorStringHorizontal(g, 180, 150 + i * 100, defendStr, g.getColor(), defendSuffix, Color.ORANGE);
            } else {
                drawColorStringHorizontal(g, 180, 150 + i * 100, defendStr, g.getColor(), null, null);
            }

            String speedStr = "speed: " + roleMetaData.getSpeed();
            if (speedValueInEquip > 0) {
                String speedSuffix = "(+" + speedValueInEquip + ")";
                drawColorStringHorizontal(g, 180, 170 + i * 100, speedStr, g.getColor(), speedSuffix, Color.ORANGE);
            } else {
                drawColorStringHorizontal(g, 180, 170 + i * 100, speedStr, g.getColor(), null, null);
            }

            g.drawImage(roleMetaData.getImage(), 320, 70 + i * 100, null);
            HeroSprite.Key currentFrame = roleMetaData.getSprite().getCurrentFrame();
            g.drawImage(currentFrame.getImage(), 320 + currentFrame.getX(), 70 + i * 100 + currentFrame.getY(), null);

            g.drawString("weapon:" + weaponEquip.getGoods().getName(), 180, 300);
            g.drawString("clothes:" + clothes.getGoods().getName(), 180, 320);
            g.drawString("shoe:" + shoe.getGoods().getName(), 180, 340);
        }

        gameCanvas.drawBitmap(paint, 0, 0);
    }

    private void drawColorStringHorizontal(Graphics g, int x, int y, String prefixText, Color prefixColor, String suffixText, Color suffixColor) {
        List<String> texts = new ArrayList<>();
        texts.add(prefixText);
        List<Color> colors = new ArrayList<>();
        colors.add(prefixColor);

        if (suffixText != null && suffixColor != null) {
            texts.add(suffixText);
            colors.add(suffixColor);
        }

        TextUtil.drawColorStringHorizontal(g, texts, colors, x, y);
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
