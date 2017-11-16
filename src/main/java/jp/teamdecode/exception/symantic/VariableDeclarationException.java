package jp.teamdecode.exception.symantic;

public class VariableDeclarationException extends SemanticException {
    public VariableDeclarationException(String message) {
        super(message);
    }

    public VariableDeclarationException(String message, Throwable cause) {
        super(message, cause);
    }

    public VariableDeclarationException(Throwable cause) {
        super(cause);
    }

    public VariableDeclarationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
