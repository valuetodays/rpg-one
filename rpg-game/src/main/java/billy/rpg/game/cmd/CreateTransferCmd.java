package billy.rpg.game.cmd;

import billy.rpg.game.character.TransferCharacter;
import billy.rpg.game.cmd.executor.CmdProcessor;
import billy.rpg.game.container.GameContainer;
import billy.rpg.game.resource.item.ScriptItem;

import java.util.List;

/**
 * 创建传送门
 *
 * @author lei.liu@datatist.com
 * @since 2018-09-28 16:44:42
 */
public class CreateTransferCmd extends CmdBase {
    private int x;
    private int y;

    @Override
    public void init() {
        List<String> arguments = super.getArguments();
        x = Integer.parseInt(arguments.get(0));
        y = Integer.parseInt(arguments.get(1));
    }

    @Override
    public int execute(CmdProcessor cmdProcessor) {
        TransferCharacter transfer = new TransferCharacter();
        transfer.initPos(x, y);
        ScriptItem activeScriptItem = GameContainer.getInstance().getActiveScriptItem();
        activeScriptItem.getTransfers().add(transfer);
        return 0;
    }

    @Override
    public String getUsage() {
        return "createtransfer x y";
    }

    @Override
    public int getArgumentSize() {
        return 2;
    }
}
