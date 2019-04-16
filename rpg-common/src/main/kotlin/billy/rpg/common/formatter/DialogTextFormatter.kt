package billy.rpg.common.formatter

/**
 * @author lei.liu
 * @since 2019-04-16 10:47
 */
interface DialogTextFormatter {
    fun format(text: String): DialogFormattedResult
}