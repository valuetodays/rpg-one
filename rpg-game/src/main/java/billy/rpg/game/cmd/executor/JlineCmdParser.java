package billy.rpg.game.cmd.executor;

import billy.rpg.game.cmd.CmdBase;
import billy.rpg.game.cmd.LabelCmd;
import org.jline.reader.ParsedLine;
import org.jline.reader.Parser;
import org.jline.reader.impl.DefaultParser;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author lei.liu@datatist.com
 * @since 2018-09-27 19:10:36
 */
public class JlineCmdParser extends CmdParser0 {
    private final Parser parser = new DefaultParser();

    private static Map<String, ? extends Class<CmdBase>> traceAllCmdClass() {
        String pkg = "billy.rpg.game.cmd.";
        String pkgPath = pkg.replace(".", "/");
        String path = Thread.currentThread().getContextClassLoader().getResource(pkgPath).getPath();
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
//        cmdClassList.forEach(e -> {
//            System.out.println(e.getSimpleName());
//        });
        Map<String, ? extends Class<CmdBase>> cmdClassMap = cmdClassList.stream().collect(Collectors.toMap(e -> e.getSimpleName().toUpperCase(), e -> e));
        return cmdClassMap;
    }

    @Override
    public CmdBase doParse(String scriptFileName, int lineNumber, String lineData) {
        ParsedLine parse = parser.parse(lineData, 0);
        List<String> words = parse.words();
        logger.debug(words);

        Class<CmdBase> aClass = null;
        Map<String, ? extends Class<CmdBase>> cmdClassMap = traceAllCmdClass();

        String commandName = words.get(0);
        if (commandName.endsWith(":")) {
            aClass = cmdClassMap.get(LabelCmd.class.getSimpleName().toUpperCase());
        } else {
            String commandClassName = commandName + "cmd";
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
