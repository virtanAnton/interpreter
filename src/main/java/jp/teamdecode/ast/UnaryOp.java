package jp.teamdecode.ast;

import jp.teamdecode.lexer.Token;

public class UnaryOp extends AST {
    private final Token op;
    private final AST expr;

    public UnaryOp(Token op, AST expr) {
        this.op = op;
        this.expr = expr;
    }

    public Token getOp() {
        return op;
    }

    public AST getExpr() {
        return expr;
    }

    @Override
    public String toString() {
        return "UnaryOp{" +
                "op=" + op +
                ", expr=" + expr +
                '}';
    }
}
