package billy.rpg.game.core.command.parser;

import billy.rpg.common.util.AssetsUtil;
import billy.rpg.game.core.command.CmdBase;
import billy.rpg.game.core.command.EmptyCmd;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

/**
 * @author lei.liu@datatist.com
 * @since 2018-09-27 19:10:45
 */
public abstract class CommandParser {
    protected final Logger logger = Logger.getLogger(getClass());

    private static Map<String, ? extends Class<CmdBase>> cmdClassMap = null;

    public CmdBase parse(String scriptFileName, int lineNumber, String lineData) {
        CmdBase before = before(scriptFileName, lineNumber, lineData);
        if (before != null) {
            return before;
        }
        CmdBase cmdBase = doParse(scriptFileName, lineNumber, lineData);
        end();
        return cmdBase;
    }

    private CmdBase before(String scriptFileName, int lineNumber, String lineData) {
        if (lineData == null) {
            return EmptyCmd.EMPTY_CMD;
        }
        lineData = lineData.trim();
        if (lineData.length() == 0) {
            return EmptyCmd.EMPTY_CMD;
        }

        if (lineData.endsWith(";")) {
            throw new RuntimeException("命令以;结尾了，是想要的吗？");
        }

        // 注释，忽略本行数据
        if (lineData.startsWith("@")) {
            return EmptyCmd.EMPTY_CMD;
        }

        return null;
    }

    protected void end(){}

    // parse *Cmd.class start
    /**
     * 获取到所有*Cmd.class的对象列表
     */
    protected Map<String, ? extends Class<CmdBase>> traceAllCmdClass() {
        if (cmdClassMap != null) {
            return cmdClassMap;
        }
        String pkg = getCommandPackage();
        String pkgAsPath = StringUtils.replace(pkg,".", "/");


        String coreJarLocationPath = CommandParser.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        logger.debug("path -> " + coreJarLocationPath);

        List<String> cmdList = null;
        if (coreJarLocationPath.contains("/target/classes/")) {
            cmdList = getCmdListInDev(pkgAsPath);
        } else {
            cmdList = getCmdListInDistribute(coreJarLocationPath, pkgAsPath);
        }
        if (CollectionUtils.isEmpty(cmdList)) {
            throw new RuntimeException("command not found");
        }
        logger.debug("cmdList -> " + cmdList);
        List<? extends Class<CmdBase>> cmdClassList = cmdList.stream()
                .map(e -> e.replace("/", ".").replace(".class", ""))
                .map(e -> {
                            try {
                                Class<?> aClass = Class.forName(e);
                                if (CmdBase.class.isAssignableFrom(aClass)) {
                                    return (Class<CmdBase>)aClass;
                                }
                                return null;
                            } catch (ClassNotFoundException e1) {
                                throw new RuntimeException(e1.getMessage());
                            }
                        }
                ).filter(Objects::nonNull).collect(Collectors.toList());

        cmdClassMap = cmdClassList.stream().collect(Collectors.toMap(e -> e.getSimpleName().toUpperCase(), e -> e));
        return cmdClassMap;
    }

    private List<String> getCmdListInDev(String pkgAsPath) {
        final String pkg = StringUtils.replace(pkgAsPath, "/", ".");
        String path = AssetsUtil.getResourcePath(pkgAsPath);
        // 当执行Junit4测试类的时候不替换如下就不能正常！
        path = path.replace(File.separator + "target"+File.separator + "test-classes" + File.separator,
                File.separator + "target"+File.separator+"classes" + File.separator);

        File directory = new File(path);
        List<String> cmdClassList = Arrays.stream(directory.listFiles())
                .filter(File::isFile)
                .map(e -> pkg + e.getName().replace(".class", ""))
                .filter(Objects::nonNull).collect(Collectors.toList());

        return cmdClassList;
    }

    private List<String> getCmdListInDistribute(String coreJarLocationPath, String pkgAsPath) {
        String jarPath = "jar:file:"+coreJarLocationPath+"!/";
//        logger.debug("jarPath: " + jarPath);
//        logger.debug("pkgAsPath: " + pkgAsPath);

        List<String> cmdList = new ArrayList<>();
        try {
            URL jarURL = new URL(jarPath);
            URLConnection urlConnection = jarURL.openConnection();
            JarURLConnection jarCon = (JarURLConnection)urlConnection;
            JarFile jarFile = jarCon.getJarFile();
            Enumeration<JarEntry> jarEntries = jarFile.entries();

            while (jarEntries.hasMoreElements()) {
                JarEntry entry = jarEntries.nextElement();
                String name = entry.getName();
                if (!entry.isDirectory() && name.startsWith(pkgAsPath) && name.endsWith("Cmd.class")) {
                    cmdList.add(name);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cmdList;
    }

    private static String getCommandPackage() {
//        String pkg = "billy.rpg.game.core.command."; hard-code style
        String commandParserPath = CommandParser.class.getPackage().getName();
        ArrayList<String> packageNameArr = new ArrayList<>(Arrays.asList(StringUtils.split(commandParserPath, ".")));
        List<String> packageNameList = packageNameArr.subList(0, packageNameArr.size() - 1);
        return StringUtils.join(packageNameList, ".") + ".";
    }
    // parse *Cmd.class end

    /**
     * 把一行脚本转化为一个命令
     * @param scriptFileName 脚本文本名称
     * @param lineNumber 行号
     * @param lineData 脚本内容
     */
    public abstract CmdBase doParse(String scriptFileName, int lineNumber, String lineData);
}
