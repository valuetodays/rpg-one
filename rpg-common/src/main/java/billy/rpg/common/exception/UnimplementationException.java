package billy.rpg.common.exception;

/**
 *
 * @author lei.liu@datatist.com
 * @since 2018-11-12 18:03:42
 */
public class UnimplementationException extends RuntimeException {
    private static final long serialVersionUID = -3124723866246291222L;

    public UnimplementationException() {
    }

    public UnimplementationException(String message) {
        super(message);
    }

    public UnimplementationException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnimplementationException(Throwable cause) {
        super(cause);
    }

    public UnimplementationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
