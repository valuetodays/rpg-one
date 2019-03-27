package billy.rpg.common.exception;

/**
 *
 * @author lei.liu
 * @since 2018-11-12 18:03:42
 */
public class UnFinishException extends RuntimeException {
    private static final long serialVersionUID = -3124723866246291222L;

    public UnFinishException() {
    }

    public UnFinishException(String message) {
        super(message);
    }

    public UnFinishException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnFinishException(Throwable cause) {
        super(cause);
    }

    public UnFinishException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
