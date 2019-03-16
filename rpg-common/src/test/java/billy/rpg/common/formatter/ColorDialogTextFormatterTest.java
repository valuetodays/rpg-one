package billy.rpg.common.formatter;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    public void testFormat() {
        String dialogText = "大师兄，师傅让我去取伏魔剑。据说伏魔洞中有`y`八位护剑兽`/y`，还有一位`y`护剑神`/y`，我怕取不出伏魔剑，所以……";
        DialogFormattedResult formattedResult = dialogTextFormatter.format(dialogText);
        int totalLine = formattedResult.getTotalLine();
        Assert.assertEquals(4, totalLine);
        for (DialogFormattedResult.DialogFormattedText dialogFormattedText : formattedResult.getTextList()) {
            logger.debug(dialogFormattedText.toString());
        }
    }

}