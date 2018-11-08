package billy.rpg.game.command;

import billy.rpg.game.GameFrame;
import billy.rpg.game.command.processor.CmdProcessor;

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
    public int execute(CmdProcessor cmdProcessor) {
        GameFrame.getInstance().getGameData().equip(heroIndex, goodsNumber);
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
