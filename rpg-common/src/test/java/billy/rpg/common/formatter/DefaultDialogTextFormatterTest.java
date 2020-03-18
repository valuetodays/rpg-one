package billy.rpg.common.formatter;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.Color;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import billy.rpg.common.formatter.DialogFormattedResult.DialogFormattedText;

/**
 * 字数不满一行
 * 字数满一行
 * 字数不满一行，但有彩色字段
 * 字数满一行，但有彩色字段
 */
public class DefaultDialogTextFormatterTest {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final static int WORDS_NUM_PER_LINE = 20;

    private DialogTextFormatter dialogTextFormatter;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();


    @Before
    public void before() {
        dialogTextFormatter = new DefaultDialogTextFormatter(WORDS_NUM_PER_LINE);
    }

    @Test
    public void testFormat_whenTextLengthIsShort() {
        String dialogText = "还是先去见师傅吧。";
        DialogFormattedResult formattedResult = dialogTextFormatter.format(dialogText);
        int totalLine = formattedResult.getTotalLine();
        assertEquals(1, totalLine);

        List<DialogFormattedText> textList = formattedResult.getTextList();
        DialogFormattedText dialogFormattedText0 = textList.get(0);
        assertThat(dialogFormattedText0.getColor(), is(Color.WHITE));
        assertThat(dialogFormattedText0.getContent(), nullValue());
        DialogFormattedText dialogFormattedText1 = textList.get(1);
        assertThat(dialogFormattedText1.getColor(), is(Color.WHITE));
        assertThat(dialogFormattedText1.getContent(), is(dialogText));

        debug(formattedResult);
    }

    @Test
    public void testFormat_shouldThrowExceptionWhenErrorColorTagWith() {
        expectedException.expect(StringIndexOutOfBoundsException.class);
        String dialogText = "还是先去见师傅吧。`";
        dialogTextFormatter.format(dialogText);
    }

    @Test
    public void testFormat_shouldThrowExceptionWhenUncloseColorTag() {
        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage("unclose tag found!");
        String dialogText = "还是先去见`b`师傅吧。";
        dialogTextFormatter.format(dialogText);
    }

    @Test
    public void testFormat_whenTextLengthIsLong() {
        String dialogText = "还是先去见师傅去见师傅去见师傅去见师傅去见师傅去见师傅去见师傅去见师傅吧。";
        DialogFormattedResult formattedResult = dialogTextFormatter.format(dialogText);
        int totalLine = formattedResult.getTotalLine();
        assertEquals(2, totalLine);

        List<DialogFormattedText> textList = formattedResult.getTextList();
        DialogFormattedText dialogFormattedText0 = textList.get(0);
        assertThat(dialogFormattedText0.getColor(), is(Color.WHITE));
        assertThat(dialogFormattedText0.getContent(), nullValue());
        DialogFormattedText dialogFormattedText1 = textList.get(1);
        assertThat(dialogFormattedText1.getColor(), is(Color.WHITE));
        assertThat(dialogFormattedText1.getContent(), is("还是先去见师傅去见师傅去见师傅去见师傅去"));
        DialogFormattedText dialogFormattedText2 = textList.get(2);
        assertThat(dialogFormattedText2.getColor(), is(Color.WHITE));
        assertThat(dialogFormattedText2.getContent(), nullValue());
        DialogFormattedText dialogFormattedText3 = textList.get(3);
        assertThat(dialogFormattedText3.getColor(), is(Color.WHITE));
        assertThat(dialogFormattedText3.getContent(), is("见师傅去见师傅去见师傅去见师傅吧。"));

        debug(formattedResult);
    }

