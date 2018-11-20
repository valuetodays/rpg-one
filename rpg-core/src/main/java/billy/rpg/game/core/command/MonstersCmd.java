package billy.rpg.game.core.command;

import billy.rpg.game.core.command.processor.CmdProcessor;
import billy.rpg.game.core.container.GameContainer;

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

    @Override
    public void init() {
        List<String> arguments = super.getArguments();
        monsterIds = arguments.stream().map(e -> Integer.parseInt(e)).collect(Collectors.toList());
    }

    @Override
    public int execute(GameContainer gameContainer, CmdProcessor cmdProcessor) {
        gameContainer.getActiveScriptItem().setPredictedMonsterIds(monsterIds);
        return 0;
    }

    @Override
    public String getUsage() {
        return "monsters MONSTER_ID1 MONSTER_ID2 MONSTER_ID3";
    }

    @Override
    public int getArgumentSize() {
        return ARGUMENT_COUNT_GE_ZERO;
    }
}
