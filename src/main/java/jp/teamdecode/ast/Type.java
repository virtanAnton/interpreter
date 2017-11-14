package jp.teamdecode.ast;

import jp.teamdecode.lexer.Token;

public class Type extends AST {
    private final Token token;
    private final Object value;

    public Type(Token token) {
        this.token = token;
        this.value = token.getValue();
    }

    public Token getToken() {
        return token;
    }

    public Object getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Type{" +
                "token=" + token +
                ", value=" + value +
                '}';
    }
}
