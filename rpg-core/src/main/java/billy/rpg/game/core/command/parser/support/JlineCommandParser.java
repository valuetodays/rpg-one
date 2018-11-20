package billy.rpg.game.core.command.parser.support;

import billy.rpg.game.core.command.CmdBase;
import billy.rpg.game.core.command.LabelCmd;
import billy.rpg.game.core.command.parser.CommandParser;
import billy.rpg.game.core.util.CoreUtil;
import org.apache.commons.lang.StringUtils;
import org.jline.reader.ParsedLine;
import org.jline.reader.Parser;
import org.jline.reader.impl.DefaultParser;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author lei.liu@datatist.com
 * @since 2018-09-27 19:10:36
 */
public class JlineCommandParser extends CommandParser {
    private final Parser parser = new DefaultParser();

    private static Map<String, ? extends Class<CmdBase>> traceAllCmdClass() {
        String pkg = getCommandPackage();

        String pkgPath = StringUtils.replace(pkg,".", "/");
        String path = CoreUtil.getResourcePath(pkgPath);
        File directory = new File(path);
        List<? extends Class<CmdBase>> cmdClassList = Arrays.stream(directory.listFiles()).filter(File::isFile).map(e -> pkg + e.getName().replace(".class", "")).map(e -> {
            try {
                Class<?> aClass = Class.forName(e);
                if (CmdBase.class.isAssignableFrom(aClass)) {
                    return (Class<CmdBase>)aClass;
                }
                return null;
            } catch (ClassNotFoundException e1) {
                throw new RuntimeException(e1.getMessage());
            }
        }).filter(Objects::nonNull).collect(Collectors.toList());

        return cmdClassList.stream().collect(Collectors.toMap(e -> e.getSimpleName().toUpperCase(), e -> e));
    }

    private static String getCommandPackage() {
//        String pkg = "billy.rpg.game.core.command."; hard-code style
        String jlineCommandParserParentPath = CommandParser.class.getPackage().getName();
        ArrayList<String> packageNameArr = new ArrayList<>(Arrays.asList(StringUtils.split(jlineCommandParserParentPath, ".")));
        List<String> packageNameList = packageNameArr.subList(0, packageNameArr.size() - 1);
        return StringUtils.join(packageNameList, ".") + ".";
    }

    @Override
    public CmdBase doParse(String scriptFileName, int lineNumber, String lineData) {
        ParsedLine parse = parser.parse(lineData, 0);
        List<String> words = parse.words();

        Map<String, ? extends Class<CmdBase>> cmdClassMap = traceAllCmdClass();

        Class<CmdBase> aClass = null;
        String commandName = words.get(0);
        if (commandName.endsWith(":")) { // 是一个Label
            aClass = cmdClassMap.get(LabelCmd.class.getSimpleName().toUpperCase());
        } else {
            String commandClassName = commandName + CmdBase.class.getSimpleName().replace("Base", "");
            aClass = cmdClassMap.get(commandClassName.toUpperCase());
            if (aClass == null) {
                throw new RuntimeException("command not support: " + commandName);
            }
        }
        CmdBase cmdBase = null;
        try {
            cmdBase = aClass.newInstance();
            cmdBase.initCommand(lineNumber, commandName, words.subList(1, words.size()));
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return cmdBase;
    }
}
