package billy.rpg.game.core.command;

import billy.rpg.common.util.AssetsUtil;
import billy.rpg.game.core.character.walkable.FlickerObjectWalkableCharacter;
import billy.rpg.game.core.command.processor.CmdProcessor;
import billy.rpg.game.core.container.GameContainer;
import billy.rpg.game.core.item.ScriptItem;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class CreateSceneObjectCmd extends CmdBase {
    private int id; // no use, reversed for purpose
    private int x; // pos x
    private int y; // pos y
    private String imageName;

    @Override
    public void init() {
        List<String> arguments = super.getArguments();
        id = Integer.parseInt(arguments.get(0));
        x = Integer.parseInt(arguments.get(1));
        y = Integer.parseInt(arguments.get(2));
        imageName = arguments.get(3);
    }

    @Override
    public int execute(GameContainer gameContainer, CmdProcessor cmdProcessor) {
        String imagePath = AssetsUtil.getResourcePath("/assets/Images/scene/" + imageName);
        File file = new File(imagePath);
        try {
            BufferedImage image = ImageIO.read(file);
            ScriptItem activeScriptItem = gameContainer.getActiveScriptItem();
            List<FlickerObjectWalkableCharacter> sceneObjects = activeScriptItem.getSceneObjects();
            FlickerObjectWalkableCharacter e = new FlickerObjectWalkableCharacter();
            e.setNumber(id);
            e.setPosX(x);
            e.setPosY(y);
            e.setImage(image);
            sceneObjects.add(e);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return 0;
    }

    @Override
    public String getUsage() {
        return getClass().getSimpleName() + " id x y IMAGE_PATH";
    }

    @Override
    public int getArgumentSize() {
        return 4;
    }
}
