package billy.rpg.game.core.command;

import billy.rpg.game.core.command.processor.CmdProcessor;
import billy.rpg.game.core.container.GameContainer;
import billy.rpg.resource.role.RoleMetaData;

import java.util.List;

/**
 * @author lei.liu
 * @since 2018-11-29 10:14:12
 */
public class LevelUpCmd extends CmdBase {
    private int roleId; // 玩家编号

    @Override
    public void init() {
        List<String> arguments = super.getArguments();
        roleId = Integer.parseInt(arguments.get(0));
    }

    @Override
    public int execute(GameContainer gameContainer, CmdProcessor cmdProcessor) {
        RoleMetaData heroRole = gameContainer.getHeroRoleOf(roleId);
        gameContainer.getGameData().levelUp(gameContainer, heroRole, true);
        return 0;
    }

    @Override
    public String getUsage() {
        return "levelup ROLE_ID";
    }

    @Override
    public int getArgumentSize() {
        return 1;
    }
}
