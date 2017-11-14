package jp.teamdecode.ast;

import jp.teamdecode.lexer.Token;

public class Program extends AST {
    private final AST name;
    private final AST block;

    public Program(AST name, AST block) {
        this.name = name;
        this.block = block;
    }

    public AST getName() {
        return name;
    }

    public AST getBlock() {
        return block;
    }

    @Override
    public String toString() {
        return "Program{" +
                "name='" + name + '\'' +
                ", block=" + block +
                '}';
    }
}
