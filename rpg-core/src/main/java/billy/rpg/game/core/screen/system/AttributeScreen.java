package billy.rpg.game.core.screen.system;

import billy.rpg.common.util.TextUtil;
import billy.rpg.game.core.DesktopCanvas;
import billy.rpg.game.core.GameData;
import billy.rpg.game.core.character.HeroCharacter;
import billy.rpg.game.core.constants.GameConstant;
import billy.rpg.game.core.container.GameContainer;
import billy.rpg.game.core.equip.clothes.ClothesEquip;
import billy.rpg.game.core.equip.shoe.ShoeEquip;
import billy.rpg.game.core.equip.weapon.WeaponEquip;
import billy.rpg.game.core.screen.BaseScreen;
import billy.rpg.game.core.util.KeyUtil;
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

    public AttributeScreen(GameContainer gameContainer, SystemUIScreen systemScreen) {
        this.systemScreen = systemScreen;
        GameData gameData = gameContainer.getGameData();
        heroList = gameData.getHeroList(gameContainer);
    }

    @Override
    public void update(GameContainer gameContainer, long delta) {
        for (HeroCharacter heroCharacter : heroList) {
            RoleMetaData roleMetaData = heroCharacter.getRoleMetaData();
            roleMetaData.getSprite().update(delta);
        }
    }

    @Override
    public void draw(GameContainer gameContainer, DesktopCanvas desktopCanvas) {
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
            g.drawString(roleMetaData.getName() + " Lv " + roleMetaData.getLevel(), 210, 70 + i * 100);
            g.drawString("hp: " + roleMetaData.getHp() + "/" + roleMetaData.getMaxHp(), 180, 90 + i * 100);
//            // 画矩形渐变血条 开始
//            g.setColor(Color.GRAY);
//            roleMetaData.setHp(roleMetaData.getMaxHp()/2);
//            g.fillRect(180, 90 + i*100, 300, 20); // 300是最大矩形的宽度
//            GradientPaint gradientPaint = new GradientPaint(180, 90 + i * 100, Color.YELLOW, 480, 90 + i * 100 + 20, Color.magenta);
//            ((Graphics2D)g).setPaint(gradientPaint);
//            g.fillRect(180, 90 + i* 100, (int)(roleMetaData.getHp()*1.0/roleMetaData.getMaxHp() * 300), 20);
//            // 画矩形渐变血条 结束

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

        desktopCanvas.drawBitmap(gameContainer.getGameFrame(), paint, 0, 0);
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
    public void onKeyDown(GameContainer gameContainer, int key) {

    }

    @Override
    public void onKeyUp(GameContainer gameContainer, int key) {
        if (KeyUtil.isEsc(key)) {
            systemScreen.pop();
        }
    }
}
