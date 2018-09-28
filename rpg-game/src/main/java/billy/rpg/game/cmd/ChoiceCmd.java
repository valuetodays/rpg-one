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

    @Override
    public void init() {
        List<String> arguments = super.getArguments();
        title = arguments.get(0);
        arguments = arguments.subList(1, arguments.size());
        for (int i = 1; i <= arguments.size()/2; i++) {
            String choice1 = arguments.get(i); // 要把它的左右两个引号去掉
            String label1 = arguments.get(arguments.size()/2 + i);
            this.addItem(choice1.substring(1, choice1.length()-1), label1);
        }
    }

    @Override
    public int execute(CmdProcessor cmdProcessor) {
        ChoiceScreen cs = new ChoiceScreen(cmdProcessor, title, choice, label);
        GameFrame.getInstance().pushScreen(cs);
        cmdProcessor.startPause();
        return 0;
    }

    @Override
    public String getUsage() {
        // TODO
        return null;
    }

    @Override
    public int getArgumentSize() {
        // TODO
        return ARGUMENT_COUNT_ODD;
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
