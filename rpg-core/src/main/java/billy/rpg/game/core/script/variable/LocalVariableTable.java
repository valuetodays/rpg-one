package billy.rpg.game.core.script.variable;

import billy.rpg.game.core.container.GameContainer;
import billy.rpg.game.core.item.ScriptItem;

import java.util.List;

/**
 * @author liulei@bshf360.com
 * @since 2017-12-21 14:07
 */
public class LocalVariableTable extends VariableTable {

    @Override
    public void putVariable(GameContainer gameContainer, String var) {
        ScriptItem activeScriptItem = gameContainer.getActiveScriptItem();
        activeScriptItem.addVariable(var);
    }

    @Override
    public void delVariable(GameContainer gameContainer, String var) {
        ScriptItem activeScriptItem = gameContainer.getActiveScriptItem();
        activeScriptItem.delVariable(var);
    }

    @Override
    public boolean existsVariable(GameContainer gameContainer, String var) {
        ScriptItem activeScriptItem = gameContainer.getActiveScriptItem();
        return activeScriptItem.getVariable(var);
    }

    @Override
    public void printVariables(GameContainer gameContainer) {
        List<ScriptItem> scriptItemList = gameContainer.getScriptItemList();
        for (ScriptItem si : scriptItemList) {
            si.printVariable();
        }
    }
}
