package jp.teamdecode.analazer;

import jp.teamdecode.exception.InterpreterException;
import jp.teamdecode.exception.symantic.VariableDeclarationException;
import jp.teamdecode.lexer.Lexer;
import jp.teamdecode.parser.Parser;
import org.junit.Test;

import static org.junit.Assert.*;

public class SemanticAnalyzerTest {

    @Test
    public void varDeclarationTest() throws Exception{
        String text = "program SymTab5;\n" +
                "    var x : integer;\n" +
                "\n" +
                "begin\n" +
                "    x := y;\n" +
                "end.";
        Lexer lexer = new Lexer(text);
        Parser parser = new Parser(lexer);
        SemanticAnalyzer analyzer = new SemanticAnalyzer();

        Throwable error = null;
        try {
            analyzer.analyze(parser.parse());
        } catch (InterpreterException e) {
            error = e;
            while (error.getCause() != null) error = error.getCause();
        }
        assertTrue(error instanceof VariableDeclarationException);
        assertEquals("Variable 'y' not declared",error.getMessage());
    }

}