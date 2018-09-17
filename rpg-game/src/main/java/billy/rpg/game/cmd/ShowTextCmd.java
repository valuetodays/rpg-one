package billy.rpg.game.cmd;

import billy.rpg.game.GameFrame;
import billy.rpg.game.character.PositionEnum;
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
    private static final String CMD_NAME = "showtext";
    private int headNumber;
    private String name;
    private PositionEnum position;
    private String text;

    public ShowTextCmd(int number, String name, PositionEnum position, String text) {
        super(CMD_NAME);
        headNumber = number;
        this.name = name;
        this.position = position;
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public int getHeadNumber() {
        return headNumber;
    }

    public void setHeadNumber(int headNumber) {
        this.headNumber = headNumber;
    }

    @Override
    public int execute(CmdProcessor cmdProcessor) {
        Image headImage = GameContainer.getInstance().getHeadImageItemOf(getHeadNumber());
        DialogScreen ms = new DialogScreen(cmdProcessor, headImage, getName(), getPosition(), getText());
        GameFrame.getInstance().pushScreen(ms);
        cmdProcessor.startPause();
        return 0;
    }

    @Override
    public String toString() {
        return "ShowTextCmd{" +
                "headNumber=" + headNumber +
                ", position=" + position +
                ", text='" + text + '\'' +
                "} " + super.toString();
    }

    public PositionEnum getPosition() {
        return position;
    }

    public void setPosition(PositionEnum position) {
        this.position = position;
    }

    public static CmdBase ofNew(String script, int lineNumber, String cmdarg) {
        try {
            cmdarg = cmdarg.trim();
            int i = cmdarg.indexOf(CHAR_SPACE);
            String headNumberText = cmdarg.substring(0, i + CHAR_SPACE.length());
            logger.debug(headNumberText);
            int headNumber = Integer.parseInt(headNumberText.trim());
            int i1 = cmdarg.indexOf("'", headNumberText.length());
            int i2 = cmdarg.indexOf("'", i1 + "'".length());
            String name = cmdarg.substring(i1 + "'".length(), i2);
            logger.debug(name);
            int i3 = cmdarg.indexOf(CHAR_SPACE, i2 + 1 + "'".length());
            String position = cmdarg.substring(i2 + 1 + "'".length(), i3);
            PositionEnum positionEnum = PositionEnum.valueOf(position);
            logger.debug(positionEnum);
            String text = cmdarg.substring(i3);
            // assert first and last char is "'"
            text = text.substring(2, text.length() - 1);
            logger.debug(text);

            return new ShowTextCmd(headNumber, name, positionEnum, text);
        } catch (Exception e) {
            logger.error("error in "+script+"[Line:"+lineNumber+", "+e.getMessage()+"]");
            return EmptyCmd.EMPTY_CMD;
        }
    }
}
