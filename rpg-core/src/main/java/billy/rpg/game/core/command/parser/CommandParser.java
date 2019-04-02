package billy.rpg.game.core.command.parser;

import billy.rpg.game.core.command.CmdBase;

/**
 * @author lei.liu
 * @since 2019-04-02 16:59
 */
public interface CommandParser {
    CmdBase parse(String scriptFileName, int lineNumber, String line);
}
