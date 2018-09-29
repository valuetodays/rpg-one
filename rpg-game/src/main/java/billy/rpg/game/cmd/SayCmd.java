package billy.rpg.game.cmd;

import billy.rpg.game.GameFrame;
import billy.rpg.game.cmd.processor.CmdProcessor;
import billy.rpg.game.container.GameContainer;
import billy.rpg.game.screen.DialogScreen;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

/**
 * 显示对话
 * @author <a href="http://blog.sina.com.cn/valuetodays">liulei-home</a>
 * @since 2016-05-10 22:38
 */
public class SayCmd extends CmdBase {
    private int headNumber;
    private String talker;
    private PositionEnum position;
    private String text;


    public String getText() {
        return text;
    }

    public String getTalker() {
        return talker;
    }

    @Override
    public void init() {
        List<String> arguments = super.getArguments();
        headNumber = Integer.parseInt(arguments.get(0));
        talker = arguments.get(1);
        position = PositionEnum.valueOf(arguments.get(2));
        text = arguments.get(3);
    }

    public int getHeadNumber() {
        return headNumber;
    }

    @Override
    public int execute(CmdProcessor cmdProcessor) {
        Image headImage = GameContainer.getInstance().getHeadImageItemOf(getHeadNumber());
        DialogScreen ms = new DialogScreen(cmdProcessor, headImage, getTalker(), getPosition(), getText());
        GameFrame.getInstance().pushScreen(ms);
        cmdProcessor.startPause();
        return 0;
    }

    @Override
    public String getUsage() {
        return "say headImg-id talker location[LEFT, RIGHT, NONE] text";
    }

    @Override
    public int getArgumentSize() {
        return 4;
    }

    @Override
    public String toString() {
        return "SayCmd{" +
                "headNumber=" + headNumber +
                ", position=" + position +
                ", text='" + text + '\'' +
                "} " + super.toString();
    }

    public PositionEnum getPosition() {
        return position;
    }

    public static enum PositionEnum {
        LEFT("LEFT"),
        RIGHT("RIGHT"),
        NONE("NONE");

        private String info;

        PositionEnum(String info) {
            this.info = info;
        }

        public static boolean isLegal(PositionEnum position) {
            return Arrays.asList(PositionEnum.values()).contains(position);
        }

        public static void assertLegal(PositionEnum position, String errorMessage) {
            if(!isLegal(position)) {
                throw new RuntimeException(errorMessage);
            }
        }
    }
}
