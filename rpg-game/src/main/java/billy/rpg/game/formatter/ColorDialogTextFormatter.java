package billy.rpg.game.formatter;

import billy.rpg.game.constants.GameConstant;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lei.liu@datatist.com
 * @since 2018-09-28 16:52:57
 */
public class ColorDialogTextFormatter implements DialogTextFormatter {
    private static final int SEP = GameConstant.WORDS_NUM_PER_LINE;

    private List<DialogFormattedResult.DialogFormattedText> msgList; // 处理后的对话的内容
    private int totalLine; // 对话总共会显示多少行

    private void initMsgList() {
        msgList = new ArrayList<>();
        appendSeparator(Color.WHITE);
    }
    private void appendSeparator(Color color) {
        DialogFormattedResult.DialogFormattedText mNull = new DialogFormattedResult.DialogFormattedText(null, color);
        msgList.add(mNull);
        totalLine++;
    }
    /**
     * 处理对话内容，将颜色值提取出来
     */
    @Override
    public DialogFormattedResult format(String text) {
        List<DialogFormattedResult.DialogFormattedText> msgListTemp = dealTag(text);

        int cnt = 0;
        initMsgList();
        for (int i = 0; i < msgListTemp.size(); i++) {
            DialogFormattedResult.DialogFormattedText mc = msgListTemp.get(i);
            int mccnt = mc.cnt;
            String mccontent = mc.content;
            if (mccnt < SEP) {
                if (mccnt < SEP - cnt) {
                    msgList.add(mc);
                    cnt += mc.cnt;
                    if (i == msgListTemp.size() - 1) {
                        totalLine++;
//                        System.out.println("one");
                    }
                } else {
                    String pre = mccontent.substring(0, SEP - cnt);
                    DialogFormattedResult.DialogFormattedText mPre = new DialogFormattedResult.DialogFormattedText(pre, mc.color);
                    msgList.add(mPre);
                    appendSeparator(mc.color);
                    int n = pre.length();

                    DialogFormattedResult.DialogFormattedText mPost = new DialogFormattedResult.DialogFormattedText(mccontent.substring(n, mc.cnt), mc.color);
                    msgList.add(mPost);
                    cnt = mPost.cnt;
                }
            } else {
                String pre = mccontent.substring(0, SEP - cnt);
                DialogFormattedResult.DialogFormattedText mPre = new DialogFormattedResult.DialogFormattedText(pre, mc.color);
                msgList.add(mPre);
                appendSeparator(mc.color);

                int n = pre.length();
                while (mccnt > SEP) {
                    DialogFormattedResult.DialogFormattedText m = new DialogFormattedResult.DialogFormattedText(
                            mccontent.substring(n, Math.min(n + SEP, mc.cnt)),
                            mc.color);
                    msgList.add(m);
                    mccnt -= SEP;
                    n += SEP;
                    appendSeparator(mc.color);
                }
                if (n < mc.cnt) {
                    DialogFormattedResult.DialogFormattedText mPost = new DialogFormattedResult.DialogFormattedText(mccontent.substring(n, mc.cnt), mc.color);
                    msgList.add(mPost);
                    cnt = mPost.cnt;
                }
            }
        }

        return new DialogFormattedResult(totalLine, msgList);
    }

    private List<DialogFormattedResult.DialogFormattedText> dealTag(String msg) {
        List<DialogFormattedResult.DialogFormattedText> msgListTemp = new ArrayList<>();

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
                msgListTemp.add(new DialogFormattedResult.DialogFormattedText(bef, Color.WHITE));
                String tagEnd = tagBegin.substring(0, 1) + "/" + tagBegin.substring(1);
                int indexOf2 = msgTemp.indexOf(tagEnd, indexOf);
                String coloredMsg = msgTemp.substring(indexOf + tagBegin.length(), indexOf2);
                Color color = getColor(tagBegin);
                msgListTemp.add(new DialogFormattedResult.DialogFormattedText(coloredMsg, color));
                msgTemp = msgTemp.substring(indexOf2 + tagEnd.length());
            }
        }

        msgListTemp.add(new DialogFormattedResult.DialogFormattedText(msgTemp, Color.WHITE));

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
