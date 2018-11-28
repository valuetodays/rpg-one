package billy.rpg.game.core.command.parser.support;

import billy.rpg.game.core.command.CmdBase;
import billy.rpg.game.core.command.LabelCmd;
import billy.rpg.game.core.command.parser.CommandParser;
import org.jline.reader.ParsedLine;
import org.jline.reader.Parser;
import org.jline.reader.impl.DefaultParser;

import java.util.List;
import java.util.Map;

/**
 * @author lei.liu@datatist.com
 * @since 2018-09-27 19:10:36
 */
public class JlineCommandParser extends CommandParser {
    private final Parser parser = new DefaultParser();

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