    @Test
    public void testFormat_whenTextLengthIsLong_withNumberOf_WORDS_NUM_PER_LINE() {
        String dialogText = "一二三四五六七八九十赵钱孙李周吴郑王冯陈一二三四五六七八九十赵钱孙李周吴郑王冯陈一二三四五六七八九十赵钱孙李周吴郑王冯陈";
        DialogFormattedResult formattedResult = dialogTextFormatter.format(dialogText);
        int totalLine = formattedResult.getTotalLine();
        assertEquals(3, totalLine);

        List<DialogFormattedText> textList = formattedResult.getTextList();
        DialogFormattedText dialogFormattedText0 = textList.get(0);
        assertThat(dialogFormattedText0.getColor(), is(Color.WHITE));
        assertThat(dialogFormattedText0.getContent(), nullValue());
        DialogFormattedText dialogFormattedText1 = textList.get(1);
        assertThat(dialogFormattedText1.getColor(), is(Color.WHITE));
        assertThat(dialogFormattedText1.getContent(), is("一二三四五六七八九十赵钱孙李周吴郑王冯陈"));
        DialogFormattedText dialogFormattedText2 = textList.get(2);
        assertThat(dialogFormattedText2.getColor(), is(Color.WHITE));
        assertThat(dialogFormattedText2.getContent(), nullValue());
        DialogFormattedText dialogFormattedText3 = textList.get(3);
        assertThat(dialogFormattedText3.getColor(), is(Color.WHITE));
        assertThat(dialogFormattedText3.getContent(), is("一二三四五六七八九十赵钱孙李周吴郑王冯陈"));
        DialogFormattedText dialogFormattedText4 = textList.get(4);
        assertThat(dialogFormattedText4.getColor(), is(Color.WHITE));
        assertThat(dialogFormattedText4.getContent(), nullValue());
        DialogFormattedText dialogFormattedText5 = textList.get(5);
        assertThat(dialogFormattedText5.getColor(), is(Color.WHITE));
        assertThat(dialogFormattedText5.getContent(), is("一二三四五六七八九十赵钱孙李周吴郑王冯陈"));

        debug(formattedResult);
    }

    @Test
    public void testFormat_whenTextLengthIsLonger() {
        String dialogText = "还是先去见师傅去见师傅去见师傅去见师傅去见师傅去见师傅去见师傅去见师傅去见师傅去见师傅去见师傅去见师傅去见师傅吧。";
        DialogFormattedResult formattedResult = dialogTextFormatter.format(dialogText);
        int totalLine = formattedResult.getTotalLine();
        assertEquals(3, totalLine);

        List<DialogFormattedText> textList = formattedResult.getTextList();
        DialogFormattedText dialogFormattedText0 = textList.get(0);
        assertThat(dialogFormattedText0.getColor(), is(Color.WHITE));
        assertThat(dialogFormattedText0.getContent(), nullValue());
        DialogFormattedText dialogFormattedText1 = textList.get(1);
        assertThat(dialogFormattedText1.getColor(), is(Color.WHITE));
        assertThat(dialogFormattedText1.getContent(), is("还是先去见师傅去见师傅去见师傅去见师傅去"));
        DialogFormattedText dialogFormattedText2 = textList.get(2);
        assertThat(dialogFormattedText2.getColor(), is(Color.WHITE));
        assertThat(dialogFormattedText2.getContent(), nullValue());
        DialogFormattedText dialogFormattedText3 = textList.get(3);
        assertThat(dialogFormattedText3.getColor(), is(Color.WHITE));
        assertThat(dialogFormattedText3.getContent(), is("见师傅去见师傅去见师傅去见师傅去见师傅去"));
        DialogFormattedText dialogFormattedText4 = textList.get(4);
        assertThat(dialogFormattedText4.getColor(), is(Color.WHITE));
        assertThat(dialogFormattedText4.getContent(), nullValue());
        DialogFormattedText dialogFormattedText5 = textList.get(5);
        assertThat(dialogFormattedText5.getColor(), is(Color.WHITE));
        assertThat(dialogFormattedText5.getContent(), is("见师傅去见师傅去见师傅去见师傅吧。"));

        debug(formattedResult);
    }


    @Test
    public void testFormat_whenTextLengthIsShortAndIsColored() {
        String dialogText = "还是先去见`y`师傅`/y`吧。";
        DialogFormattedResult formattedResult = dialogTextFormatter.format(dialogText);
        int totalLine = formattedResult.getTotalLine();
        assertEquals(1, totalLine);

        List<DialogFormattedText> textList = formattedResult.getTextList();
        DialogFormattedText dialogFormattedText0 = textList.get(0);
        assertThat(dialogFormattedText0.getColor(), is(Color.WHITE));
        assertThat(dialogFormattedText0.getContent(), nullValue());
        DialogFormattedText dialogFormattedText1 = textList.get(1);
        assertThat(dialogFormattedText1.getColor(), is(Color.WHITE));
        assertThat(dialogFormattedText1.getContent(), is("还是先去见"));
        DialogFormattedText dialogFormattedText2 = textList.get(2);
        assertThat(dialogFormattedText2.getColor(), is(Color.YELLOW));
        assertThat(dialogFormattedText2.getContent(), is("师傅"));
        DialogFormattedText dialogFormattedText3 = textList.get(3);
        assertThat(dialogFormattedText3.getColor(), is(Color.WHITE));
        assertThat(dialogFormattedText3.getContent(), is("吧。"));

        debug(formattedResult);
    }


