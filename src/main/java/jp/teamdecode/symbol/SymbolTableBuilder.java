package jp.teamdecode.symbol;

import jp.teamdecode.ast.*;
import jp.teamdecode.ast.visitor.NodeVisitor;
import jp.teamdecode.exception.BaseInterpreterException;
import jp.teamdecode.exception.InternalException;
import jp.teamdecode.exception.InterpreterException;
import jp.teamdecode.exception.symantic.TypeDeclarationException;
import jp.teamdecode.exception.symantic.VariableDeclarationException;

public class SymbolTableBuilder extends NodeVisitor {
    private final SymbolTable symbolTable;

    public SymbolTableBuilder() {
        this.symbolTable = new SymbolTable();
    }

    public void buildTable(AST node) throws InternalException, InterpreterException {
        visit(node);
    }

    public void visitBlock(AST node) throws InternalException, InterpreterException {
        Block block = (Block) node;
        for (AST declaration : block.getDeclarations())
            visit(declaration);
        visit(block.getCompoundStatement());
    }

    public void visitProgram(AST node) throws InternalException, InterpreterException {
        Program program = (Program) node;
        visit(program.getBlock());
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

    public void visitAssign(AST node) throws TypeDeclarationException {
//        Assign assign = (Assign) node;
//        String varName = ((Var) assign.getLeft()).getValue().toString();
//        if (symbolTable.lookup(varName) == null) throw error(varName);
    }

    public void visitVar(AST node) throws TypeDeclarationException {
//        Var var = (Var) node;
//        String varName = var.getValue().toString();
//        if (symbolTable.lookup(varName) == null) throw error(varName);
    }

    public void visitVarDecl(AST node) throws TypeDeclarationException, VariableDeclarationException {
        VarDecl varDecl = (VarDecl) node;
        String typeName = ((Type) varDecl.getTypeNode()).getValue().toString();
        Symbol typeSymbol = symbolTable.lookup(typeName);
        if (typeSymbol == null) throw errorType(typeName);
        String varName = ((Var) varDecl.getVarNode()).getValue().toString();
        Symbol foundedSymbol = symbolTable.lookup(varName);
        if (foundedSymbol != null) throw errorVar(varName, foundedSymbol);
        symbolTable.define(new VarSymbol(varName, typeSymbol));
    }

    private TypeDeclarationException errorType(String type) {
        return new TypeDeclarationException("Type not found \'" + type + '\'');
    }

    private VariableDeclarationException errorVar(String varName, Symbol symbol) {
        return new VariableDeclarationException("Variable '" + varName + "' already defined: " + symbol);
    }

    public SymbolTable getSymbolTable() {
        return symbolTable;
    }
}
