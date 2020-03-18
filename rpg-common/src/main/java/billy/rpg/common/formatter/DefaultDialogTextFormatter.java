package billy.rpg.common.formatter;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import billy.rpg.common.formatter.DialogFormattedResult.DialogFormattedText;

/**
 *
 * @author lei.liu
 * @update 2020-03-18 11:44 convert from kotlin
 * @since 2019-04-16 10:54
 */
public class DefaultDialogTextFormatter implements DialogTextFormatter {
    private int wordsNumPerLine;

    public DefaultDialogTextFormatter(int wordsNumPerLine) {
        this.wordsNumPerLine = wordsNumPerLine;
    }

    @Override
    public DialogFormattedResult format(String text) {
        List<DialogFormattedText> textListWithColor = processColorTag(text);
        List<DialogFormattedText> formattedTextListWithColor = processColorText(textListWithColor);

        return new DialogFormattedResult(
            calcTotalLine(textListWithColor.stream().map (DialogFormattedText::getContent).collect(Collectors.joining(""))),
            formattedTextListWithColor
        );
    }

    private List<DialogFormattedText> processColorTag(String msg) {
        List<DialogFormattedText> msgListTemp = new ArrayList<>();

        String msgTemp = msg;
        while (true) {
            int colorTagPos = msgTemp.indexOf('`');
            if (colorTagPos == -1) { // 没有颜色标志
                break;
            }
            String tagBegin = msgTemp.substring(colorTagPos, colorTagPos + "`C`".length());
            int indexOf = msgTemp.indexOf(tagBegin);
            String bef = msgTemp.substring(0, indexOf);
            msgListTemp.add(new DialogFormattedText(bef, Color.WHITE));
            String tagEnd = tagBegin.substring(0, 1) + "/" + tagBegin.substring(1);
            int indexOf2 = msgTemp.indexOf(tagEnd, indexOf);
            if (indexOf2 < 0) {
                throw new RuntimeException("unclose tag found!");
            }
            String coloredMsg = msgTemp.substring(indexOf + tagBegin.length(), indexOf2);
            Color color = getColor(tagBegin);
            msgListTemp.add(new DialogFormattedText(coloredMsg, color));
            msgTemp = msgTemp.substring(indexOf2 + tagEnd.length());
        }

        msgListTemp.add(new DialogFormattedText(msgTemp, Color.WHITE));

        return msgListTemp;
    }

    private Color getColor(String tagName) {
        char flagName = tagName.toLowerCase().charAt(1);
        if ('r' == flagName) {
            return Color.RED;
        }
        if ('b' == flagName) {
            return Color.BLUE;
        }
        if ('g' == flagName) {
            return Color.GREEN;
        }
        if ('y' == flagName) {
            return Color.YELLOW;
        }

        return Color.BLACK;
    }

    private List<DialogFormattedText> processColorText(List<DialogFormattedText> textListWithColor)  {
        List<DialogFormattedText> processedColorMsgList = new ArrayList<>();

        appendNewLine(processedColorMsgList);

        int currentOffset = 0;
        for (DialogFormattedText textWithColor : textListWithColor) {
            String content = textWithColor.getContent();
            Color color = textWithColor.getColor();
            int cnt = textWithColor.getCnt();

            if (currentOffset + cnt > wordsNumPerLine) {
                int start = 0;
                int end = wordsNumPerLine - currentOffset;
                currentOffset = 0;
                while (start < content.length()) {
                    if (content.length() < end) {
                        end = content.length();
                        currentOffset = end - start;
                    }
                    String lineText = content.substring(start, end);
                    processedColorMsgList.add(new DialogFormattedText(lineText, color));
                    if (currentOffset == 0) {
                        appendNewLine(processedColorMsgList);
                    }
                    start = end;
                    end = start + wordsNumPerLine;
                }

            } else {
                processedColorMsgList.add(new DialogFormattedText(content, color));
                currentOffset += cnt;
            }
        }

        return processedColorMsgList;
    }

    private void appendNewLine(List<DialogFormattedText> processedColorMsgList) {
        processedColorMsgList.add(DialogFormattedText.NEW_LINE);
    }

    private int calcTotalLine(String text) {
        return text.length() / wordsNumPerLine +  ((text.length() % wordsNumPerLine > 0) ? 1 : 0);
    }

}
