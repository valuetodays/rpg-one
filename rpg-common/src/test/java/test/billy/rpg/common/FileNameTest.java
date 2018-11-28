package test.billy.rpg.common;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author lei.liu@datatist.com
 * @since 2018-11-14 09:39:22
 */
public class FileNameTest {
    /**
     * 测试文件路径中含有../的情况，能正常从上级目录里寻找文件
     */
    @Test
    public void test() {
        String path = "D:\\tmp\\fmj\\map\\";
        String s = path + "../../m1.tcp";
        File file = new File(s);
        System.out.println(file.getPath());
        System.out.println(file.getName());
        System.out.println(file.length());
    }

    @Test
    public void testJarFile() throws IOException {
        String jarPath = "jar:file:/D:/tmp/fmj/rpg-common-1.1-SNAPSHOT.jar!/";
        URL jarURL = new URL(jarPath);
        URLConnection urlConnection = jarURL.openConnection();
        System.out.println("connection.class=" + urlConnection.getClass().getName());
        JarURLConnection jarCon = (JarURLConnection)urlConnection;
        JarFile jarFile = jarCon.getJarFile();
        Enumeration<JarEntry> jarEntries = jarFile.entries();

        while (jarEntries.hasMoreElements()) {
            JarEntry entry = jarEntries.nextElement();
            String name = entry.getName();
            System.out.println("> " + name);
            if ("assets/GMC.txt".equals(name) && !entry.isDirectory()) {
                // 开始读取文件内容
                InputStream is = this.getClass().getClassLoader().getResourceAsStream(name);
                System.out.println(is);
            }
        }
    }
}
