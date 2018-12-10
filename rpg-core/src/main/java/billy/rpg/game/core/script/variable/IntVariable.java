package billy.rpg.game.core.script.variable;

public abstract class IntVariable implements Variable<Integer> {
    public static final int DEFAULT_VALUE = 1;

    public void set(String var) {
        this.set(var, DEFAULT_VALUE);
    }

    public abstract void set(String var, Integer value);
    public abstract Integer get(String var);
    public abstract void print();
}
