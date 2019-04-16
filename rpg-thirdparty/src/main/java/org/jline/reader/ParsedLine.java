package org.jline.reader;

import java.util.List;


public interface ParsedLine {

    /**
     * The current word being completed.
     * If the cursor is after the last word, an empty string is returned.
     *
     * @return the word being completed or an empty string
     */
    String word();

    /**
     * The cursor position within the current word
     */
    int wordCursor();

    /**
     * The index of the current word in the list of words
     */
    int wordIndex();

    /**
     * The list of words
     */
    List<String> words();

    /**
     * The unparsed line
     */
    String line();

    /**
     * The cursor position within the line
     */
    int cursor();

}

