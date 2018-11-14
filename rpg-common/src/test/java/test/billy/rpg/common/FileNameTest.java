package test.billy.rpg.common;

import org.junit.Test;

import java.io.File;

/**
 * @author lei.liu@datatist.com
 * @since 2018-11-14 09:39:22
 */
public class FileNameTest {
    @Test
    public void test() {
        String path = "D:\\tmp\\fmj\\map\\";
        String s = path + "../../m1.tcp";
        File file = new File(s);
        System.out.println(file.getPath());
        System.out.println(file.getName());
        System.out.println(file.length());
    }
}
