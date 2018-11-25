package billy.rpg.game.core.command;

import billy.rpg.game.core.command.processor.CmdProcessor;
import billy.rpg.game.core.container.GameContainer;
import billy.rpg.game.core.screen.BaseScreen;
import billy.rpg.game.core.screen.EmotionScreen;

import java.util.List;

public class EmotionCmd extends CmdBase {
    private int roleIdOrNpcId; // 0:玩家，其它:npc的编号
    private int type;

    @Override
    public void init() {
        List<String> arguments = super.getArguments();
        roleIdOrNpcId = Integer.parseInt(arguments.get(0));
        type = Integer.parseInt(arguments.get(1));
    }

    @Override
    public int execute(GameContainer gameContainer, CmdProcessor cmdProcessor) {
        BaseScreen es = new EmotionScreen(roleIdOrNpcId, type);
        gameContainer.getGameFrame().pushScreen(es);
        return 0;
    }

    @Override
    public String getUsage() {
        return "emotion roleIdOrNpcId emotionType";
    }

    @Override
    public int getArgumentSize() {
        return 2;
    }
}
