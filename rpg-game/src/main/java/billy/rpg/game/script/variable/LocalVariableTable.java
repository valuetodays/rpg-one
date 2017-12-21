package billy.rpg.game.script.variable;

import billy.rpg.game.container.GameContainer;
import billy.rpg.game.resource.item.ScriptItem;

import java.util.List;

/**
 * @author liulei@bshf360.com
 * @since 2017-12-21 14:07
 */
public class LocalVariableTable extends VariableTable {

    @Override
    public void putVariable(String var) {
        ScriptItem activeScriptItem = GameContainer.getInstance().getActiveScriptItem();
        activeScriptItem.addVariable(var);
    }

    @Override
    public boolean getVariable(String var) {
        ScriptItem activeScriptItem = GameContainer.getInstance().getActiveScriptItem();
        return activeScriptItem.getVariable(var);
    }

    @Override
    public void printVariables() {
        List<ScriptItem> scriptItemList = GameContainer.getInstance().getScriptItemList();
        for (ScriptItem si : scriptItemList) {
            si.printVariable();
        }
    }
}
