package billy.rpg.game.core.command;

import billy.rpg.game.core.character.walkable.npc.NPCWalkableCharacter;
import billy.rpg.game.core.command.processor.CmdProcessor;
import billy.rpg.game.core.container.GameContainer;

import java.util.List;

/**
 * 删除npc
 * @author liulei@bshf360.com
 * @since 2017-11-28 15:42
 */
public class DeleteNpcCmd extends CmdBase {
    private int npcid;

    @Override
    public void init() {
        List<String> arguments = super.getArguments();
        npcid = Integer.parseInt(arguments.get(0));
    }

    @Override
    public int execute(GameContainer gameContainer, CmdProcessor cmdProcessor) {
        List<NPCWalkableCharacter> npcs = gameContainer.getActiveScriptItem().getNpcs();
        NPCWalkableCharacter npcCharacter2Del = null;
        for (NPCWalkableCharacter npcCharacter : npcs) {
            if (npcid == npcCharacter.getNumber()) {
                npcCharacter2Del = npcCharacter;
            }
        }
        npcs.remove(npcCharacter2Del);
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

    public int getNpcid() {
        return npcid;
    }
}
