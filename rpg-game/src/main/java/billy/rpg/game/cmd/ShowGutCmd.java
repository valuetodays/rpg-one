package billy.rpg.game.cmd;

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

    public String getContent() {
        return content;
    }
}
