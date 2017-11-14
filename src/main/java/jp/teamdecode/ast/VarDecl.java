package jp.teamdecode.ast;

public class VarDecl extends AST {
    private final AST varNode;
    private final AST typeNode;

    public VarDecl(AST varNode, AST typeNode) {
        this.varNode = varNode;
        this.typeNode = typeNode;
    }

    public AST getVarNode() {
        return varNode;
    }

    public AST getTypeNode() {
        return typeNode;
    }

    @Override
    public String toString() {
        return "VarDecl{" +
                "varNode=" + varNode +
                ", typeNode=" + typeNode +
                '}';
    }
}
