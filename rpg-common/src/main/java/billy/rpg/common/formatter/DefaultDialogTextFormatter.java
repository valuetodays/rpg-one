package billy.rpg.common.formatter;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DefaultDialogTextFormatter implements DialogTextFormatter {
    private int wordsNumPerLine;

    public DefaultDialogTextFormatter(int wordsNumPerLine) {
        this.wordsNumPerLine = wordsNumPerLine;
    }

    private List<DialogFormattedResult.DialogFormattedText> processColorTag(String msg) {
        List<DialogFormattedResult.DialogFormattedText> msgListTemp = new ArrayList<>();

        String msgTemp = msg;
        while (true) {
            int colorTagPos = msgTemp.indexOf('`');
            if (colorTagPos == -1) {
                break;
            }
            String tagBegin = msgTemp.substring(colorTagPos, colorTagPos + "`C`".length());
            int indexOf = msgTemp.indexOf(tagBegin);
            String bef = msgTemp.substring(0, indexOf);
            msgListTemp.add(new DialogFormattedResult.DialogFormattedText(bef, Color.WHITE));
            String tagEnd = tagBegin.substring(0, 1) + "/" + tagBegin.substring(1);
            int indexOf2 = msgTemp.indexOf(tagEnd, indexOf);
            if (indexOf2 < 0) {
                throw new RuntimeException("unclose tag found!");
            }
            String coloredMsg = msgTemp.substring(indexOf + tagBegin.length(), indexOf2);
            Color color = getColor(tagBegin);
            msgListTemp.add(new DialogFormattedResult.DialogFormattedText(coloredMsg, color));
            msgTemp = msgTemp.substring(indexOf2 + tagEnd.length());
        }

        msgListTemp.add(new DialogFormattedResult.DialogFormattedText(msgTemp, Color.WHITE));

        return msgListTemp;
    }


    private Color getColor(String tagName) {
        char flagName = tagName.toLowerCase().charAt(1);
        if ('r' == flagName) {
            return Color.red;
        }
        if ('b' == flagName) {
            return Color.blue;
        }
        if ('g' == flagName) {
            return Color.green;
        }
        if ('y' == flagName) {
            return Color.yellow;
        }

        return Color.BLACK;
    }

    @Override
    public DialogFormattedResult format(String text) {
        List<DialogFormattedResult.DialogFormattedText> textListWithColor = processColorTag(text);
        List<DialogFormattedResult.DialogFormattedText> formattedTextListWithColor = processColorText(textListWithColor);

        return new DialogFormattedResult(
                        calcTotalLine(textListWithColor.stream().map(e -> e.content).collect(Collectors.joining(""))),
                formattedTextListWithColor);
    }

    private List<DialogFormattedResult.DialogFormattedText> processColorText(
            List<DialogFormattedResult.DialogFormattedText> textListWithColor) {
        List<DialogFormattedResult.DialogFormattedText> processedColorMsgList = new ArrayList<>();

        appendNewLine(processedColorMsgList);

        int currentOffset = 0;
        for (DialogFormattedResult.DialogFormattedText textWithColor : textListWithColor) {
            String content = textWithColor.content;
            Color color = textWithColor.color;
            int cnt = textWithColor.cnt;

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
                    processedColorMsgList.add(new DialogFormattedResult.DialogFormattedText(lineText, color));
                    if (currentOffset == 0) {
                        appendNewLine(processedColorMsgList);
                    }
                    start = end;
                    end = start + wordsNumPerLine;
                }

            } else {
                processedColorMsgList.add(new DialogFormattedResult.DialogFormattedText(content, color));
                currentOffset += cnt;
            }
        }

        return processedColorMsgList;
    }

    private void appendNewLine(List<DialogFormattedResult.DialogFormattedText> processedColorMsgList) {
        processedColorMsgList.add(DialogFormattedResult.DialogFormattedText.NEW_LINE);
    }

    private int calcTotalLine(String text) {
        return text.length() / wordsNumPerLine + (text.length() % wordsNumPerLine > 0 ? 1 : 0);
    }
}
