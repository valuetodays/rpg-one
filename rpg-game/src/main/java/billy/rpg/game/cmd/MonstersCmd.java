package billy.rpg.game.cmd;

import billy.rpg.game.GameFrame;
import billy.rpg.game.cmd.executor.CmdProcessor;

import java.util.List;

/**
 * @author liulei@bshf360.com
 * @since 2017-07-31 17:47
 */
public class MonstersCmd extends CmdBase {
    private List<Integer> monsterIds;

    public MonstersCmd(List<Integer> monsterIds) {
        super("monsters");
        this.monsterIds = monsterIds;
    }

    public List<Integer> getMonsterIds() {
        return monsterIds;
    }

    @Override
    public int execute(CmdProcessor cmdProcessor) {
        GameFrame.getInstance().getGameContainer().getActiveScriptItem().setPredictedMonsterIds(monsterIds);
        return 0;
    }
}
