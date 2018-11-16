package billy.rpg.game.core.fishing;

/**
 * @author liulei-home
 * @since 2018-06-10 09:11
 */
public enum  FishType {
    FISH_GRASS_CARP("草鱼", "balabala", 300, 20),
    FISH_CRUCIAN("鲫鱼", "balabala", 500, 30),
    FISH_CARP("鲤鱼", "balabala", 800, 50);

    private String name;
    private String desc;
    private int price;
    private int difficultFactor;

    FishType(String name, String desc, int price, int difficultFactor) {
        this.name = name;
        this.desc = desc;
        this.price = price;
        this.difficultFactor = difficultFactor;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public int getPrice() {
        return price;
    }

    public int getDifficultFactor() {
        return difficultFactor;
    }
}
