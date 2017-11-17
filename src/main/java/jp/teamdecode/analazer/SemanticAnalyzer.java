package jp.teamdecode.analazer;

import jp.teamdecode.ast.*;
import jp.teamdecode.ast.visitor.NodeVisitor;
import jp.teamdecode.exception.InternalException;
import jp.teamdecode.exception.InterpreterException;
import jp.teamdecode.exception.symantic.TypeDeclarationException;
import jp.teamdecode.exception.symantic.VariableDeclarationException;
import jp.teamdecode.symbol.BuiltinTypeSymbol;
import jp.teamdecode.symbol.Symbol;
import jp.teamdecode.symbol.ScopedSymbolTable;
import jp.teamdecode.symbol.VarSymbol;

public class SemanticAnalyzer extends NodeVisitor {
    private ScopedSymbolTable scopedSymbolTable;

    public void analyze(AST ast) throws InterpreterException, InternalException {
        scopedSymbolTable = new ScopedSymbolTable(0, "DomainScope");
        scopedSymbolTable.define(new BuiltinTypeSymbol("INTEGER"));
        scopedSymbolTable.define(new BuiltinTypeSymbol("REAL"));
        visit(ast);
    }

    public void visitProgram(AST node) throws InternalException, InterpreterException {
        Program program = (Program) node;
        visit(program.getBlock());
    }

    public ScopedSymbolTable getScopedSymbolTable() {
        return scopedSymbolTable;
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
        if (getScopedSymbolTable().lookup(varName) == null) throw errorVar(varName);
    }

    public void visitVarDecl(AST node) throws TypeDeclarationException, VariableDeclarationException {
        VarDecl varDecl = (VarDecl) node;
        String typeName = ((Type) varDecl.getTypeNode()).getValue().toString();
        Symbol typeSymbol = scopedSymbolTable.lookup(typeName);
        if (typeSymbol == null) throw errorType(typeName);
        String varName = ((Var) varDecl.getVarNode()).getValue().toString();
        Symbol foundedSymbol = scopedSymbolTable.lookup(varName);
        if (foundedSymbol != null) throw errorVar(varName, foundedSymbol);
        scopedSymbolTable.define(new VarSymbol(varName, typeSymbol));
    }



    private VariableDeclarationException errorVar(String varName) {
        return new VariableDeclarationException("Variable '" + varName + "' not declared");
    }

    private TypeDeclarationException errorType(String type) {
        return new TypeDeclarationException("Type not found \'" + type + '\'');
    }

    private VariableDeclarationException errorVar(String varName, Symbol symbol) {
        return new VariableDeclarationException("Variable '" + varName + "' already defined: " + symbol);
    }

}
