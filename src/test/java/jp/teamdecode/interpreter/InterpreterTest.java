package jp.teamdecode.interpreter;

import jp.teamdecode.lexer.Lexer;
import jp.teamdecode.parser.Parser;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;

public class InterpreterTest {

    @Test
    public void lesson10() throws Exception {
        String text = "PROGRAM Part10;\n" +
                "VAR\n" +
                "   number     : INTEGER;\n" +
                "   a, b, c, x : INTEGER;\n" +
                "   y          : REAL;\n" +
                "\n" +
                "BEGIN {Part10}\n" +
                "   BEGIN\n" +
                "      number := 2;\n" +
                "      a := number;\n" +
                "      b := 10 * a + 10 * number DIV 4;\n" +
                "      c := a - - b\n" +
                "   END;\n" +
                "   x := 11;\n" +
                "   y := 20 / 7 + 3.14;\n" +
                "   { writeln('a = ', a); }\n" +
                "   { writeln('b = ', b); }\n" +
                "   { writeln('c = ', c); }\n" +
                "   { writeln('number = ', number); }\n" +
                "   { writeln('x = ', x); }\n" +
                "   { writeln('y = ', y); }\n" +
                "END.  {Part10}";
        Lexer lexer = new Lexer(text);
        Parser parser = new Parser(lexer);
        Interpreter interpreter = new Interpreter(parser);
        interpreter.interpret();

        Map<String, Object> results = interpreter.getGlobalScope();
        System.out.println("results = " + results);
        assertEquals(2, results.get("number"));
        assertEquals(2, results.get("a"));
        assertEquals(25, results.get("b"));
        assertEquals(27, results.get("c"));
        assertEquals(11, results.get("x"));
        assertEquals(5.99714285714, ((Number) results.get("y")).doubleValue(), 1e-10);
    }

}