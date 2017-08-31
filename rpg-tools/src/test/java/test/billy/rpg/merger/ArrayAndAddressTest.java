package test.billy.rpg.merger;

import org.junit.Test;

/**
 * @author liulei@bshf360.com
 * @since 2017-08-31 17:40
 */
public class ArrayAndAddressTest {
    @Test
    public void test0() {
        int count = 2;
        int sec1Count = 2;
        int sec2Count = 1;
        int[] sec1 = new int[]{100, 200};   // 2个
        int[] sec2 = new int[]{300};        // 1个
        int[] data = new int[sec1Count + sec2Count];
        data[0] = sec1[0];
        data[1] = sec1[1];
        data[2] = sec2[0];
        ;
        for (int datum : data) {
            System.out.println(datum);
        }
    }
}
