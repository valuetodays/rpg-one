package billy.rpg.game.core.buff.util;

import billy.rpg.game.core.ExpectedExceptionTestBase;
import billy.rpg.game.core.buff.AttackEnhanceFixedValueBuff;
import billy.rpg.game.core.buff.Buff;
import billy.rpg.game.core.exception.GameException;
import billy.rpg.resource.skill.SkillMetaData;
import org.junit.Assert;
import org.junit.Test;


/**
 * @author lei.liu
 * @since 2019-05-08 09:56
 */
public class BuffUtilTest extends ExpectedExceptionTestBase {

    @Test
    public void skillToBuff_normal() {
        SkillMetaData skill = new SkillMetaData();
        skill.setBuff("AttackEnhanceFixedValueBuff");
        skill.setBuffValue(10);
        skill.setBuffRound(7);
        Buff buff = BuffUtil.skillToBuff(skill);
        Assert.assertNotNull(buff);
        Assert.assertTrue(buff instanceof AttackEnhanceFixedValueBuff);
    }

    @Test
    public void skillToBuff_abnormal_withoutBuff() {
        SkillMetaData skill = new SkillMetaData();
        expectedException.expect(GameException.class);
        BuffUtil.skillToBuff(skill);
    }

    @Test
    public void skillToBuff_abnormal_withIllegalBuff() {
        SkillMetaData skill = new SkillMetaData();
        skill.setBuff("abedfg");
        expectedException.expect(GameException.class);
        expectedException.expectMessage("no buff.");
        BuffUtil.skillToBuff(skill);
    }
}