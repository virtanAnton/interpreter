package jp.teamdecode.ast;

import java.util.Arrays;

public class Block extends AST {
    private final AST[] declarations;
    private final AST compoundStatement;

    public Block(AST[] declarations, AST compoundStatement) {
        this.declarations = declarations;
        this.compoundStatement = compoundStatement;
    }

    public AST[] getDeclarations() {
        return declarations;
    }

    public AST getCompoundStatement() {
        return compoundStatement;
    }

    @Override
    public String toString() {
        return "Block{" +
                "declarations=" + Arrays.toString(declarations) +
                ", compoundStatement=" + compoundStatement +
                '}';
    }
}
