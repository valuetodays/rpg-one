package billy.rpg.game.core.command;

import billy.rpg.game.core.command.processor.CmdProcessor;
import billy.rpg.game.core.container.GameContainer;

import java.util.List;

/**
 * @author lei.liu@datatist.com
 * @since 2018-11-08 09:44:51
 */
public class EquipCmd extends CmdBase {
    private int heroIndex;
    private int goodsNumber;

    @Override
    public void init() {
        List<String> arguments = super.getArguments();
        heroIndex = Integer.parseInt(arguments.get(0));
        goodsNumber = Integer.parseInt(arguments.get(1));
    }

    @Override
    public int execute(GameContainer gameContainer, CmdProcessor cmdProcessor) {
        gameContainer.getGameData().equip(gameContainer, heroIndex, goodsNumber);
        return 0;
    }

    @Override
    public String getUsage() {
        return "equip heroId goodsNumber";
    }

    @Override
    public int getArgumentSize() {
        return 2;
    }
}