    @Test
    public void testFormat_whenTextLengthIsShortAndIsDefaultColor() {
        String dialogText = "还是先去见`a`师傅`/a`吧。";
        DialogFormattedResult formattedResult = dialogTextFormatter.format(dialogText);
        int totalLine = formattedResult.getTotalLine();
        assertEquals(1, totalLine);

        List<DialogFormattedText> textList = formattedResult.getTextList();
        DialogFormattedText dialogFormattedText0 = textList.get(0);
        assertThat(dialogFormattedText0.getColor(), is(Color.WHITE));
        assertThat(dialogFormattedText0.getContent(), nullValue());
        DialogFormattedText dialogFormattedText1 = textList.get(1);
        assertThat(dialogFormattedText1.getColor(), is(Color.WHITE));
        assertThat(dialogFormattedText1.getContent(), is("还是先去见"));
        DialogFormattedText dialogFormattedText2 = textList.get(2);
        assertThat(dialogFormattedText2.getColor(), is(Color.BLACK));
        assertThat(dialogFormattedText2.getContent(), is("师傅"));
        DialogFormattedText dialogFormattedText3 = textList.get(3);
        assertThat(dialogFormattedText3.getColor(), is(Color.WHITE));
        assertThat(dialogFormattedText3.getContent(), is("吧。"));
    }


    @Test
    public void testFormat_whenTextLengthIsLongAndIsColored() {
        String dialogText = "还是`r`先去见`/r`师傅去见师傅去见师傅去见师傅去见师傅去见师傅去见师傅去见师傅吧。";
        DialogFormattedResult formattedResult = dialogTextFormatter.format(dialogText);
        int totalLine = formattedResult.getTotalLine();
        assertEquals(2, totalLine);

        List<DialogFormattedText> textList = formattedResult.getTextList();
        DialogFormattedText dialogFormattedText0 = textList.get(0);
        assertThat(dialogFormattedText0.getColor(), is(Color.WHITE));
        assertThat(dialogFormattedText0.getContent(), nullValue());
        DialogFormattedText dialogFormattedText1 = textList.get(1);
        assertThat(dialogFormattedText1.getColor(), is(Color.WHITE));
        assertThat(dialogFormattedText1.getContent(), is("还是"));
        DialogFormattedText dialogFormattedText2 = textList.get(2);
        assertThat(dialogFormattedText2.getColor(), is(Color.RED));
        assertThat(dialogFormattedText2.getContent(), is("先去见"));
        DialogFormattedText dialogFormattedText3 = textList.get(3);
        assertThat(dialogFormattedText3.getColor(), is(Color.WHITE));
        assertThat(dialogFormattedText3.getContent(), is("师傅去见师傅去见师傅去见师傅去"));
        DialogFormattedText dialogFormattedText4 = textList.get(4);
        assertThat(dialogFormattedText4.getColor(), is(Color.WHITE));
        assertThat(dialogFormattedText4.getContent(), nullValue());
        DialogFormattedText dialogFormattedText5 = textList.get(5);
        assertThat(dialogFormattedText5.getColor(), is(Color.WHITE));
        assertThat(dialogFormattedText5.getContent(), is("见师傅去见师傅去见师傅去见师傅吧。"));

        debug(formattedResult);
    }

