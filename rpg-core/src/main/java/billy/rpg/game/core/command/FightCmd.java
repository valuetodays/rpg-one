package billy.rpg.game.core.command;

import billy.rpg.game.core.command.processor.CmdProcessor;
import billy.rpg.game.core.container.GameContainer;
import billy.rpg.game.core.screen.battle.BattleScreen;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author lei.liu@datatist.com
 * @since 2018-11-21 11:47:36
 */
public class FightCmd extends CmdBase {
    private String battleImagePath;
    private List<Integer> monsterIds;

    @Override
    public void init() {
        List<String> arguments = super.getArguments();
        battleImagePath = arguments.get(0);

        monsterIds = arguments.subList(1, arguments.size()).stream().map(Integer::parseInt).collect(Collectors.toList());
    }

    @Override
    public int execute(GameContainer gameContainer, CmdProcessor cmdProcessor) {
        int[] monsterArr = new int[monsterIds.size()];
        for (int i = 0; i < monsterIds.size(); i++) {
            monsterArr[i] = monsterIds.get(i);
        }

        gameContainer.getGameFrame().pushScreen(new BattleScreen(gameContainer, monsterArr)); // 进入战斗界面
        return 0;
    }

    @Override
    public String getUsage() {
        return null;
    }

    @Override
    public int getArgumentSize() {
        return ARGUMENT_COUNT_GT_ZERO;
    }
}
