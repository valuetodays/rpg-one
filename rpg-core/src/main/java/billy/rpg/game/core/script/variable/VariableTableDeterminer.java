package billy.rpg.game.core.script.variable;

import org.apache.commons.lang.StringUtils;

import billy.rpg.game.core.container.GameContainer;

/**
 * @author liulei@bshf360.com
 * @since 2017-12-21 14:12
 */
public class VariableTableDeterminer extends VariableTable {
    private VariableTable globalVirtualTable = new GlobalVaribaleTable();
    private VariableTable localVirtualTable = new LocalVariableTable();

    private static VariableTableDeterminer virtualTableDeterminer = new VariableTableDeterminer();
    private VariableTableDeterminer() {}

    public static VariableTableDeterminer getInstance() {
        return virtualTableDeterminer;
    }

    @Override
    public void putVariable(GameContainer gameContainer, String var) {
        if (isGlobalVirtual(var)) {
            globalVirtualTable.putVariable(gameContainer, var);
        } else {
            localVirtualTable.putVariable(gameContainer, var);
        }
    }

    @Override
    public void delVariable(GameContainer gameContainer, String var) {
        if (isGlobalVirtual(var)) {
            globalVirtualTable.delVariable(gameContainer, var);
        } else {
            localVirtualTable.delVariable(gameContainer, var);
        }
    }

    /**
     * 全数字的变量就是全局变量，这种约束不太好
     *
     * @param var 变量名
     */
    private boolean isGlobalVirtual(String var) {
        return StringUtils.isNumeric(var);
    }

    @Override
    public boolean existsVariable(GameContainer gameContainer, String var) {
        if (isGlobalVirtual(var)) {
            return globalVirtualTable.existsVariable(gameContainer, var);
        } else {
            return localVirtualTable.existsVariable(gameContainer, var);
        }
    }

    @Override
    public void printVariables(GameContainer gameContainer) {
        globalVirtualTable.printVariables(gameContainer);
        localVirtualTable.printVariables(gameContainer);
    }
}
