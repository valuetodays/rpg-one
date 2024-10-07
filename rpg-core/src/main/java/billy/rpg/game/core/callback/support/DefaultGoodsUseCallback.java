package billy.rpg.game.core.callback.support;

import billy.rpg.game.core.callback.GoodsUseCallback;
import billy.rpg.game.core.character.PlayerCharacter;
import billy.rpg.game.core.container.GameContainer;
import billy.rpg.resource.goods.GoodsMetaData;
import billy.rpg.resource.goods.GoodsType;

/**
 * @author lei.liu
 * @since 2018-11-05 17:07:47
 */
public class DefaultGoodsUseCallback implements GoodsUseCallback {

    @Override
    public void onUse(GameContainer gameContainer, GoodsMetaData goods) {
        int type = goods.getType();

        if (type == GoodsType.TYPE_MEDICINE.getValue()) {
            // TODO 考虑药品是全体属性
            int heroIndex = gameContainer.getGameData().getHeroIndex();
            PlayerCharacter heroCharacter = gameContainer.getGameData().getHeroList(gameContainer).get(heroIndex);
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
