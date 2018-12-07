package billy.rpg.game.core;

import billy.rpg.common.util.JsonUtil;
import billy.rpg.game.core.buff.Buff;
import billy.rpg.resource.skill.SkillMetaData;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;

/**
 * @author lei.liu@datatist.com
 * @since 2018-12-07 16:13:52
 */
public class SkillTest extends GameContainerBaseTest {

    @Test
    public void test() {
        SkillMetaData skillMetaData = gameContainer.getSkillMetaDataOf(3);
        logger.debug("skill: " + JsonUtil.toPrettyJsonString(skillMetaData));
        String buff = skillMetaData.getBuff();
        String buffClassName = Buff.class.getPackage().getName() + "." + buff;
        logger.debug("buffClassName: " + buffClassName);
        int buffValue = skillMetaData.getBuffValue();
        int buffRound = skillMetaData.getBuffRound();
        try {
            Class<?> aClass = Class.forName(buffClassName);
            Buff buffObject = (Buff)aClass.getConstructors()[0].newInstance(buffValue, buffRound);
            logger.debug("buffObject: " + buffObject.getClass().getName());

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
