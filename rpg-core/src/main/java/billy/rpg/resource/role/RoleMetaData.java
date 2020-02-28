package billy.rpg.resource.role;

import billy.rpg.common.constant.ToolsConstant;
import billy.rpg.common.util.AssetsUtil;
import billy.rpg.resource.sprite.HeroSprite;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * <ol>
 *     <li>4 bytes {@link ToolsConstant#MAGIC_ROL}</li>
 *     <li>4 bytes number</li>
 *     <li>4 bytes type</li>
 *     <li>4 bytes role-name-length</li>
 *     <li>n bytes role-name</li>
 *     <li>4 bytes image-length</li>
 *     <li>N bytes image(reversed)</li>
 *     <li>4 bytes hp</li>
 *     <li>4 bytes mp</li>
 *     <li>4 bytes speed</li>
 *     <li>4 bytes attack</li>
 *     <li>4 bytes defend</li>
 *     <li>4 bytes exp</li>
 *     <li>4 bytes money</li>
 *     <li>4 bytes skillIds length</li>
 *     <li>n bytes skiilIds</li>
 *     <li>4 bytes leveChain</li> TODO
 * </ol>
 * @author liulei@bshf360.com
 * @since 2017-07-12 10:57
 */
public class RoleMetaData implements Cloneable { // TODO 添加级别
    public static final int TYPE_PLAYER = 1;
    public static final int TYPE_NPC = 2;
    public static final int TYPE_ENEMY = 3;
    public static final int TYPE_SCENE = 4;

    private int number;
    private int type;
    private int level = 1; // TODO 等级，默认开始时都是1，不过有玩家是中途入场的
    private String name;
    private BufferedImage image;
    private int hp;
    private int maxHp;
    private int mp;
    private int maxMp;
    private int speed;
    private int attack;
    private int defend;
    private int exp;
    private int money;
    private String skillIds;
    private int levelChain = 1; // TODO 先固定成1
    private HeroSprite sprite;


    public RoleMetaData() {
        sprite = new HeroSprite();
        List<HeroSprite.Key> keyList = new ArrayList<>();
        int x = 0;
        int y = 0;
        BufferedImage image = AssetsUtil.getResourceAsImage("/assets/sprite/Heal5.png");
        int show = 10;
        int nShow = 5;
        for (int i = 0; i < 13; i++) {
            int ix = i % 5;
            int iy = i / 5;
            BufferedImage iimage = image.getSubimage(ix * 192, iy * 192, 192, 192);
            HeroSprite.Key key = new HeroSprite.Key(x, y, iimage, show, nShow);
            keyList.add(key);
        }
        sprite.setKeyList(keyList);
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
    }

    public int getMp() {
        return mp;
    }

    public void setMp(int mp) {
        this.mp = mp;
    }

    public int getMaxMp() {
        return maxMp;
    }

    public void setMaxMp(int maxMp) {
        this.maxMp = maxMp;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getDefend() {
        return defend;
    }

    public void setDefend(int defend) {
        this.defend = defend;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getLevelChain() {
        return levelChain;
    }

    public void setLevelChain(int levelChain) {
        this.levelChain = levelChain;
    }

    public String getSkillIds() {
        return skillIds;
    }

    public void setSkillIds(String skillIds) {
        this.skillIds = skillIds;
    }

    public HeroSprite getSprite() {
        return sprite;
    }

    public void setSprite(HeroSprite sprite) {
        this.sprite = sprite;
    }

    @Override
    public RoleMetaData clone() {
        RoleMetaData clone = null;
        try {
            clone = (RoleMetaData) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return clone;
    }
}
