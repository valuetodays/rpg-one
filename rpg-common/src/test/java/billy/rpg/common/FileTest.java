package billy.rpg.common;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
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
 * @author lei.liu
 * @since 2018-11-14 09:39:22
 */
public class FileTest {
    /**
     * 测试文件路径中含有../的情况，能正常从上级目录里寻找文件
     */
    @Test
    public void testFilePathContainRelativeSymbol() {
        String path = "target/classes/billy/rpg/common/formatter/";
        String s = path + "../util/JavaVersionUtil.class";
        File file = new File(s);
        Assert.assertEquals("JavaVersionUtil.class", file.getName());
    }

    @Test
    public void testJarFile() throws IOException {
        String jarPath = "jar:file:/"+System.getenv("JAVA_HOME")+"/lib/tools.jar!/";
        URL jarURL = new URL(jarPath);
        URLConnection urlConnection = jarURL.openConnection();
        Assert.assertEquals("sun.net.www.protocol.jar.JarURLConnection", urlConnection.getClass().getName());
        JarURLConnection jarCon = (JarURLConnection)urlConnection;
        JarFile jarFile = jarCon.getJarFile();
        Enumeration<JarEntry> jarEntries = jarFile.entries();

        String str = null;
        while (jarEntries.hasMoreElements()) {
            JarEntry entry = jarEntries.nextElement();
            String name = entry.getName();
            if ("META-INF/MANIFEST.MF".equals(name) && !entry.isDirectory()) {
                // 开始读取文件内容
                InputStream is = this.getClass().getClassLoader().getResourceAsStream(name);
                str = IOUtils.toString(is);
            }
        }
        Assert.assertNotNull(str);
    }
}
