package billy.rpg.game.core.command;

import billy.rpg.game.core.command.processor.CmdProcessor;
import billy.rpg.game.core.constants.GameConstant;
import billy.rpg.game.core.container.GameContainer;
import billy.rpg.game.core.script.variable.VariableDeterminer;

import java.util.List;

/**
 * 生成一个[begin, end]之间的数，并把它保存到var变量中
 *
 * @author lei.liu
 * @since 2018-12-10 15:18:19
 */
public class RandCmd extends CmdBase {
    private static final int MAX = 1000;
    private String var;
    private int randValue;
    private int begin;
    private int end;

    @Override
    public void init() {
        List<String> arguments = super.getArguments();
        var = arguments.get(0);
        begin = Integer.parseInt(arguments.get(1));
        end = Integer.parseInt(arguments.get(2));

        randValue = (GameConstant.random.nextInt(MAX) % (end - begin + 1)) + begin;
    }

    @Override
    public int execute(GameContainer gameContainer, CmdProcessor cmdProcessor) {
        if (begin >= MAX) {
            throw new RuntimeException("the begin is incorrect, please use number less than " + MAX);
        }
        if (end <= begin) {
            throw new RuntimeException("the begin and end is incorrect.");
        }
        VariableDeterminer.getInstance().set(var, getRandValue());
        return 0;
    }

    public int getRandValue() {
        return randValue;
    }

    @Override
    public String getUsage() {
        return "rand var 10 20";
    }

    @Override
    public int getArgumentSize() {
        return 3;
    }
}
