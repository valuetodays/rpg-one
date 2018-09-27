package billy.rpg.game.cmd;

import billy.rpg.game.GameFrame;
import billy.rpg.game.character.NPCCharacter;
import billy.rpg.game.cmd.executor.CmdProcessor;

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
    public int execute(CmdProcessor cmdProcessor) {
        List<NPCCharacter> npcs = GameFrame.getInstance().getGameContainer().getActiveScriptItem().getNpcs();
        NPCCharacter npcCharacter2Del = null;
        for (NPCCharacter npcCharacter : npcs) {
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
