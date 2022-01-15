package hashtools.core.exception;

/**
 * <p>
 * Exception that indicates offline status.
 * </p>
 *
 * @author Adriano Siqueira
 * @version 1.0.0
 * @since 2.0.0
 */
public class NoInternetConnectionException extends RuntimeException {

    public NoInternetConnectionException() {
    }

    public NoInternetConnectionException(String message) {
        super(message);
    }

    public NoInternetConnectionException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoInternetConnectionException(Throwable cause) {
        super(cause);
    }

    public NoInternetConnectionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
