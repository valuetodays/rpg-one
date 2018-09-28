package billy.rpg.game.cmd;

import billy.rpg.game.GameFrame;
import billy.rpg.game.cmd.executor.CmdProcessor;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 随机战斗怪物
 *
 * @author liulei@bshf360.com
 * @since 2017-07-31 17:47
 */
public class MonstersCmd extends CmdBase {
    private List<Integer> monsterIds;


    public List<Integer> getMonsterIds() {
        return monsterIds;
    }

    @Override
    public void init() {
        List<String> arguments = super.getArguments();
        monsterIds = arguments.stream().map(e -> Integer.parseInt(e)).collect(Collectors.toList());
    }

    @Override
    public int execute(CmdProcessor cmdProcessor) {
        GameFrame.getInstance().getGameContainer().getActiveScriptItem().setPredictedMonsterIds(monsterIds);
        return 0;
    }

    @Override
    public String getUsage() {
        return null;
    }

    @Override
    public int getArgumentSize() {
        return ARGUMENT_COUNT_GE_ZERO;
    }
}
