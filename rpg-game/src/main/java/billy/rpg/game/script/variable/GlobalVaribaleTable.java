package billy.rpg.game.script.variable;

import java.util.*;

public class GlobalVaribaleTable extends VariableTable {

    private Set<String> globalVariables = new HashSet<>();

    @Override
    public boolean existsVariable(String variable) {
        return globalVariables.contains(variable);
    }

    @Override
    public void putVariable(String variable) {
        globalVariables.add(variable);
    }

    @Override
    public void printVariables() {
        System.out.println("=== globalVariables starts ===");
        for (String v: globalVariables) {
            System.out.println("   " +v);
        }
        System.out.println("=== globalVariables ends ===");
    }

}
