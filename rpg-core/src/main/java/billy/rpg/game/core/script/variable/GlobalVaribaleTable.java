package billy.rpg.game.core.script.variable;

import java.util.*;

import billy.rpg.game.core.container.GameContainer;

public class GlobalVaribaleTable extends VariableTable {

    private Set<String> globalVariables = new HashSet<>();

    @Override
    public boolean existsVariable(GameContainer gameContainer, String variable) {
        return globalVariables.contains(variable);
    }

    @Override
    public void putVariable(GameContainer gameContainer, String var) {
        globalVariables.add(var);
    }

    @Override
    public void delVariable(GameContainer gameContainer, String var) {
        globalVariables.remove(var);
    }

    @Override
    public void printVariables(GameContainer gameContainer) {
        System.out.println("=== globalVariables starts ===");
        for (String v: globalVariables) {
            System.out.println("   " +v);
        }
        System.out.println("=== globalVariables ends ===");
    }

}
