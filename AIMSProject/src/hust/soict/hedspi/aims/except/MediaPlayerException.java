package hust.soict.hedspi.aims.except;

import java.io.Serial;

// Refactoring the class name to make it more descriptive
public class MediaPlayerException extends Exception {

    // Extracted constant with a meaningful name
    @Serial
    private static final long serialVersionUID = 1L;

    public MediaPlayerException() {
        super();
    }

    public MediaPlayerException(String message) {
        super(message);
    }

    public MediaPlayerException(Throwable cause) {
        super(cause);
    }

    public MediaPlayerException(String message, Throwable cause) {
        super(message, cause);
    }

    public MediaPlayerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}