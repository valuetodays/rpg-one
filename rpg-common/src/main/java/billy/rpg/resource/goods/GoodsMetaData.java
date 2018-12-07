package billy.rpg.resource.goods;

/**
 * TODO 药品类物品添加是否全体效果？
 *
 * @author liulei@bshf360.com
 * @since 2017-09-04 17:36
 */
public class GoodsMetaData {
    public static final int EMPTY_GOODS_INDEX = 0;

    public static final int RANGE_SINGLE = 1;
    public static final int RANGE_ALL = 2;

    private int number;
    private int type;
    private String name;
    private int buy;
    private int sell;
    private int imageId;
    private int event;
    private int animationId;
    private int hp;
    private int mp;
    private int attack;
    private int defend;
    private int speed;
    private String desc;
    private int range = 1; // 效果类型，（对武器来说，1=单攻，2=群攻, 对药物来说，1=单服，2=群服）

    // 如下字段不在物品编辑器中使用
    private int count;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
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

    public int getBuy() {
        return buy;
    }

    public void setBuy(int buy) {
        this.buy = buy;
    }

    public int getSell() {
        return sell;
    }

    public void setSell(int sell) {
        this.sell = sell;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public int getEvent() {
        return event;
    }

    public void setEvent(int event) {
        this.event = event;
    }

    public int getAnimationId() {
        return animationId;
    }

    public void setAnimationId(int animationId) {
        this.animationId = animationId;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getMp() {
        return mp;
    }

    public void setMp(int mp) {
        this.mp = mp;
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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    /**
     * 是否是一个空物品（占位物品）
     */
    public boolean isEmptyGoods() {
        return number == GoodsMetaData.EMPTY_GOODS_INDEX;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }
}
