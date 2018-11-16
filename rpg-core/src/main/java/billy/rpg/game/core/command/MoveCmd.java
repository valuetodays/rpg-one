package billy.rpg.game.core.command;

import billy.rpg.game.core.character.walkable.npc.NPCWalkableCharacter;
import billy.rpg.game.core.command.processor.CmdProcessor;
import billy.rpg.game.core.constants.WalkableConstant;
import billy.rpg.game.core.container.GameContainer;

import java.util.List;

/**
 * 移动角色
 */
public class MoveCmd extends CmdBase {
    private int npcId;
    private WalkableConstant.PositionEnum faceTo;

    @Override
    public void init() {
        List<String> arguments = super.getArguments();
        npcId = Integer.parseInt(arguments.get(0));
        faceTo = WalkableConstant.PositionEnum.valueOf(arguments.get(1));
    }

    @Override
    public int execute(GameContainer gameContainer, CmdProcessor cmdProcessor) {
        List<NPCWalkableCharacter> npcs = gameContainer.getActiveScriptItem().getNpcs();
        for (NPCWalkableCharacter npcCharacter : npcs) {
            if (npcId == npcCharacter.getNumber()) {
                npcCharacter.setDirection(faceTo);
                int nextPosX = npcCharacter.getNextPosX(gameContainer);
                int nextPosY = npcCharacter.getNextPosY(gameContainer);
                npcCharacter.setPosX(nextPosX);
                npcCharacter.setPosY(nextPosY);
            }
        }
        return 0;
    }

    @Override
    public String getUsage() {
        return null;
    }

    @Override
    public int getArgumentSize() {
        return 2;
    }



}
