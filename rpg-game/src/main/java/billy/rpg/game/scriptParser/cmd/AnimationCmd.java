package billy.rpg.game.scriptParser.cmd;

public class AnimationCmd extends CmdBase {
    private int number;
    private int x;
    private int y;

    public AnimationCmd(int number, int x, int y) {
        super("animation");
        this.number = number;
        this.x = x;
        this.y = y;
    }

    public int getNumber() {
        return number;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
