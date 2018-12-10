package billy.rpg.game.core.buff;

import billy.rpg.game.core.character.fightable.BuffManager;
import billy.rpg.game.core.character.fightable.DefaultBuffManager;
import org.junit.Test;

/**
 * @author lei.liu@datatist.com
 * @since 2018-12-06 16:38:15
 */
public class BuffManageTest /* extends GameContainerBaseTest*/ {
    @Test
    public void testAddBuff() {
        BuffManager buffManager = new DefaultBuffManager();
                AttackEnhanceFixedValueBuff attackValueEnhanceBuff = new AttackEnhanceFixedValueBuff(10, Buff.DEFAULT_ROUNDS);
        buffManager.addBuff(attackValueEnhanceBuff);
        // 添加一个buff后
        System.out.println(buffManager.getBuffChainList());
        AttackEnhancePercentValueBuff attackPercentEnhanceBuff = new AttackEnhancePercentValueBuff(20, Buff.DEFAULT_ROUNDS);
        buffManager.addBuff(attackPercentEnhanceBuff);
        // 添加一个相同类型buff后
        System.out.println(buffManager.getBuffChainList());
        DuBuff duBuff = new DuBuff();
        buffManager.addBuff(duBuff);
        System.out.println(buffManager.getBuffChainList());
        DefendWeakenFixedValueBuff defendWeakenFixedValueBuff = new DefendWeakenFixedValueBuff(10, Buff.DEFAULT_ROUNDS);
        buffManager.addBuff(defendWeakenFixedValueBuff);
        // 添加一个降防的buff
        System.out.println(buffManager.getBuffChainList());
    }
}
