package billy.rpg.resource.role;

import java.awt.image.BufferedImage;

/**
 * <ol>
 *     <li>4 bytes RoleEditorConstant.ROLE_MAGIC</li>
 *     <li>4 bytes version</li>
 *     <li>4 bytes type</li>
 *     <li>4 bytes number</li>
 *     <li>4 bytes role-name-length</li>
 *     <li>n bytes role-name</li>
 *     <li>4 bytes image-length</li>
 *     <li>N bytes image(reversed)</li>
 *     <li>4 bytes hp</li>
 *     <li>4 bytes maxHp</li>
 *     <li>4 bytes mp</li>
 *     <li>4 bytes maxMp</li>
 *     <li>4 bytes speed</li>
 *     <li>4 bytes attack</li>
 *     <li>4 bytes defend</li>
 *     <li>4 bytes exp</li>
 *     <li>4 bytes money</li>
 *     <li>4 bytes leveChain</li> TODO
 * </ol>
 * @author liulei@bshf360.com
 * @since 2017-07-12 10:57
 */
public class RoleMetaData implements Cloneable { // TODO 添加级别
    public static final int TYPE_PLAYER = 1;
    public static final int TYPE_NPC = 2;
    public static final int TYPE_MONSTER = 3;
    public static final int TYPE_SCENE = 4;

    private int type;
    private int number;
    private int level = 1; // TODO 等级，默认开始时都是1
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
    private int levelChain = 1; // TODO 先固定成1


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
