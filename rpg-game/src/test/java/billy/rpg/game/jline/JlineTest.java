package billy.rpg.game.jline;

import org.jline.reader.ParsedLine;
import org.jline.reader.Parser;
import org.jline.reader.impl.DefaultParser;
import org.junit.Test;

/**
 * @author lei.liu@datatist.com
 * @since 2018-09-27 18:16:04
 */
public class JlineTest {

    @Test
    public void test() {
        String message = "1 '柳清风' RIGHT '小蝴蝶，不要跑……'";
        Parser parser = new DefaultParser();
        ParsedLine parse = parser.parse(message, 0);
        System.out.println(parse.words());
    }
}
