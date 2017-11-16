package jp.teamdecode.exception.symantic;

public class TypeDeclarationException extends SemanticException {
    public TypeDeclarationException(String message) {
        super(message);
    }

    public TypeDeclarationException(String message, Throwable cause) {
        super(message, cause);
    }

    public TypeDeclarationException(Throwable cause) {
        super(cause);
    }

    public TypeDeclarationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
