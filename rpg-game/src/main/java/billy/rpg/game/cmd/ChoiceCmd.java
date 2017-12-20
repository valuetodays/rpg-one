package billy.rpg.game.cmd;

import billy.rpg.game.GameFrame;
import billy.rpg.game.cmd.executor.CmdProcessor;
import billy.rpg.game.screen.ChoiceScreen;

import java.util.ArrayList;
import java.util.List;

/**
 * 提供选择的选择项目
 *
 * @author liulei@bshf360.com
 * @since 2017-07-24 13:25
 */
public class ChoiceCmd extends CmdBase {
    private String title;
    private List<String> choice = new ArrayList<>();
    private List<String> label = new ArrayList<>();

    public ChoiceCmd(String title) {
        super("choice");
        this.title = title;
    }

    @Override
    public int execute(CmdProcessor cmdProcessor) {
        ChoiceScreen cs = new ChoiceScreen(cmdProcessor, title, choice, label);
        GameFrame.getInstance().pushScreen(cs);
        cmdProcessor.startPause();
        return 0;
    }

    /**
     * 添加一个选项
     * @param choice 选项文本
     * @param label 当该项被选择时跳转到的标签
     */
    public void addItem(String choice, String label) {
        this.choice.add(choice);
        this.label.add(label);
    }


    @Override
    public String toString() {
        return "ChoiceCmd{" +
                "title='" + title + '\'' +
                ", choice=" + choice +
                ", label=" + label +
                "} " + super.toString();
    }
}
