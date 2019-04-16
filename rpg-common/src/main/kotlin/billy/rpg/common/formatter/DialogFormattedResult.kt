package billy.rpg.common.formatter

import java.awt.Color

/**
 * @author lei.liu
 * @since 2019-04-16 10:48
 */
data class DialogFormattedResult(val totalLine: Int, val textList: List<DialogFormattedText>)

data class DialogFormattedText(val content: String?, val color: Color) {
    val cnt: Int = content?.length ?: -1

    override fun toString(): String {
        return content + "@(" + color.red + "," + color.green + "," + color.blue + ")"
    }

    companion object {
        val NEW_LINE = DialogFormattedText(null, Color.WHITE)
    }
}
