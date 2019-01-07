package billy.rpg.common.formatter;

import java.awt.*;
import java.util.List;

/**
 * @author lei.liu@datatist.com
 * @since 2018-09-28 17:04:32
 */
public class DialogFormattedResult {
    private final int totalLine;
    private final List<DialogFormattedText> textList;

    public DialogFormattedResult(int totalLine, List<DialogFormattedText> textList) {
        this.totalLine = totalLine;
        this.textList = textList;
    }

    public int getTotalLine() {
        return totalLine;
    }

    public List<DialogFormattedText> getTextList() {
        return textList;
    }

    public static class DialogFormattedText {
        public final String content;
        public final Color color;
        public final int cnt;

        public DialogFormattedText(String content, Color color) {
            this.content = content;
            this.color = color;
            cnt = (content == null ? -1 : content.length());
        }

        @Override
        public String toString() {
            return content + "@(" + color.getRed() + "," + color.getGreen() + "," + color.getBlue() + ")";
        }
    }
}
