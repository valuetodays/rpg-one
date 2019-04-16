package org.jline.reader;

public class EOFError extends SyntaxError {

    private static final long serialVersionUID = 1L;

    private final String missing;

    public EOFError(int line, int column, String message) {
        this(line, column, message, null);
    }

    public EOFError(int line, int column, String message, String missing) {
        super(line, column, message);
        this.missing = missing;
    }

    public String getMissing() {
        return missing;
    }
}
