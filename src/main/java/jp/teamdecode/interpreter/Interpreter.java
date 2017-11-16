package jp.teamdecode.interpreter;

import com.sun.org.apache.bcel.internal.generic.FLOAD;
import jp.teamdecode.ast.*;
import jp.teamdecode.ast.visitor.NodeVisitor;
import jp.teamdecode.exception.InternalException;
import jp.teamdecode.exception.InterpreterException;
import jp.teamdecode.exception.LexerException;
import jp.teamdecode.exception.ParserException;
import jp.teamdecode.lexer.Token;
import jp.teamdecode.parser.Parser;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static jp.teamdecode.lexer.Token.Type.*;

public class Interpreter extends NodeVisitor {
    private final Map<String, Object> GLOBAL_SCOPE = new HashMap<>();
    private final Parser parser;

    public Interpreter(Parser parser) {
        this.parser = parser;
    }

    public Object interpret() throws LexerException, ParserException, InternalException, InterpreterException {
        return visit(parser.parse());
    }

    public void visitProgram(AST node) throws InternalException, InterpreterException {
        Program program = (Program) node;
        visit(program.getBlock());
    }

    public void visitProcedureDecl(AST node) throws InternalException, InterpreterException {
        ProcedureDecl decl = (ProcedureDecl) node;

    }

    public void visitBlock(AST node) throws InternalException, InterpreterException {
        Block block = (Block) node;
        for (AST declaration : block.getDeclarations())
            visit(declaration);
        visit(block.getCompoundStatement());
    }

    public void visitVarDecl(AST node) {

    }

    public void visitType(AST node) {

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
        Number value = (Number) num.getValue();
        if (num.getToke().getType() == INTEGER_CONST) {
            return value;
        } else if (num.getToke().getType() == REAL_CONST) {
            return value;
        } else throw error(node, "Unknown number type " + num.getValue());
    }

    public Object visitUnaryOp(AST node) throws InternalException, InterpreterException {
        UnaryOp unaryOp = (UnaryOp) node;
        Token.Type type = unaryOp.getOp().getType();
        Number result = (Number) visit(unaryOp.getExpr());
        if (result instanceof Long) throw error(node, "Unsupported type " + result);
        if (type == PLUS) {
            if (result instanceof Float || result instanceof Double) {
                return +result.doubleValue();
            } else {
                return +result.intValue();
            }
        } else if (type == MINUS) {
            if (result instanceof Float || result instanceof Double) {
                return -result.doubleValue();
            } else {
                return -result.intValue();
            }
        } else throw error(node, "Unknown unary operation type: " + unaryOp.getOp().getValue());
    }

    public Object visitBinOp(AST node) throws InternalException, InterpreterException {
        BinOp binOp = ((BinOp) node);
        Number left = (Number) visit(binOp.getLeft());
        Number right = (Number) visit(binOp.getRight());
        if (binOp.getOp().getType() == PLUS) {
            if (left instanceof Float || left instanceof Double
                    || right instanceof Float || right instanceof Double) {
                return left.doubleValue() + right.doubleValue();
            } else {
                return left.intValue() + right.intValue();
            }
        } else if (binOp.getOp().getType() == MINUS) {
            if (left instanceof Float || left instanceof Double
                    || right instanceof Float || right instanceof Double) {
                return left.doubleValue() - right.doubleValue();
            } else {
                return left.intValue() - right.intValue();
            }
        } else if (binOp.getOp().getType() == MUL) {
            if (left instanceof Float || left instanceof Double
                    || right instanceof Float || right instanceof Double) {
                return left.doubleValue() * right.doubleValue();
            } else {
                return left.intValue() * right.intValue();
            }
        } else if (binOp.getOp().getType() == INTEGER_DIV) {
            return left.intValue() / right.intValue();
        } else if (binOp.getOp().getType() == FLOAT_DIV) {
            return left.doubleValue() / right.doubleValue();
        } else throw error(node, "Unknown operation " + binOp.getOp().getValue());
    }

    private InterpreterException error(AST node, String msg) {
        return new InterpreterException("Error when visit " + node + "; " + msg);
    }

    private InterpreterException nameError(String varName) {
        return new InterpreterException("Variable not found " + varName);
    }

    public Map<String, Object> getGlobalScope() {
        return Collections.unmodifiableMap(GLOBAL_SCOPE);
    }
}
