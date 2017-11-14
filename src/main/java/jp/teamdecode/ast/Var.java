package jp.teamdecode.ast;

import jp.teamdecode.lexer.Token;

public class Var extends AST {
    private final Token token;
    private final Object value;

    public Var(Token token) {
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
        return "Var{" +
                "token=" + token +
                ", value=" + value +
                '}';
    }
}
