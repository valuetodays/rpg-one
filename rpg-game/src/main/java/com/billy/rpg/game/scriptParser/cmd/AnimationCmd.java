package com.billy.rpg.game.scriptParser.cmd;

public class AnimationCmd extends CmdBase {
    private int no;
    private boolean repeat;
    
    public AnimationCmd(int no, boolean repeat) {
        super("animation");
        this.no = no;
        this.repeat = repeat;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public boolean isRepeat() {
        return repeat;
    }

    public void setRepeat(boolean repeat) {
        this.repeat = repeat;
    }
    
    
    
}
