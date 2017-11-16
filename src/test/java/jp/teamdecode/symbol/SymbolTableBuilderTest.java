package jp.teamdecode.symbol;

import jp.teamdecode.ast.AST;
import jp.teamdecode.interpreter.Interpreter;
import jp.teamdecode.lexer.Lexer;
import jp.teamdecode.parser.Parser;
import org.junit.Test;

import static org.junit.Assert.*;

public class SymbolTableBuilderTest {
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

        SymbolTable symbolTable = symbolTableBuilder.getSymbolTable();
        System.out.println(symbolTable);
        System.out.println(interpreter.getGlobalScope());
        assertEquals(INTEGER, symbolTable.lookup("number").type.name);
        assertEquals(INTEGER, symbolTable.lookup("a").type.name);
        assertEquals(INTEGER, symbolTable.lookup("b").type.name);
        assertEquals(REAL, symbolTable.lookup("y").type.name);
    }


}