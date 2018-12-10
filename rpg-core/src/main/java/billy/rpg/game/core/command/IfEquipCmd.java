package billy.rpg.game.core.command;

import billy.rpg.game.core.character.PlayerCharacter;
import billy.rpg.game.core.command.processor.CmdProcessor;
import billy.rpg.game.core.command.processor.support.DefaultCmdProcessor;
import billy.rpg.game.core.container.GameContainer;
import billy.rpg.game.core.script.LabelBean;
import billy.rpg.resource.goods.GoodsMetaData;

import java.util.Arrays;
import java.util.List;

/**
 * @author lei.liu@datatist.com
 * @since 2018-11-21 15:38:51
 */
public class IfEquipCmd extends CmdBase {
    private int roleId;
    private int goods;
    private String labelGoto;

    @Override
    public void init() {
        List<String> arguments = super.getArguments();
        roleId = Integer.parseInt(arguments.get(0));
        goods = Integer.parseInt(arguments.get(1));
        labelGoto = arguments.get(2);
    }

    @Override
    public int execute(GameContainer gameContainer, CmdProcessor cmdProcessor) {
        GoodsMetaData goodsMetaData = gameContainer.getGoodsMetaDataOf(goods);
        if (goodsMetaData == null) {
            throw new RuntimeException("error goods: " + goods);
        }
        PlayerCharacter heroCharacterToCheckEquip = null;
        List<PlayerCharacter> heroList = gameContainer.getGameData().getHeroList(gameContainer);
        for (PlayerCharacter heroCharacter : heroList) {
            if (heroCharacter.getRoleMetaData().getNumber() == roleId) {
                heroCharacterToCheckEquip = heroCharacter;
            }
        }
        if (heroCharacterToCheckEquip == null) {
            throw new RuntimeException("error when hasEquip: no specified roleId " + roleId);
        }

        int clothesNumber = heroCharacterToCheckEquip.getEquipables().getClothes().getEquip().getGoods().getNumber();
        int weaponNumber = heroCharacterToCheckEquip.getEquipables().getWeapon().getEquip().getGoods().getNumber();
        int shoeNumber = heroCharacterToCheckEquip.getEquipables().getShoe().getEquip().getGoods().getNumber();

        List<Integer> equipGoodsIdList = Arrays.asList(clothesNumber, weaponNumber, shoeNumber);
        if (!equipGoodsIdList.contains(goods)) {
            LabelBean label = gameContainer.getLabelByTitle(labelGoto);
            cmdProcessor.setInnerCmdProcessor(new DefaultCmdProcessor(label.getCmds()));
        }

        return 0;
    }

    @Override
    public String getUsage() {
        return "ifequip ROLE_ID GOODS_ID";
    }

    @Override
    public int getArgumentSize() {
        return 3;
    }
}
