package org.jline.reader;

/**
 * @author lei.liu
 * @since 2019-04-16 10:28
 */

public interface Parser {

    ParsedLine parse(String line, int cursor, ParseContext context) throws SyntaxError;

    default ParsedLine parse(String line, int cursor) throws SyntaxError {
        return parse(line, cursor, ParseContext.UNSPECIFIED);
    }

    enum ParseContext {
        UNSPECIFIED,

        /** Try a real "final" parse.
         * May throw EOFError in which case we have incomplete input.
         */
        ACCEPT_LINE,

        /** Parse to find completions (typically after a Tab).
         * We should tolerate and ignore errors.
         */
        COMPLETE,

        /** Called when we need to update the secondary prompts.
         * Specifically, when we need the 'missing' field from EOFError,
         * which is used by a "%M" in a prompt pattern.
         */
        SECONDARY_PROMPT
    }
}