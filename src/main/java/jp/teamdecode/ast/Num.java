package jp.teamdecode.ast;

import jp.teamdecode.lexer.Token;

public class Num extends AST {
    private final Token toke;
    private final Object value;

    public Num(Token toke) {
        this.toke = toke;
        this.value = toke.getValue();
    }

    public Token getToke() {
        return toke;
    }

    public Object getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Num{" +
                "toke=" + toke +
                ", value=" + value +
                '}';
    }
}
