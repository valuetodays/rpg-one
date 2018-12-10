package billy.rpg.game.core.script.variable;

import java.util.Map;

public interface Variable<T> {
    void set(String var);
    void set(String var, T value);
    T get(String var);
    void print();
    Map<String, T> realData();
}
