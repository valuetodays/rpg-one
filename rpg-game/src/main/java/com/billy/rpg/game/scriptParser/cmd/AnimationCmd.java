package com.billy.rpg.game.scriptParser.cmd;

public class AnimationCmd extends CmdBase {
    private int number;

    public AnimationCmd(int number) {
        super("animation");
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

}
