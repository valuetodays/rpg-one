package billy.rpg.game.command;

import org.jline.reader.ParsedLine;
import org.jline.reader.Parser;
import org.jline.reader.impl.DefaultParser;
import org.junit.Test;

/**
 * @author lei.liu@datatist.com
 * @since 2018-09-28 10:04:14
 */
public class ChoiceCmdTest {

    @Test
    public void testDoParse() {
        String line = "choice '我们说过话了吗' '说过了' '没有说过' '记不得了' aaa bbb ccc";
        Parser parser = new DefaultParser();
        ParsedLine parse = parser.parse(line, 0);
        System.out.println(parse.words());
        ChoiceCmd choiceCmd = new ChoiceCmd();
        choiceCmd.initCommand(1, parse.words().get(0), parse.words().subList(1, parse.words().size()));
    }
}