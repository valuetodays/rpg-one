package billy.rpg.resource.goods;

/**
 * @author liulei-home
 * @since 2018-10-03 15:01
 */
public enum GoodsType {
    TYPE_MEDICINE(0, "药物类"),
    TYPE_ELIXIR(1, "仙丹类"),
    TYPE_WEAPON(2, "武器类") {
        @Override
        public void checkLegal(GoodsMetaData goods) {
            super.checkLegal(goods);
            if (goods.getNumber() != GoodsMetaData.EMPTY_GOODS_INDEX && goods.getType() != this.getValue()) {
                throw new RuntimeException("错误的武器");
            }
        }
    },
    TYPE_CLOTHES(3, "衣服类"),
    TYPE_SHOE(4, "鞋子类");

    private final int value;
    private final String desc;

    private GoodsType(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public int getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }

    public void checkLegal(GoodsMetaData goods) {
        if (goods.getNumber() == GoodsMetaData.EMPTY_GOODS_INDEX) {
        }
    }
}
