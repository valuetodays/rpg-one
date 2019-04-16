package billy.rpg.common.formatter;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.Color;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

/**
 * @see ColorDialogTextFormatter
 */
public class ColorDialogTextFormatterTest {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final static int WORDS_NUM_PER_LINE = 20;

    private DialogTextFormatter dialogTextFormatter;

    @Before
    public void before() {
        dialogTextFormatter = new ColorDialogTextFormatter(WORDS_NUM_PER_LINE);
    }

    @Test
    public void testFormat_whenTextLengthIsShort() {
        String dialogText = "还是先去见师傅吧。";
        DialogFormattedResult formattedResult = dialogTextFormatter.format(dialogText);
        int totalLine = formattedResult.getTotalLine();
        assertEquals(1, totalLine);
        List<DialogFormattedText> textList = formattedResult.getTextList();
        DialogFormattedText dialogFormattedText = textList.get(0);
        assertThat(dialogFormattedText.getColor(), is(Color.WHITE));
        assertThat(dialogFormattedText.getContent(), nullValue());

        DialogFormattedText dialogFormattedText1 = textList.get(1);
        assertThat(dialogFormattedText1.getColor(), is(Color.WHITE));
        assertThat(dialogFormattedText1.getContent(), is("还是先去见师傅吧。"));

        debug(formattedResult);
    }

    @Test
    public void testFormat() {
        String dialogText = "大师兄，师傅让我去取伏魔剑。据说伏魔洞中有`y`八位护剑兽`/y`，还有一位`y`护剑神`/y`，我怕取不出伏魔剑，所以……";
        DialogFormattedResult formattedResult = dialogTextFormatter.format(dialogText);
        int totalLine = formattedResult.getTotalLine();
        Assert.assertEquals(3, totalLine);
        for (DialogFormattedText dialogFormattedText : formattedResult.getTextList()) {
            logger.debug(dialogFormattedText.toString());
        }
    }

    private void debug(DialogFormattedResult formattedResult) {
        for (DialogFormattedText dialogFormattedText : formattedResult.getTextList()) {
            logger.debug(dialogFormattedText.toString());
        }
    }

}