    @Test
    public void testFormat_whenTextLengthIsLongAndIsColored_withLongColor() {
        String dialogText = "还是`g`先去见师傅去见师傅去见师傅去见师傅去见师傅去见师傅去见师傅去`/g`见师傅吧。";
        DialogFormattedResult formattedResult = dialogTextFormatter.format(dialogText);
        int totalLine = formattedResult.getTotalLine();
        assertEquals(2, totalLine);

        List<DialogFormattedText> textList = formattedResult.getTextList();
        DialogFormattedText dialogFormattedText0 = textList.get(0);
        assertThat(dialogFormattedText0.getColor(), is(Color.WHITE));
        assertThat(dialogFormattedText0.getContent(), nullValue());
        DialogFormattedText dialogFormattedText1 = textList.get(1);
        assertThat(dialogFormattedText1.getColor(), is(Color.WHITE));
        assertThat(dialogFormattedText1.getContent(), is("还是"));
        DialogFormattedText dialogFormattedText2 = textList.get(2);
        assertThat(dialogFormattedText2.getColor(), is(Color.GREEN));
        assertThat(dialogFormattedText2.getContent(), is("先去见师傅去见师傅去见师傅去见师傅去"));
        DialogFormattedText dialogFormattedText3 = textList.get(3);
        assertThat(dialogFormattedText3.getColor(), is(Color.WHITE));
        assertThat(dialogFormattedText3.getContent(), nullValue());
        DialogFormattedText dialogFormattedText4 = textList.get(4);
        assertThat(dialogFormattedText4.getColor(), is(Color.GREEN));
        assertThat(dialogFormattedText4.getContent(), is("见师傅去见师傅去见师傅去"));
        DialogFormattedText dialogFormattedText5 = textList.get(5);
        assertThat(dialogFormattedText5.getColor(), is(Color.WHITE));
        assertThat(dialogFormattedText5.getContent(), is("见师傅吧。"));

        debug(formattedResult);
    }

    @Test
    public void testFormat_whenTextLengthIsLongAndIsColored_withMultiColor() {
        String dialogText = "还是`g`先去见师傅去见师傅去见师傅去见师傅去见师傅去见师傅去见师傅去`/g`见`b`师傅`/b`吧。";
        DialogFormattedResult formattedResult = dialogTextFormatter.format(dialogText);
        int totalLine = formattedResult.getTotalLine();
        assertEquals(2, totalLine);

        List<DialogFormattedText> textList = formattedResult.getTextList();
        DialogFormattedText dialogFormattedText0 = textList.get(0);
        assertThat(dialogFormattedText0.getColor(), is(Color.WHITE));
        assertThat(dialogFormattedText0.getContent(), nullValue());
        DialogFormattedText dialogFormattedText1 = textList.get(1);
        assertThat(dialogFormattedText1.getColor(), is(Color.WHITE));
        assertThat(dialogFormattedText1.getContent(), is("还是"));
        DialogFormattedText dialogFormattedText2 = textList.get(2);
        assertThat(dialogFormattedText2.getColor(), is(Color.GREEN));
        assertThat(dialogFormattedText2.getContent(), is("先去见师傅去见师傅去见师傅去见师傅去"));
        DialogFormattedText dialogFormattedText3 = textList.get(3);
        assertThat(dialogFormattedText3.getColor(), is(Color.WHITE));
        assertThat(dialogFormattedText3.getContent(), nullValue());
        DialogFormattedText dialogFormattedText4 = textList.get(4);
        assertThat(dialogFormattedText4.getColor(), is(Color.GREEN));
        assertThat(dialogFormattedText4.getContent(), is("见师傅去见师傅去见师傅去"));
        DialogFormattedText dialogFormattedText5 = textList.get(5);
        assertThat(dialogFormattedText5.getColor(), is(Color.WHITE));
        assertThat(dialogFormattedText5.getContent(), is("见"));
        DialogFormattedText dialogFormattedText6 = textList.get(6);
        assertThat(dialogFormattedText6.getColor(), is(Color.BLUE));
        assertThat(dialogFormattedText6.getContent(), is("师傅"));
        DialogFormattedText dialogFormattedText7 = textList.get(7);
        assertThat(dialogFormattedText7.getColor(), is(Color.WHITE));
        assertThat(dialogFormattedText7.getContent(), is("吧。"));

        debug(formattedResult);
    }

    private void debug(DialogFormattedResult formattedResult) {
        for (DialogFormattedText dialogFormattedText : formattedResult.getTextList()) {
            logger.debug(dialogFormattedText.toString());
        }
    }

}