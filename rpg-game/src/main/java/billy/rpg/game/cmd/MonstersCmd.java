package billy.rpg.game.cmd;

import java.util.List;

/**
 * @author liulei@bshf360.com
 * @since 2017-07-31 17:47
 */
public class MonstersCmd extends CmdBase {
    private List<Integer> monsterIds;

    public MonstersCmd(List monsterIds) {
        super("monsters");
        this.monsterIds = monsterIds;
    }

    public List<Integer> getMonsterIds() {
        return monsterIds;
    }
}
