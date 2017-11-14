package jp.teamdecode.lexer;

import org.junit.Test;

import java.lang.reflect.Method;

import static org.junit.Assert.*;

public class LexerTest {
    @Test
    public void pick() throws Exception {
        Lexer lexer = new Lexer("1234");
        Method method = lexer.getClass().getDeclaredMethod("pick", int.class);
        method.setAccessible(true);
        assertEquals(method.invoke(lexer, 2), "23");
    }
}