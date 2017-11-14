package jp.teamdecode.ast;

import java.util.Arrays;

public class Compound extends AST {
    private final AST[] children;

    public Compound(AST[] children) {
        this.children = children;
    }

    public AST[] getChildren() {
        return children;
    }

    @Override
    public String toString() {
        return "Compound{" +
                "children=" + Arrays.toString(children) +
                '}';
    }
}
