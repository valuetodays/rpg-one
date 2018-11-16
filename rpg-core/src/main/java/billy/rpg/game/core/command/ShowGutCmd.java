package billy.rpg.game.core.command;

import billy.rpg.game.core.command.processor.CmdProcessor;
import billy.rpg.game.core.container.GameContainer;
import billy.rpg.game.core.screen.ShowGutScreen;

import java.util.List;

/**
 * show gut
 *
 * @author liulei@bshf360.com
 * @since 2017-11-28 10:53
 */
public class ShowGutCmd extends CmdBase {
    private String content;

    @Override
    public void init() {
        List<String> arguments = super.getArguments();
        content = arguments.get(0);
    }

    @Override
    public int execute(GameContainer gameContainer, CmdProcessor cmdProcessor) {
        ShowGutScreen showGutScreen = new ShowGutScreen(content);
        gameContainer.getGameFrame().pushScreen(showGutScreen);
        return 0;
    }

    @Override
    public String getUsage() {
        return null;
    }

    @Override
    public int getArgumentSize() {
        return 1;
    }

    public String getContent() {
        return content;
    }
}
