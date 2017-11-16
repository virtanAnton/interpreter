package jp.teamdecode.ast;

public class ProcedureDecl extends AST {
    private final AST name;
    private final AST blockNode;

    public ProcedureDecl(AST name, AST block) {
        this.name = name;
        this.blockNode = block;
    }

    public AST getName() {
        return name;
    }

    public AST getBlockNode() {
        return blockNode;
    }

    @Override
    public String toString() {
        return "ProcedureDecl{" +
                "name=" + name +
                ", blockNode=" + blockNode +
                '}';
    }
}
