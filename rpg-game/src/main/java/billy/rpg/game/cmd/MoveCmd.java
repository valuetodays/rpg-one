package billy.rpg.game.cmd;

import billy.rpg.game.GameFrame;
import billy.rpg.game.character.NPCCharacter;
import billy.rpg.game.cmd.executor.CmdProcessor;

import java.util.List;

/**
 * 移动角色
 */
public class MoveCmd extends CmdBase {

    private int npcid;
    private int faceto;

    public MoveCmd(int npcid, int faceto) {
        super("move");
        this.npcid = npcid;
        this.faceto = faceto;
    }

    @Override
    public int execute(CmdProcessor cmdProcessor) {
        List<NPCCharacter> npcs = GameFrame.getInstance().getGameContainer().getActiveScriptItem().getNpcs();
        for (NPCCharacter npcCharacter : npcs) {
            if (npcid == npcCharacter.getNumber()) {
                npcCharacter.setDirection(faceto);
                int nextPosX = npcCharacter.getNextPosX();
                int nextPosY = npcCharacter.getNextPosY();
                npcCharacter.setPosX(nextPosX);
                npcCharacter.setPosY(nextPosY);
            }
        }
        return 0;
    }

    public int getNpcid() {
        return npcid;
    }

    public int getFaceto() {
        return faceto;
    }
}
