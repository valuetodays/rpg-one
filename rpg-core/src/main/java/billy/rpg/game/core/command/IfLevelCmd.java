package billy.rpg.game.core.command;

import billy.rpg.game.core.character.HeroCharacter;
import billy.rpg.game.core.command.processor.CmdProcessor;
import billy.rpg.game.core.command.processor.support.DefaultCmdProcessor;
import billy.rpg.game.core.container.GameContainer;
import billy.rpg.game.core.script.LabelBean;

import java.util.List;

/**
 * @author lei.liu@datatist.com
 * @since 2018-11-22 13:58:54
 */
public class IfLevelCmd extends CmdBase {
    private int roleId;
    private int predictedLevel;
    private String labelGoto;

    @Override
    public void init() {
        List<String> arguments = super.getArguments();
        roleId = Integer.parseInt(arguments.get(0));
        predictedLevel = Integer.parseInt(arguments.get(1));
        labelGoto = arguments.get(2);
    }

    @Override
    public int execute(GameContainer gameContainer, CmdProcessor cmdProcessor) {
        HeroCharacter heroCharacter = gameContainer.getGameData().getHeroList(gameContainer).get(roleId);
        int level = heroCharacter.getRoleMetaData().getLevel();

        if (level < predictedLevel) {
            LabelBean label = gameContainer.getLabelByTitle(labelGoto);
            cmdProcessor.setInnerCmdProcessor(new DefaultCmdProcessor(label.getCmds()));
        }
        return 0;
    }

    @Override
    public String getUsage() {
        return "iflevel 20 NOT_LEVEL_20";
    }

    @Override
    public int getArgumentSize() {
        return 3;
    }
}
