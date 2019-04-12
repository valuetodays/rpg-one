package billy.rpg.common.util;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.io.File;
import java.net.URL;

/**
 * @author lei.liu@datatist.com
 * @since 2018-11-28 10:46:23
 */
public class AssetsUtil {
    private static final Logger LOG = Logger.getLogger(AssetsUtil.class);
    private AssetsUtil() {}

    /**
     * 获取资源文件的路径，现已支持开发环境中资源文件的获取和打包后资源文件的获取
     * @param resource resource
     */
    public static String getResourcePath(String resource) {
        if (StringUtils.isBlank(resource)) {
            throw new NullPointerException("resourcePath is null or empty");
        }
        Thread.currentThread().getContextClassLoader().getResource(resource);

        String path = AssetsUtil.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        System.out.println("path -> " + path);

        if (path.contains("/target/classes/")) { // for maven
            return getResourcePathInDev(resource);
        } else if (path.contains("/build/classes/java/main/")) { // for gradle
            String resourcesPath = path.replace("/classes/java", "/resources");
            resourcesPath += resource;
            return makeNativeFileSeparator(resourcesPath);
        } else {
            String gameRootDirectoryPath = new File(path).getParentFile().getParentFile().getAbsoluteFile().getPath();
//            LOG.debug("gameRootDirectoryPath -> " + gameRootDirectoryPath);
            File gameRootDirectory = new File(gameRootDirectoryPath + resource);
//            LOG.debug("gameRootDirectory -> " + gameRootDirectory.getPath());
            return gameRootDirectory.getPath();
        }

//        if ("jar".equals(protocol)) {
//            String resourcePath = resourceURL.toString();
//            String jarPath = resourcePath.substring(0, resourcePath.indexOf("!/") + "!/".length());
//            try {
//                URL jarURL = new URL(jarPath);
//                URLConnection urlConnection = jarURL.openConnection();
////                LOG.debug("connection.class=" + urlConnection.getClass().getName());
//                JarURLConnection jarCon = (JarURLConnection)urlConnection;
//                    JarFile jarFile = jarCon.getJarFile();
//                Enumeration<JarEntry> jarEntries = jarFile.entries();
//
//                while (jarEntries.hasMoreElements()) {
//                    JarEntry entry = jarEntries.nextElement();
//                    String name = entry.getName();
//                    InputStream systemResourceAsStream = ClassLoader.getSystemResourceAsStream(name);
//                    LOG.debug("> " + systemResourceAsStream);
//                    InputStream resourceAsStream = AssetsUtil.class.getClassLoader().getResourceAsStream(name);
////                    LOG.debug("stream: " + resourceAsStream);
//                    inputStreamMap.put(resource, resourceAsStream);
//                }
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
    }

    private static String getResourcePathInDev(String resource) {
        if (resource.startsWith("/")) {
            resource = resource.substring(1);
        }
        URL resourceURL = Thread.currentThread().getContextClassLoader().getResource(resource);
        if (resourceURL == null) {
            throw new RuntimeException("no resource: " + resource);
        }
//        LOG.debug("resource: " + resourceURL.toString());
        String protocol = resourceURL.getProtocol();
//        LOG.debug("protocol: " + protocol);
        if ("file".equals(protocol)) { // 处理开发环境的情况
            String substring = resourceURL.getPath().substring(1);
            return makeNativeFileSeparator(substring);
        }
        throw new RuntimeException("illegal protocol: " + protocol);
    }

    private static String makeNativeFileSeparator(String path) {
        return StringUtils.replace(path, "/", File.separator);
    }
}
