package jp.teamdecode.analazer;

import jp.teamdecode.ast.*;
import jp.teamdecode.ast.visitor.NodeVisitor;
import jp.teamdecode.exception.InternalException;
import jp.teamdecode.exception.InterpreterException;
import jp.teamdecode.exception.symantic.TypeDeclarationException;
import jp.teamdecode.exception.symantic.VariableDeclarationException;
import jp.teamdecode.symbol.SymbolTable;
import jp.teamdecode.symbol.SymbolTableBuilder;

public class SemanticAnalyzer extends NodeVisitor {
    private SymbolTableBuilder symbolTableBuilder = new SymbolTableBuilder();

    public void analyze(AST ast) throws InterpreterException, InternalException {
        symbolTableBuilder.buildTable(ast);
        visit(ast);
    }

    public void visitProgram(AST node) throws InternalException, InterpreterException {
        Program program = (Program) node;
        visit(program.getBlock());
    }

    private SymbolTable getSymbolTable() {
        return symbolTableBuilder.getSymbolTable();
    }

    public void visitBlock(AST node) throws InternalException, InterpreterException {
        Block block = (Block) node;
        for (AST declaration : block.getDeclarations())
            visit(declaration);
        visit(block.getCompoundStatement());
    }


    public void visitProcedureDecl(AST node) throws InternalException, InterpreterException {

    }

    public void visitBinOp(AST node) throws InternalException, InterpreterException {
        BinOp binOp = (BinOp) node;
        visit(binOp.getLeft());
        visit(binOp.getRight());
    }

    public void visitNum(AST mode) {

    }

    public void visitUnaryOp(AST node) throws InternalException, InterpreterException {
        UnaryOp unaryOp = (UnaryOp) node;
        visit(unaryOp.getExpr());
    }

    public void visitCompound(AST node) throws InternalException, InterpreterException {
        Compound compound = (Compound) node;
        for (AST child : compound.getChildren()) {
            visit(child);
        }
    }

    public void visitNoOp(AST node) {

    }

    public void visitAssign(AST node) throws VariableDeclarationException, InternalException, InterpreterException {
        Assign assign = (Assign) node;
        visit(assign.getLeft());
        visit(assign.getRight());
    }

    public void visitVar(AST node) throws VariableDeclarationException {
        Var var = (Var) node;
        String varName = var.getValue().toString();
        if (getSymbolTable().lookup(varName) == null) throw errorVar(varName);
    }

    public void visitVarDecl(AST node) throws TypeDeclarationException {

    }

    private VariableDeclarationException errorVar(String varName) {
        return new VariableDeclarationException("Variable '" + varName + "' not declared");
    }

}
