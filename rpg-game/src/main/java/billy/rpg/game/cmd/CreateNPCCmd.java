package billy.rpg.game.cmd;

import billy.rpg.game.GameFrame;
import billy.rpg.game.character.NPCCharacter;
import billy.rpg.game.character.npc.CommonNPCCharacter;
import billy.rpg.game.character.npc.NoWalkNPCCharacter;
import billy.rpg.game.cmd.executor.CmdProcessor;
import billy.rpg.game.constants.CharacterConstant;

import java.util.List;

/**
 * 创建npc
 *
 * @author liulei
 * @since 2017-05-18 17:02
 */
public class CreateNPCCmd extends CmdBase {
    private int npcId; // npcId in a script, 0 means no talk
    private int x; // pos x
    private int y; // pos y
    private int npcNum; // which npc image to use
    private int type; // 1 no walk, 2 random move

    public int getNpcId() {
        return npcId;
    }

    public int getX() {
        return x;
    }


    public int getY() {
        return y;
    }


    public int getNpcNum() {
        return npcNum;
    }

    public int getType() {
        return type;
    }

    @Override
    public void init() {
        List<String> arguments = super.getArguments();
        npcId = Integer.parseInt(arguments.get(0));
        x = Integer.parseInt(arguments.get(1));
        y = Integer.parseInt(arguments.get(2));
        npcNum = Integer.parseInt(arguments.get(3));
        type = Integer.parseInt(arguments.get(4));
    }

    @Override
    public int execute(CmdProcessor cmdProcessor) {
        int x = getX();
        int y = getY();
        int npcNum = getNpcNum();
        int type = getType();
        NPCCharacter npc = null;
        if (type == 1) {
            npc = new NoWalkNPCCharacter();
        } else {
            npc = new CommonNPCCharacter();
        }
        npc.initPos(x, y);
        npc.setTileNum(npcNum);
        npc.setNumber(getNpcId());
        npc.setDirection(CharacterConstant.PositionEnum.DOWN);
        GameFrame.getInstance().getGameContainer().getActiveScriptItem().getNpcs().add(npc);
        return 0;
    }

    @Override
    public String getUsage() {
        return null; // TODO
    }

    @Override
    public int getArgumentSize() {
        return 5;
    }

    @Override
    public String toString() {
        return "CreateNPCCmd{" +
                "npcId=" + npcId +
                ", x=" + x +
                ", y=" + y +
                ", npcNum=" + npcNum +
                ", type=" + type +
                "} " + super.toString();
    }
}
