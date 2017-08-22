package billy.rpg.game.cmd;

/**
 * @author liulei
 * @since 2017-05-18 17:02
 */
public class CreateNPCCmd extends CmdBase {
    private int npcId; // npcId in a script, 0 means no talk
    private int x; // pos x
    private int y; // pos y
    private int npcNum; // which npc image to use
    private int type; // 1 no walk, 2 random move

    public CreateNPCCmd(int npcId, int x, int y, int npcNum, int type) {
        super("createnpc");
        this.npcId = npcId;
        this.x = x;
        this.y = y;
        this.npcNum = npcNum;
        this.type = type;
    }

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
