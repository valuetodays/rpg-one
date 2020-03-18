package billy.rpg.common.formatter;

import java.util.List;
import java.awt.Color;

public class DialogFormattedResult {

    public static class DialogFormattedText {
        public static final DialogFormattedText NEW_LINE = new DialogFormattedText(null, Color.WHITE);

        private String content;
        private Color color;
        private int cnt;

        public DialogFormattedText(String content, Color color) {
            this.content = content;
            this.color = color;
            cnt = content != null ? content.length() : -1;
        }

        public String getContent() {
            return content;
        }

        public Color getColor() {
            return color;
        }

        public int getCnt() {
            return cnt;
        }

        @Override
        public String toString() {
            return content + "@(" + color.getRed() + "," + color.getGreen() + "," + color.getBlue() + ")";
        }
    }

    private int totalLine;
    private List<DialogFormattedText> textList;

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
}
