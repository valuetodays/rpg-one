package billy.rpg.game.cmd;

import billy.rpg.game.GameFrame;
import billy.rpg.game.cmd.executor.CmdProcessor;
import billy.rpg.game.container.GameContainer;
import billy.rpg.game.screen.DialogScreen;

import java.awt.*;

/**
 * 显示对话
 * @author <a href="http://blog.sina.com.cn/valuetodays">liulei-home</a>
 * @since 2016-05-10 22:38
 */
public class ShowTextCmd extends CmdBase {
    private int headNumber;
    private int location;
    private String text;

    public ShowTextCmd(int number, int location, String text) {
        super("showtext");
        headNumber = number;
        this.location = location;
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getHeadNumber() {
        return headNumber;
    }

    public void setHeadNumber(int headNumber) {
        this.headNumber = headNumber;
    }

    @Override
    public int execute(CmdProcessor cmdProcessor) {
        Image headImage = GameContainer.getInstance().getHeadImageItemOf(headNumber);
        DialogScreen ms = new DialogScreen(cmdProcessor, headImage, getLocation(), text);
        GameFrame.getInstance().pushScreen(ms);
        cmdProcessor.startPause();
        return 0;
    }

    @Override
    public String toString() {
        return "ShowTextCmd{" +
                "headNumber=" + headNumber +
                ", location=" + location +
                ", text='" + text + '\'' +
                "} " + super.toString();
    }

    public int getLocation() {
        return location;
    }

    public void setLocation(int location) {
        this.location = location;
    }
}
