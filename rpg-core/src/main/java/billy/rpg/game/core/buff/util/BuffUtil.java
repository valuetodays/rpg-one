package billy.rpg.game.core.buff.util;

import billy.rpg.game.core.buff.Buff;
import billy.rpg.game.core.exception.GameException;
import billy.rpg.resource.skill.SkillMetaData;

import java.lang.reflect.InvocationTargetException;

/**
 * @author lei.liu
 * @since 2018-12-07 17:07:31
 */
public final class BuffUtil {
    private BuffUtil() {}

    public static Buff skillToBuff(SkillMetaData skillMetaData) {
        String buff = skillMetaData.getBuff();
        if (buff == null) {
            throw new GameException("no buff specified: " + skillMetaData.getName() + "#" + skillMetaData.getNumber());
        }
        int buffValue = skillMetaData.getBuffValue();
        int buffRound = skillMetaData.getBuffRound();
        String buffClassName = Buff.class.getPackage().getName() + "." + buff;
        try {
            Class<?> aClass = Class.forName(buffClassName);
            Buff buffObject = (Buff) aClass.getConstructors()[0].newInstance(buffValue, buffRound + 1);
            buffObject.setName(skillMetaData.getName());
            return buffObject;
        } catch (IllegalAccessException
                | InstantiationException
                | InvocationTargetException
                | ClassNotFoundException e) {

        }

        throw new GameException("no buff. ");
    }
}
