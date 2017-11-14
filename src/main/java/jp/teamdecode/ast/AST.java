package jp.teamdecode.ast;

public abstract class AST {

    public String getNodeName() {
        return this.getClass().getSimpleName();
    }
}
