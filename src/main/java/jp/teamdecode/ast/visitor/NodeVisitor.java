package jp.teamdecode.ast.visitor;

import jp.teamdecode.ast.AST;
import jp.teamdecode.exception.InternalException;
import jp.teamdecode.exception.InterpreterException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class NodeVisitor {

    protected Object visit(AST node) throws InterpreterException, InternalException {
        try {
            String methodName = "visit" + node.getNodeName();
            Method method = this.getClass().getMethod(methodName, AST.class);
            return method.invoke(this, node);

        } catch (NoSuchMethodException | IllegalAccessException e) {
            throw error(node, e);
        } catch (InvocationTargetException e) {
            throw interpreterError(node, e);
        }
    }

    private InternalException error(AST ast, Throwable cause) {
        return new InternalException("Cant evaluate visit() for node: " + ast.getNodeName(), cause);
    }

    private InterpreterException interpreterError(AST ast, Throwable cause) {
        return new InterpreterException("Error during evaluate visit() for node: " + ast.getNodeName(), cause);
    }
}
