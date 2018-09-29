package billy.rpg.game.cmd.parser;

import billy.rpg.game.cmd.CmdBase;
import billy.rpg.game.cmd.EmptyCmd;
import org.apache.log4j.Logger;

/**
 * @author lei.liu@datatist.com
 * @since 2018-09-27 19:10:45
 */
public abstract class CmdParser0 {
    protected final Logger logger = Logger.getLogger(getClass());

    public CmdBase parse(String scriptFileName, int lineNumber, String lineData) {
        CmdBase before = before(scriptFileName, lineNumber, lineData);
        if (before != null) {
            return before;
        }
        CmdBase cmdBase = doParse(scriptFileName, lineNumber, lineData);
        end();
        return cmdBase;
    }

    protected CmdBase before(String scriptFileName, int lineNumber, String lineData) {
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

    public abstract CmdBase doParse(String scriptFileName, int lineNumber, String lineData);
}
