package billy.rpg.game.cmd.executor;

import billy.rpg.game.cmd.CmdBase;
import org.jline.reader.ParsedLine;
import org.jline.reader.Parser;
import org.jline.reader.impl.DefaultParser;

import java.util.List;

/**
 * @author lei.liu@datatist.com
 * @since 2018-09-27 19:10:36
 */
public class JlineCmdParser extends CmdParser0 {
    private final Parser parser = new DefaultParser();

    @Override
    public CmdBase doParse(String scriptFileName, int lineNumber, String lineData) {
        ParsedLine parse = parser.parse(lineData, 0);
        List<String> words = parse.words();
        logger.debug(words);

        String command = words.get(0);
        // TODO
        // 读取CmdBase的所有子类；
        // 根据 command的值去查找对应的CmdBase子类
        // 调用 CmdBase#initCommand()初始化命令
        if (words.size() == 1) {
            cmdBase = parse0(scriptFileName, lineNumber, line.toLowerCase());
        } else {
            cmdBase = parseNew(scriptFileName, lineNumber, command, words.subList(1, words.size()));
        }
        return null;
    }
}
