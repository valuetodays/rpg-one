package com.rupeng.game;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.net.URL;

import javax.swing.SwingUtilities;

public class GameUtils {

    /**
     * 在UI线程中执行runnable，并且等待结束
     * 
     * @param runnable
     */
    public static void invokeAndWait(Runnable runnable) {
        try {
            if (SwingUtilities.isEventDispatchThread()) {
                runnable.run();
            } else {
                SwingUtilities.invokeAndWait(runnable);
            }
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void closeQuietly(InputStream inStream) {
        if (inStream != null) {
            try {
                inStream.close();
            } catch (IOException e) {

            }
        }
    }

    public static byte[] readAllBytes(File file) throws IOException {

        FileInputStream inStream = new FileInputStream(file);
        try {
            byte[] buffer = new byte[(int) file.length()];
            inStream.read(buffer);
            return buffer;
        } finally {
            closeQuietly(inStream);
        }
    }

    /**
     * 得到.class所在的bin文件夹的全路径
     * 
     * @return
     */
    public static File getBinDir() {
        // 如果把RupengGame.jar放到了ext下的话GameUtils.class.getClassLoader()得到的是ExtClassLoader的对象，
        // 而具体游戏主程序类的.class.getClassLoader()是AppClassLoader的对象
        // 这样如果用GameUtils.class.getClassLoader()就加载不到游戏ClassPath中的资源了
        // 因此要用ClassLoader.getSystemClassLoader()，而不是
        // GameUtils.class.getClassLoader()
        URL rootURL = ClassLoader.getSystemClassLoader().getResource("");
        // URL rootURL = GameUtils.class.getClassLoader().getResource("");
        try {
            File dir = new File(rootURL.toURI());
            return dir;
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public static String mapPath(String relativePath) {
        return new File(getBinDir(), relativePath).toString();
    }

    public static String[] listResources(String packageName, String endsWith)
            throws IOException {
        // 如果把RupengGame.jar放到了ext下的话GameUtils.class.getClassLoader()是ExtClassLoader的对象，
        // 而具体游戏主程序类的.class.getClassLoader()是AppClassLoader的对象
        // 这样如果用GameUtils.class.getClassLoader()就加载不到游戏ClassPath中的资源了
        // 因此要用ClassLoader.getSystemClassLoader()，而不是
        // GameUtils.class.getClassLoader()
        URL rootURL = ClassLoader.getSystemClassLoader().getResource(
                packageName);
        // URL rootURL =
        // GameUtils.class.getClassLoader().getResource(packageName);

        File rootFile;
        try {
            rootFile = new File(rootURL.toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        // 要求主程序不能以jar的方式部署运行
        String[] pngFiles = rootFile.list(new EndsWithFilenameFilter(endsWith));
        return pngFiles;
    }

    public static void pause(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {

        }
    }
}
