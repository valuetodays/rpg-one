package billy.rpg.game.core.command;

import billy.rpg.game.core.character.walkable.BoxWalkableCharacter;
import billy.rpg.game.core.command.processor.CmdProcessor;
import billy.rpg.game.core.container.GameContainer;
import billy.rpg.resource.box.BoxImageLoader;
import org.apache.commons.lang.BooleanUtils;

import java.util.List;

public class CreateChestCmd extends CmdBase {
    private int id; // id in a script
    private int x; // pos x
    private int y; // pos y
    private boolean visible;

    @Override
    public void init() {
        List<String> arguments = super.getArguments();
        id = Integer.parseInt(arguments.get(0));
        x = Integer.parseInt(arguments.get(1));
        y = Integer.parseInt(arguments.get(2));
        visible = BooleanUtils.toBoolean(arguments.get(3));
    }

    @Override
    public int execute(GameContainer gameContainer, CmdProcessor cmdProcessor) {
        List<BoxWalkableCharacter> boxes = gameContainer.getActiveScriptItem().getBoxes();
        long count = boxes.stream().filter(e -> e.getNumber() == id).count();
        if (count > 0) {
            throw new RuntimeException("宝箱被重复创建");
        }

        BoxWalkableCharacter box = new BoxWalkableCharacter();
        box.setNumber(id);
        box.initPos(x, y);
        box.setVisible(visible);
        box.setTileNum(BoxImageLoader.getOpenImageNum());

        gameContainer.getActiveScriptItem().getBoxes().add(box);
        return 0;
    }

    @Override
    public String getUsage() {
        return null;
    }

    @Override
    public int getArgumentSize() {
        return 0;
    }
}
