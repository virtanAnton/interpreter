package jp.teamdecode.lexer;

public class Token {
    public enum Type {
        /**
         * reserved keyword
         */
        PROGRAM,

        /**
         * reserved keyword
         */
        PROCEDURE,

        /**
         * reserved keyword
         */
        VAR,

        /**
         * ( : )
         */
        COLON,
        /**
         * ( , )
         */
        COMMA,

        /**
         * integer type
         */
        INTEGER,

        /**
         * Pascal real type
         */
        REAL,

        /**
         * for example 3 or 5
         */
        INTEGER_CONST,

        /**
         * for example 3,14
         */
        REAL_CONST,

        /**
         * for int division (DIV keyword)
         */
        INTEGER_DIV,

        /**
         * for float division ( / )
         */
        FLOAT_DIV,
        ID,
        BEGIN,
        END,
        DOT,
        SEMI,
        /**
         * ( := )
         */
        ASSIGN,
        LPAREN,
        RPAREN,
        PLUS,
        MUL,
        MINUS,

        /**
         * end of file
         */
        EOF
    }

    private final Type type;
    private final Object value;

    public Token(Type type, Object value) {
        this.type = type;
        this.value = value;
    }

    public Type getType() {
        return type;
    }

    public Object getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Token{" +
                "type=" + type +
                ", value=" + value +
                '}';
    }
}
