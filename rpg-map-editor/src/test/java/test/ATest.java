package test;

import org.junit.Test;

/**
 *
 * @author liulei
 * @since 2017-05-16 17:57
 */
public class ATest {
    @Test
    public void testA() {

        String npcName = "little_02.png";
        int npcNum = Integer.parseInt(npcName.substring("little_".length(), npcName.lastIndexOf(".")));
        System.out.println(npcNum);
    }
}
