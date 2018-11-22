package billy.rpg.game.core.command;

import billy.rpg.game.core.command.processor.CmdProcessor;
import billy.rpg.game.core.command.processor.support.DefaultCmdProcessor;
import billy.rpg.game.core.container.GameContainer;
import billy.rpg.game.core.script.LabelBean;

import java.util.List;

/**
 * @author lei.liu@datatist.com
 * @since 2018-11-21 15:24:53
 */
public class IfMoneyCmd extends CmdBase {
    private int predictedMoney;
    private String labelGoto;

    @Override
    public void init() {
        List<String> arguments = super.getArguments();
        predictedMoney = Integer.parseInt(arguments.get(0));
        labelGoto = arguments.get(1);
    }

    @Override
    public int execute(GameContainer gameContainer, CmdProcessor cmdProcessor) {
        int money = gameContainer.getGameData().getMoney();
        if (money < predictedMoney) {
            LabelBean label = gameContainer.getLabelByTitle(labelGoto);
            cmdProcessor.setInnerCmdProcessor(new DefaultCmdProcessor(label.getCmds()));
        }
        return 0;
    }

    @Override
    public String getUsage() {
        return "ifmoney number label_when_not";
    }

    @Override
    public int getArgumentSize() {
        return 2;
    }
}
