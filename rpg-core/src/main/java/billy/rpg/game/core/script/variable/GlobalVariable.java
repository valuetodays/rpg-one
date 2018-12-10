package billy.rpg.game.core.script.variable;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class GlobalVariable extends IntVariable {
    private Map<String, Integer> globalVariables = new HashMap<>();

    @Override
    public void set(String var, Integer value) {
//        if (null != get(var)) {
//            throw new RuntimeException("variable already exists");
//        }
        globalVariables.put(var, value);
    }

    @Override
    public Integer get(String var) {
        return globalVariables.get(var);
    }

    @Override
    public void print() {
        Set<Map.Entry<String, Integer>> entries = globalVariables.entrySet();
        System.out.println("=== globalVariable starts ===");
        for (Map.Entry<String, Integer> entry : entries) {
            System.out.println(" |- " + entry.getKey() + " = " + entry.getValue());
        }
        System.out.println("=== globalVariable ends ===");
    }

    @Override
    public Map<String, Integer> realData() {
        Map<String, Integer> map = new HashMap<>();
        map.putAll(globalVariables);
        return map;
    }
}
