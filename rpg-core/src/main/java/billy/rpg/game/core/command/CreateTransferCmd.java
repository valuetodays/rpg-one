package billy.rpg.game.core.command;

import billy.rpg.game.core.character.walkable.TransferWalkableCharacter;
import billy.rpg.game.core.command.processor.CmdProcessor;
import billy.rpg.game.core.container.GameContainer;
import billy.rpg.game.core.item.ScriptItem;

import java.util.List;

/**
 * 创建传送门
 *
 * @author lei.liu@datatist.com
 * @since 2018-09-28 16:44:42
 */
public class CreateTransferCmd extends CmdBase {
    private int objId;
    private int x;
    private int y;

    @Override
    public void init() {
        List<String> arguments = super.getArguments();
        objId = Integer.parseInt(arguments.get(0));
        x = Integer.parseInt(arguments.get(1));
        y = Integer.parseInt(arguments.get(2));
    }

    @Override
    public int execute(GameContainer gameContainer, CmdProcessor cmdProcessor) {
        TransferWalkableCharacter transfer = new TransferWalkableCharacter();
        transfer.setNumber(objId);
        transfer.initPos(x, y);
        ScriptItem activeScriptItem = gameContainer.getActiveScriptItem();
        activeScriptItem.getTransfers().add(transfer);
        return 0;
    }

    @Override
    public String getUsage() {
        return "createtransfer objId x y";
    }

    @Override
    public int getArgumentSize() {
        return 3;
    }
}
