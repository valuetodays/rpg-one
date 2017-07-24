package billy.rpg.game.cmd;

/**
 * @author liulei
 * @since 2017-05-18 17:02
 */
public class CreateNPCCmd extends CmdBase {
    private int x; // pos x
    private int y; // pos y
    private int npcNum; // which npc
    private int type; // 1 no walk, 2 random move

    public CreateNPCCmd(int x, int y, int npcNum, int type) {
        super("createnpc");
        this.x = x;
        this.y = y;
        this.npcNum = npcNum;
        this.type = type;
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
                "x=" + x +
                ", y=" + y +
                ", npcNum=" + npcNum +
                ", type=" + type +
                "} " + super.toString();
    }
}
