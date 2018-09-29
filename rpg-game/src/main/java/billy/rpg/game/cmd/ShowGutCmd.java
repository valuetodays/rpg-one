package billy.rpg.game.cmd;

import billy.rpg.game.GameFrame;
import billy.rpg.game.cmd.processor.CmdProcessor;
import billy.rpg.game.screen.ShowGutScreen;

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
    public int execute(CmdProcessor cmdProcessor) {
        ShowGutScreen showGutScreen = new ShowGutScreen(content);
        GameFrame.getInstance().pushScreen(showGutScreen);
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
