package billy.rpg.game.cmd;

import billy.rpg.game.GameFrame;
import billy.rpg.game.cmd.executor.CmdProcessor;
import billy.rpg.game.screen.ShowGutScreen;

/**
 * show gut
 *
 * @author liulei@bshf360.com
 * @since 2017-11-28 10:53
 */
public class ShowGutCmd extends CmdBase {
    private String content;

    public ShowGutCmd(String content) {
        super("shwowgut");
        this.content = content;
    }

    @Override
    public int execute(CmdProcessor cmdProcessor) {
        ShowGutScreen showGutScreen = new ShowGutScreen(content);
        GameFrame.getInstance().pushScreen(showGutScreen);
        return 0;
    }

    public String getContent() {
        return content;
    }
}
