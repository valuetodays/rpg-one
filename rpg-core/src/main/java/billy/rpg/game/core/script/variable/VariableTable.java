package billy.rpg.game.core.script.variable;

import billy.rpg.game.core.container.GameContainer;

/**
 * @author liulei@bshf360.com
 * @since 2017-12-21 14:08
 */
public abstract class VariableTable {
    /**
     * 存放一个变量
     * @param var 变量
     */
    public abstract void putVariable(GameContainer gameContainer, String var);

    /**
     * 删除一个变量
     * @param var 变量
     */
    public abstract void delVariable(GameContainer gameContainer, String var);

    /**
     * 是否存在指定变量
     * @param var 变量
     */
    public abstract boolean existsVariable(GameContainer gameContainer, String var);

    /**
     * 打印变量
     */
    public abstract void printVariables(GameContainer gameContainer);

}
