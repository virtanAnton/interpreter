package jp.teamdecode.exception;

public abstract class BaseInterpreterException extends Exception {
    public BaseInterpreterException() {
    }

    public BaseInterpreterException(String message) {
        super(message);
    }

    public BaseInterpreterException(String message, Throwable cause) {
        super(message, cause);
    }

    public BaseInterpreterException(Throwable cause) {
        super(cause);
    }

    public BaseInterpreterException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
