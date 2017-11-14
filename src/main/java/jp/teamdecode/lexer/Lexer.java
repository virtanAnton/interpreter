package jp.teamdecode.lexer;

import com.sun.istack.internal.Nullable;
import jp.teamdecode.exception.LexerException;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static jp.teamdecode.lexer.Token.Type.*;

public class Lexer {
    private static final Map<String, Token> KEYWORDS;

    static {
        Map<String, Token> keywords = new HashMap<>();
        keywords.put("PROGRAM", new Token(PROGRAM, "PROGRAM"));
        keywords.put("VAR", new Token(VAR, "VAR"));
        keywords.put("DIV", new Token(INTEGER_DIV, "DIV"));
        keywords.put("INTEGER", new Token(INTEGER, "INTEGER"));
        keywords.put("REAL", new Token(REAL, "REAL"));
        keywords.put("BEGIN", new Token(BEGIN, "BEGIN"));
        keywords.put("END", new Token(END, "END"));
        KEYWORDS = Collections.unmodifiableMap(keywords);
    }

    private final String text;
    private int position;
    @Nullable
    private Character currentChar;

    public Lexer(String text) {
        this.text = text;
        this.position = 0;
        if (this.text.length() == 0) this.currentChar = null;
        else this.currentChar = this.text.charAt(0);
    }

    private void advance() {
        position++;
        if (position > text.length() - 1) {
            this.currentChar = null;
        } else {
            this.currentChar = this.text.charAt(position);
        }
    }

    private String pick(int next) {
        int position = this.position + next;
        if (position > text.length() - 1) return null;
        else {
            return text.substring(this.position + 1, this.position + 1 + next);
        }
    }

    private void skipWhitespace() {
        while (currentChar != null && Character.isWhitespace(currentChar)) {
            this.advance();
        }
    }

    private Token handleNumber() {
        StringBuilder sb = new StringBuilder();
        while (currentChar != null && Character.isDigit(currentChar)) {
            sb.append(currentChar);
            advance();
            if (currentChar == '.') {
                sb.append(currentChar);
                advance();
                while (currentChar != null && Character.isDigit(currentChar)) {
                    sb.append(currentChar);
                    advance();
                }
                return new Token(REAL_CONST, Float.parseFloat(sb.toString()));
            }
        }
        return new Token(INTEGER_CONST, Integer.parseInt(sb.toString()));
    }

    private Token handleId() {
        StringBuilder sb = new StringBuilder();
        while (Character.isAlphabetic(currentChar) || currentChar == '_') {
            sb.append(currentChar);
            advance();
        }
        if (KEYWORDS.containsKey(sb.toString().toUpperCase())) {
            return KEYWORDS.get(sb.toString().toUpperCase());
        } else {
            return new Token(ID, sb.toString().toLowerCase());
        }
    }

    private void skipComment() {
        while (currentChar != null && currentChar != '}') {
            advance();
        }
        advance();
    }

    public Token getNextToken() throws LexerException {
        skipWhitespace();
        if (currentChar == null) return new Token(EOF, "");
        else if (Character.isAlphabetic(currentChar) || currentChar == '_') return handleId();
        else if (Character.isDigit(currentChar)) return handleNumber();
        else if (currentChar == ':' && Objects.equals(pick(1), "=")) {
            return new Token(ASSIGN, ":=");
        } else if (currentChar == '+') {
            advance();
            return new Token(PLUS, "+");
        } else if (currentChar == '-') {
            advance();
            return new Token(MINUS, "-");
        } else if (currentChar == '*') {
            advance();
            return new Token(MUL, "*");
        } else if (currentChar == '/') {
            advance();
            return new Token(FLOAT_DIV, "/");
        } else if (currentChar == '{') {
            advance();
            skipComment();
            return getNextToken();
        } else if (currentChar == ':') {
            advance();
            return new Token(COLON, ":");
        } else if (currentChar == '.') {
            advance();
            return new Token(DOT, ".");
        } else if (currentChar == ';') {
            advance();
            return new Token(SEMI, ";");
        } else if (currentChar == ',') {
            advance();
            return new Token(COMMA, ",");
        } else if (currentChar == '(') {
            advance();
            return new Token(LPAREN, "(");
        } else if (currentChar == ')') {
            advance();
            return new Token(RPAREN, ")");
        } else throw error();
    }


    private LexerException error() {
        return new LexerException("Invalid character. Position: " + position + "; currentChar: " + currentChar);
    }
}
