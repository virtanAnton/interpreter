package jp.teamdecode.ast;

import jp.teamdecode.lexer.Token;

public class Assign extends AST {
    private final AST left;
    private final Token op;
    private final AST right;

    public Assign(AST left, Token op, AST right) {
        this.left = left;
        this.op = op;
        this.right = right;
    }

    public AST getLeft() {
        return left;
    }

    public Token getOp() {
        return op;
    }

    public AST getRight() {
        return right;
    }

    @Override
    public String toString() {
        return "Assign{" +
                "left=" + left +
                ", op=" + op +
                ", right=" + right +
                '}';
    }
}
