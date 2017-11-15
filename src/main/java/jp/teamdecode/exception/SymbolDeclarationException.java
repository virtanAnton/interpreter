package jp.teamdecode.exception;

public class SymbolDeclarationException extends BaseInterpreterException {
    public SymbolDeclarationException(String message) {
        super(message);
    }

    public SymbolDeclarationException(String message, Throwable cause) {
        super(message, cause);
    }

    public SymbolDeclarationException(Throwable cause) {
        super(cause);
    }

    public SymbolDeclarationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
