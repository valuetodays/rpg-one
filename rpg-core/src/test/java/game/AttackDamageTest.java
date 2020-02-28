package game;

import org.junit.Test;

/**
 * @author liulei@bshf360.com
 * @since 2017-08-29 16:42
 */
public class AttackDamageTest {

    /**
     * 先取基本属性atk,def
        伤害=攻击*(100÷((100+((防御-防御穿透值)*(1-防御穿透百分比))))
     */
    @Test
    public void testDamage3() {
        int attack = 67;
        int defend = 32;
        float dmgF = 1.0f * (attack*1.0f) * (100f/(defend+100f));
        // 50
        System.err.println((int)dmgF);
    }

    /**
     * 添加穿甲（固定护甲穿透，百分比护甲穿透）
     *   伤害=攻击*(100÷((100+((防御-防御穿透值)*(1-防御穿透百分比))))
     */
    @Test
    public void testDamage4() {
        int attack = 67;
        int defend = 32;
        int fixedLethality = 10;  // 固定护甲穿透
        int perLethality = 10;    // 百分比护甲穿透
        float efficientDefAfterfixedLethality = (1.0f*defend-fixedLethality);
        float efficientDefFFinal = efficientDefAfterfixedLethality * (1f-perLethality/100f);
        efficientDefFFinal = Math.max(0f, efficientDefFFinal);
        float dmgF = 1.0f * (attack*1.0f) * (100f/(efficientDefFFinal+100f));
        // 55
        System.err.println((int)dmgF);
    }

}
