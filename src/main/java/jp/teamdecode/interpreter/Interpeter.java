package jp.teamdecode.interpreter;

import jp.teamdecode.ast.*;
import jp.teamdecode.ast.visitor.NodeVisitor;
import jp.teamdecode.exception.InternalException;
import jp.teamdecode.exception.InterpreterException;
import jp.teamdecode.exception.LexerException;
import jp.teamdecode.exception.ParserException;
import jp.teamdecode.lexer.Token;
import jp.teamdecode.parser.Parser;

import java.util.HashMap;
import java.util.Map;

import static jp.teamdecode.lexer.Token.Type.*;

public class Interpeter extends NodeVisitor {
    private final Map<String, Object> GLOBAL_SCOPE = new HashMap<>();
    private final Parser parser;

    public Interpeter(Parser parser) {
        this.parser = parser;
    }

    public Object interpret() throws LexerException, ParserException, InternalException, InterpreterException {
        return visit(parser.parse());
    }

    public void visitProgram(AST node) throws InternalException, InterpreterException {
        Program program = (Program) node;
        visit(program.getBlock());
    }

    public void visitBlock(AST node) throws InternalException, InterpreterException {
        Block block = (Block) node;
        for (AST declaration : block.getDeclarations())
            visit(declaration);
        visit(block.getCompoundStatement());
    }

    public void visitCompound(AST node) throws InternalException, InterpreterException {
        Compound compound = (Compound) node;
        for (AST child : compound.getChildren()) {
            visit(child);
        }
    }

    public void visitNoOp(AST node) {
        // dp nothing
    }

    public void visitAssign(AST node) throws InternalException, InterpreterException {
        Assign assign = (Assign) node;
        String varName = ((Var) assign.getLeft()).getValue().toString();
        GLOBAL_SCOPE.put(varName, visit(assign.getRight()));
    }

    public Object visitVar(AST node) throws InterpreterException {
        Var var = (Var) node;
        String varName = var.getValue().toString();
        if (GLOBAL_SCOPE.containsKey(varName)) {
            return GLOBAL_SCOPE.get(varName);
        } else throw nameError(varName);
    }

    public Object visitNum(AST node) throws InterpreterException {
        Num num = (Num) node;
        if (num.getToke().getType() == INTEGER_CONST) {
            return Integer.parseInt(num.getValue().toString());
        } else if (num.getToke().getType() == REAL_CONST) {
            return Float.parseFloat(num.getValue().toString());
        } else throw error(node, "Unknown number type " + num.getValue());
    }

    public Object visitUnaryOp(AST node) throws InternalException, InterpreterException {
        UnaryOp unaryOp = (UnaryOp) node;
        Token.Type type = unaryOp.getOp().getType();
        if (type == PLUS) {
            Object result = visit(unaryOp.getExpr());
            if (result instanceof Integer) {
                return +(int) result;
            } else if (result instanceof Double || result instanceof Float)
                return +(float) result;
            else throw error(node, "Unsupported type " + result);
        } else if (type == MINUS) {
            Object result = visit(unaryOp.getExpr());
            if (result instanceof Integer) {
                return -(int) result;
            } else if (result instanceof Double || result instanceof Float)
                return -(float) result;
            else throw error(node, "Unsupported type " + result);
        } else throw error(node, "Unknown unary operation type: " + unaryOp.getOp().getValue());
    }

    public Object visitBinOp(AST node) throws InternalException, InterpreterException {
        BinOp binOp = ((BinOp) node);
        if (binOp.getOp().getType() == PLUS) {
            Object left = visit(binOp.getLeft());
            Object right = visit(binOp.getRight());
            if (left instanceof Float || right instanceof Float) {
                return ((double) left + (double) right);
            } else {
                return ((int) left + (int) right);
            }
        } else if (binOp.getOp().getType() == MINUS) {
            Object left = visit(binOp.getLeft());
            Object right = visit(binOp.getRight());
            if (left instanceof Float || right instanceof Float) {
                return ((double) left - (double) right);
            } else {
                return ((int) left - (int) right);
            }
        } else if (binOp.getOp().getType() == MUL) {
            Object left = visit(binOp.getLeft());
            Object right = visit(binOp.getRight());
            if (left instanceof Float || right instanceof Float) {
                return ((double) left * (double) right);
            } else {
                return ((int) left * (int) right);
            }
        } else if (binOp.getOp().getType() == INTEGER_DIV) {
            Object left = visit(binOp.getLeft());
            Object right = visit(binOp.getRight());
            return ((int) left) / ((int) right);
        } else if (binOp.getOp().getType() == FLOAT_DIV) {
            Object left = visit(binOp.getLeft());
            Object right = visit(binOp.getRight());
            return ((double) left * (double) right);
        } else throw error(node, "Unknown operation " + binOp.getOp().getValue());
    }

    private InterpreterException error(AST node, String msg) {
        return new InterpreterException("Error when visit " + node + "; " + msg);
    }

    private InterpreterException nameError(String varName) {
        return new InterpreterException("Variable not found " + varName);
    }
}
