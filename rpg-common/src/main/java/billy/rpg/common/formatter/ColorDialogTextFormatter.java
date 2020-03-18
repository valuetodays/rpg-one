package billy.rpg.common.formatter;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import billy.rpg.common.formatter.DialogFormattedResult.DialogFormattedText;

/**
 * this class can not work well when text is
 * <code>'师兄，我知道了，m的值等于100，厉害吧，我还知道`y`你的国家`/y`是zh。'</code>
 * it's consequence is
 * <pre>
 *     师兄，我知道了，m的值等于100，厉害吧
 *     ，我还知道
 *     的国家是zh。
 * </pre>
 * rather than
 * <pre>
 *     师兄，我知道了，m的值等于100，厉害吧
 *     ，我还知道的国家是zh。
 * </pre>
 *
 * and it's ok in {@link DefaultDialogTextFormatter}
 *
 * @author lei.liu
 * @since 2018-09-28 16:52:57
 *
 * @see DefaultDialogTextFormatter
 * @version v20190412
 */
@Deprecated
public class ColorDialogTextFormatter implements DialogTextFormatter {
    private final int WORDS_NUM_PER_LINE;

    private List<DialogFormattedText> msgList; // 处理后的对话的内容
    private int totalLine; // 对话总共会显示多少行

    public ColorDialogTextFormatter(int wordsNumPerLine) {
        this.WORDS_NUM_PER_LINE = wordsNumPerLine;
    }

    private void initMsgList() {
        msgList = new ArrayList<>();
        appendSeparator(Color.WHITE);
    }
    private void appendSeparator(Color color) {
        DialogFormattedText mNull = new DialogFormattedText(null, color);
        msgList.add(mNull);
        totalLine++;
    }
    /**
     * 处理对话内容，将颜色值提取出来
     */
    @Override
    public DialogFormattedResult format(String text) {
        List<DialogFormattedText> msgListTemp = dealTag(text);

        int cnt = 0;
        initMsgList();
        for (int i = 0; i < msgListTemp.size(); i++) {
            DialogFormattedText mc = msgListTemp.get(i);
            int mccnt = mc.getCnt();
            String mccontent = mc.getContent();
            if (mccnt < WORDS_NUM_PER_LINE) {
                if (mccnt < WORDS_NUM_PER_LINE - cnt) {
                    msgList.add(mc);
                    cnt += mc.getCnt();
                    if (i == msgListTemp.size() - 1) {
                        totalLine++;
//                        System.out.println("one");
                    }
                } else {
                    String pre = mccontent.substring(0, WORDS_NUM_PER_LINE - cnt);
                    DialogFormattedText mPre = new DialogFormattedText(pre, mc.getColor());
                    msgList.add(mPre);
                    appendSeparator(mc.getColor());
                    int n = pre.length();

                    DialogFormattedText mPost = new DialogFormattedText(mccontent.substring(n, mc.getCnt()), mc.getColor());
                    msgList.add(mPost);
                    cnt = mPost.getCnt();
                }
            } else {
                String pre = mccontent.substring(0, WORDS_NUM_PER_LINE - cnt);
                DialogFormattedText mPre = new DialogFormattedText(pre, mc.getColor());
                msgList.add(mPre);
                appendSeparator(mc.getColor());

                int n = pre.length();
                while (mccnt > WORDS_NUM_PER_LINE) {
                    DialogFormattedText m = new DialogFormattedText(
                            mccontent.substring(n, Math.min(n + WORDS_NUM_PER_LINE, mc.getCnt())),
                            mc.getColor());
                    msgList.add(m);
                    mccnt -= WORDS_NUM_PER_LINE;
                    n += WORDS_NUM_PER_LINE;
                    appendSeparator(mc.getColor());
                }
                if (n < mc.getCnt()) {
                    DialogFormattedText mPost = new DialogFormattedText(mccontent.substring(n, mc.getCnt()), mc.getColor());
                    msgList.add(mPost);
                    cnt = mPost.getCnt();
                }
            }
        }

        totalLine--;
        return new DialogFormattedResult(totalLine, msgList);
    }

    private List<DialogFormattedText> dealTag(String msg) {
        List<DialogFormattedText> msgListTemp = new ArrayList<>();

        String msgTemp = msg;
        while (true) {
            int colorTagPos = msgTemp.indexOf('`');
            if (colorTagPos == -1) {
                break;
            }
            String tagBegin = msgTemp.substring(colorTagPos, colorTagPos + 3);
            int indexOf = msgTemp.indexOf(tagBegin);
            if (indexOf > -1) {
                String bef = msgTemp.substring(0, indexOf);
                msgListTemp.add(new DialogFormattedText(bef, Color.WHITE));
                String tagEnd = tagBegin.substring(0, 1) + "/" + tagBegin.substring(1);
                int indexOf2 = msgTemp.indexOf(tagEnd, indexOf);
                String coloredMsg = msgTemp.substring(indexOf + tagBegin.length(), indexOf2);
                Color color = getColor(tagBegin);
                msgListTemp.add(new DialogFormattedText(coloredMsg, color));
                msgTemp = msgTemp.substring(indexOf2 + tagEnd.length());
            }
        }

        msgListTemp.add(new DialogFormattedText(msgTemp, Color.WHITE));

        return msgListTemp;
    }

    private Color getColor(String tagName) {
        char flagName = tagName.charAt(1);
        if ('r' == flagName || 'R' == flagName) {
            return Color.red;
        }
        if ('b' == flagName || 'B' == flagName) {
            return Color.blue;
        }
        if ('g' == flagName || 'G' == flagName) {
            return Color.green;
        }
        if ('y' == flagName || 'Y' == flagName) {
            return Color.yellow;
        } else {
            return Color.BLACK;
        }
    }
}
