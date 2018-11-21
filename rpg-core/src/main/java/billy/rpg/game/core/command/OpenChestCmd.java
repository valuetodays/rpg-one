package billy.rpg.game.core.command;

import billy.rpg.game.core.character.walkable.BoxWalkableCharacter;
import billy.rpg.game.core.command.processor.CmdProcessor;
import billy.rpg.game.core.container.GameContainer;
import billy.rpg.resource.box.BoxImageLoader;

import java.util.List;
import java.util.Optional;

public class OpenChestCmd extends CmdBase {
    private int id; // id in a script

    @Override
    public void init() {
        List<String> arguments = super.getArguments();
        id = Integer.parseInt(arguments.get(0));
    }

    @Override
    public int execute(GameContainer gameContainer, CmdProcessor cmdProcessor) {
        List<BoxWalkableCharacter> boxes = gameContainer.getActiveScriptItem().getBoxes();
        Optional<BoxWalkableCharacter> first = boxes.stream().filter(e -> e.getNumber() == id).findFirst();
        if (!first.isPresent()) {
            throw new RuntimeException("宝箱不存在");
        }
        BoxWalkableCharacter box = first.get();
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
        return 1;
    }
}
