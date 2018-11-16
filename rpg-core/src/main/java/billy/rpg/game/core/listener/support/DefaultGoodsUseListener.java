package billy.rpg.game.core.listener.support;

import billy.rpg.game.core.character.HeroCharacter;
import billy.rpg.game.core.container.GameContainer;
import billy.rpg.game.core.listener.GoodsUseListener;
import billy.rpg.resource.goods.GoodsMetaData;
import billy.rpg.resource.goods.GoodsType;

/**
 * @author lei.liu@datatist.com
 * @since 2018-11-05 17:07:47
 */
public class DefaultGoodsUseListener implements GoodsUseListener {

    @Override
    public void onUse(GameContainer gameContainer, GoodsMetaData goods) {
        int type = goods.getType();

        if (type == GoodsType.TYPE_MEDICINE.getValue()) {
            // TODO 考虑药品是全体属性
            int heroIndex = gameContainer.getGameData().getHeroIndex();
            HeroCharacter heroCharacter = gameContainer.getGameData().getHeroList(gameContainer).get(heroIndex);
            // TODO 药物暂只能加hp
            int hp = heroCharacter.getRoleMetaData().getHp();
            int hpToAdd = goods.getHp();
            int hpNotExceedMaxHp = Math.min(hp + hpToAdd, heroCharacter.getRoleMetaData().getMaxHp());
            heroCharacter.getRoleMetaData().setHp(hpNotExceedMaxHp);
        } else {
            throw new RuntimeException("goods can not be to use: " + goods.getType());
        }
    }
}
