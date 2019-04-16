package billy.rpg.common.formatter

import java.awt.Color
import java.util.*
import java.util.stream.Collectors

/**
 * @author lei.liu
 * @since 2019-04-16 10:54
 */
class DefaultDialogTextFormatter(private val wordsNumPerLine: Int) : DialogTextFormatter {

    override fun format(text: String): DialogFormattedResult {
        val textListWithColor = processColorTag(text)
        val formattedTextListWithColor = processColorText(textListWithColor)

        return DialogFormattedResult(
                calcTotalLine(textListWithColor.stream().map { e -> e.content }.collect(Collectors.joining(""))),
                formattedTextListWithColor)
    }

    private fun processColorTag(msg: String): List<DialogFormattedText> {
        val msgListTemp = ArrayList<DialogFormattedText>()

        var msgTemp = msg
        while (true) {
            val colorTagPos = msgTemp.indexOf('`')
            if (colorTagPos == -1) {
                break
            }
            val tagBegin = msgTemp.substring(colorTagPos, colorTagPos + "`C`".length)
            val indexOf = msgTemp.indexOf(tagBegin)
            val bef = msgTemp.substring(0, indexOf)
            msgListTemp.add(DialogFormattedText(bef, Color.WHITE))
            val tagEnd = tagBegin.substring(0, 1) + "/" + tagBegin.substring(1)
            val indexOf2 = msgTemp.indexOf(tagEnd, indexOf)
            if (indexOf2 < 0) {
                throw RuntimeException("unclose tag found!")
            }
            val coloredMsg = msgTemp.substring(indexOf + tagBegin.length, indexOf2)
            val color = getColor(tagBegin)
            msgListTemp.add(DialogFormattedText(coloredMsg, color))
            msgTemp = msgTemp.substring(indexOf2 + tagEnd.length)
        }

        msgListTemp.add(DialogFormattedText(msgTemp, Color.WHITE))

        return msgListTemp
    }

    private fun getColor(tagName: String): Color {
        val flagName = tagName.toLowerCase()[1]
        if ('r' == flagName) {
            return Color.red
        }
        if ('b' == flagName) {
            return Color.blue
        }
        if ('g' == flagName) {
            return Color.green
        }
        return if ('y' == flagName) {
            Color.yellow
        } else Color.BLACK

    }

    private fun processColorText(
            textListWithColor: List<DialogFormattedText>): List<DialogFormattedText> {
        val processedColorMsgList = ArrayList<DialogFormattedText>()

        appendNewLine(processedColorMsgList)

        var currentOffset = 0
        for (textWithColor in textListWithColor) {
            val content = textWithColor.content
            val color = textWithColor.color
            val cnt = textWithColor.cnt

            if (currentOffset + cnt > wordsNumPerLine) {
                var start = 0
                var end = wordsNumPerLine - currentOffset
                currentOffset = 0
                while (start < content!!.length) {
                    if (content.length < end) {
                        end = content.length
                        currentOffset = end - start
                    }
                    val lineText = content.substring(start, end)
                    processedColorMsgList.add(DialogFormattedText(lineText, color))
                    if (currentOffset == 0) {
                        appendNewLine(processedColorMsgList)
                    }
                    start = end
                    end = start + wordsNumPerLine
                }

            } else {
                processedColorMsgList.add(DialogFormattedText(content, color))
                currentOffset += cnt
            }
        }

        return processedColorMsgList
    }

    private fun appendNewLine(processedColorMsgList: MutableList<DialogFormattedText>) {
        processedColorMsgList.add(DialogFormattedText.NEW_LINE)
    }

    private fun calcTotalLine(text: String): Int {
        return text.length / wordsNumPerLine + if (text.length % wordsNumPerLine > 0) 1 else 0
    }

}
