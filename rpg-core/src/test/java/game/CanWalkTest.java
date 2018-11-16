package game;

import org.junit.Test;

/**
 * a rpg能否通行的最基本标准
 *
 * @author liulei@bshf360.com
 * @since 2017-12-22 13:22
 */
public class CanWalkTest {

    @Test
    public void testCanWalk() {
        int pos1 = 0x02; // 不能通行
        int pos2 = 0x82; // 可通行
        // 结果为true可通行，false不可通行
        System.out.println((pos1 & 0x80) != 0);
        System.out.println((pos2 & 0x80) != 0);
    }
}
