package billy.rpg.game.core.fishing;

import billy.rpg.game.core.constants.GameConstant;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author liulei-home
 * @since 2018-06-10 09:07
 */
public class Fishing {
    private static final String MSG = "你钓到了一条%s，卖给渔夫可得%d元。";
    private List<FishType> fishTypeList;
    private FishingMessageReceiver fishingMessageReceiver = new FishingMessageReceiver();

    /**
     * [1, 100]
     */
    private int luck;

    public void doFish() {
        before();
        doFish0();
        after();
    }

    private void after() {

    }

    private void doFish0() {
        int randomIndex = GameConstant.random.nextInt(fishTypeList.size());
        FishType fishType = fishTypeList.get(randomIndex);
        if (this.luck > fishType.getDifficultFactor()) {
            System.out.println(String.format(MSG, fishType.getName(), fishType.getPrice()));
            fishingMessageReceiver.doReceive(fishType);
        } else {
            System.out.println("你的钓鱼水平还不够");
        }
    }

    private void before() {
        initFishType();
    }

    private void initFishType() {
        this.fishTypeList = Collections.unmodifiableList(Arrays.asList(FishType.values()));
        this.luck = 50; // 应取决于主角的'幸运'属性
    }

}
