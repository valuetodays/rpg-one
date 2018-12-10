package billy.rpg.game.core.script.variable;

public interface Variable<T> {
    void set(String var);
    void set(String var, T value);
    T get(String var);
    void print();
}
