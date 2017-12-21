package billy.rpg.game.script.variable;

import org.apache.commons.lang.StringUtils;

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
    public void putVariable(String var) {
        if (isGlobalVirtual(var)) {
            globalVirtualTable.putVariable(var);
        } else {
            localVirtualTable.putVariable(var);
        }
    }

    /**
     * 全数字的变量就是全局变量
     * @param var 变量
     */
    private boolean isGlobalVirtual(String var) {
        return StringUtils.isNumeric(var);
    }

    @Override
    public boolean getVariable(String var) {
        if (isGlobalVirtual(var)) {
            return globalVirtualTable.getVariable(var);
        } else {
            return localVirtualTable.getVariable(var);
        }
    }

    @Override
    public void printVariables() {
        globalVirtualTable.printVariables();
        localVirtualTable.printVariables();
    }
}
