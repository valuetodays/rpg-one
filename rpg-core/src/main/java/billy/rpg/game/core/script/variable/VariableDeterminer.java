package billy.rpg.game.core.script.variable;

import java.util.Map;

public class VariableDeterminer extends IntVariable {
    // 全局事件
    private IntVariable globalVariable = new GlobalVariable();

    private static VariableDeterminer VARIABLE_DETERMINER = new VariableDeterminer();
    private VariableDeterminer() {}

    public static VariableDeterminer getInstance() {
        return VARIABLE_DETERMINER;
    }

    @Override
    public void set(String var, Integer value) {
        globalVariable.set(var, value == null ? 0 : value);
    }

    @Override
    public Integer get(String var) {
        return globalVariable.get(var);
    }

    @Override
    public void print() {
        globalVariable.print();
    }

    @Override
    public Map<String, Integer> realData() {
        return globalVariable.realData();
    }
}
