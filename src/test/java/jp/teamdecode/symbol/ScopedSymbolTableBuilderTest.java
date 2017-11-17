package jp.teamdecode.symbol;

import jp.teamdecode.ast.AST;
import jp.teamdecode.exception.InterpreterException;
import jp.teamdecode.exception.symantic.VariableDeclarationException;
import jp.teamdecode.interpreter.Interpreter;
import jp.teamdecode.lexer.Lexer;
import jp.teamdecode.parser.Parser;
import org.junit.Test;

import static org.junit.Assert.*;

public class ScopedSymbolTableBuilderTest {
    private final String REAL = "REAL";
    private final String INTEGER = "INTEGER";

    @Test
    public void lesson11() throws Exception {
        String text = "PROGRAM Part11;\n" +
                "VAR\n" +
                "   number : INTEGER;\n" +
                "   a, b   : INTEGER;\n" +
                "   y      : REAL;\n" +
                "\n" +
                "BEGIN {Part11}\n" +
                "   number := 2;\n" +
                "   a := number ;\n" +
                "   b := 10 * a + 10 * number DIV 4;\n" +
                "   y := 20 / 7 + 3.14\n" +
                "END.  {Part11}";
        Lexer lexer = new Lexer(text);
        Parser parser = new Parser(lexer);

        SymbolTableBuilder symbolTableBuilder = new SymbolTableBuilder();
        AST ast = parser.parse();
        symbolTableBuilder.buildTable(ast);

        Interpreter interpreter = new Interpreter(parser);
        interpreter.interpret();

        ScopedSymbolTable scopedSymbolTable = symbolTableBuilder.getScopedSymbolTable();
        System.out.println(scopedSymbolTable);
        System.out.println(interpreter.getGlobalScope());
        assertEquals(INTEGER, scopedSymbolTable.lookup("number").type.name);
        assertEquals(INTEGER, scopedSymbolTable.lookup("a").type.name);
        assertEquals(INTEGER, scopedSymbolTable.lookup("b").type.name);
        assertEquals(REAL, scopedSymbolTable.lookup("y").type.name);
    }

    @Test
    public void declarationCheck() throws Exception {
        String text = "program SymTab5;\n" +
                "    var x : integer;\n" +
                "     x : integer;\n" +
                "\n" +
                "begin\n" +
                "    x := 5;\n" +
                "end.";
        Lexer lexer = new Lexer(text);
        Parser parser = new Parser(lexer);
        SymbolTableBuilder builder = new SymbolTableBuilder();

        Throwable error = null;
        try {
            builder.buildTable(parser.parse());
        } catch (InterpreterException e) {
            error = e;
            while (error.getCause() != null) error = error.getCause();
        }
        assertTrue(error instanceof VariableDeclarationException);
        assertEquals("Variable 'x' already defined: <x:INTEGER>", error.getMessage());
    }
}