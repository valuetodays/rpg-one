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
        AttackEnhanceFixedValueBuff attackValueEnhanceBuff = new AttackEnhanceFixedValueBuff(10, 3);
        buffManager.addBuff(attackValueEnhanceBuff);
        // 添加一个buff后
        System.out.println(buffManager.getBuffList());
        AttackEnhancePercentValueBuff attackPercentEnhanceBuff = new AttackEnhancePercentValueBuff(20, 4);
        buffManager.addBuff(attackPercentEnhanceBuff);
        // 添加一个相同类型buff后
        System.out.println(buffManager.getBuffList());
        DuBuff duBuff = new DuBuff();
        buffManager.addBuff(duBuff);
        System.out.println(buffManager.getBuffList());
    }
}
