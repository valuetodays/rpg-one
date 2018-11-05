package billy.rpg.game.listener.support;

import billy.rpg.game.GameFrame;
import billy.rpg.game.character.HeroCharacter;
import billy.rpg.game.listener.GoodsUseListener;
import billy.rpg.resource.goods.GoodsMetaData;
import billy.rpg.resource.goods.GoodsType;

/**
 * @author lei.liu@datatist.com
 * @since 2018-11-05 17:07:47
 */
public class DefaultGoodsUseListener implements GoodsUseListener {

    @Override
    public void onUse(GoodsMetaData goods) {
        int type = goods.getType();
        if (type == GoodsType.TYPE_MEDICINE.getValue()) {
            // TODO 考虑药品是全体属性
            int heroIndex = GameFrame.getInstance().getGameData().getHeroIndex();
            HeroCharacter heroCharacter = GameFrame.getInstance().getGameData().getHeroList().get(heroIndex);
            // TODO 药物暂只能加hp
            int hp = heroCharacter.getFightable().getRoleMetaData().getHp();
            int hpToAdd = goods.getHp();
            int hpNotExceedMaxHp = Math.min(hp + hpToAdd, heroCharacter.getFightable().getRoleMetaData().getMaxHp());
            heroCharacter.getFightable().getRoleMetaData().setHp(hpNotExceedMaxHp);
        }

        throw new RuntimeException("goods can not be to use: " + goods.getType());
    }
}
