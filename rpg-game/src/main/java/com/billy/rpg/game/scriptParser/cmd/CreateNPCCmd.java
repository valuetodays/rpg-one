package com.billy.rpg.game.scriptParser.cmd;

/**
 * @author liulei
 * @since 2017-05-18 17:02
 */
public class CreateNPCCmd extends CmdBase {
    private int x; // pos x
    private int y; // pos y
    private int npcNum; // which npc

    public CreateNPCCmd(int x, int y, int npcNum) {
        super("createnpc");
        this.x = x;
        this.y = y;
        this.npcNum = npcNum;
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

}
