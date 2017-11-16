package jp.teamdecode.exception.symantic;

import jp.teamdecode.exception.BaseInterpreterException;

public abstract class SemanticException extends BaseInterpreterException {


    public SemanticException(String message) {
        super(message);
    }

    public SemanticException(String message, Throwable cause) {
        super(message, cause);
    }

    public SemanticException(Throwable cause) {
        super(cause);
    }

    public SemanticException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
