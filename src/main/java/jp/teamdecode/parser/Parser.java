package jp.teamdecode.parser;

import jp.teamdecode.ast.*;
import jp.teamdecode.exception.LexerException;
import jp.teamdecode.exception.ParserException;
import jp.teamdecode.lexer.Lexer;
import jp.teamdecode.lexer.Token;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static jp.teamdecode.lexer.Token.Type.*;


public class Parser {
    private final Lexer lexer;
    private Token currentToken;

    public Parser(Lexer lexer) throws LexerException {
        this.lexer = lexer;
        currentToken = lexer.getNextToken();
    }

    private void eat(Token.Type type) throws ParserException, LexerException {
        if (currentToken.getType() == type)
            currentToken = lexer.getNextToken();
        else throw error(currentToken);
    }

    public AST parse() throws LexerException, ParserException {
        AST node = program();
        if (currentToken.getType() != EOF) throw error(currentToken);
        return node;
    }

    private AST program() throws LexerException, ParserException {
        eat(PROGRAM);
        AST variable = variable();
        eat(SEMI);
        AST block = block();
        eat(DOT);
        return new Program(variable, block);
    }

    private AST block() throws LexerException, ParserException {
        return new Block(declarations(), compoundStatement());
    }

    private AST[] declarations() throws LexerException, ParserException {
        List<AST> declarations = new ArrayList<>();
        if (currentToken.getType() == VAR) {
            eat(VAR);
            while (currentToken.getType() == ID) {
                declarations.addAll(Arrays.asList(variableDeclaration()));
                eat(SEMI);
            }
        }
        return declarations.toArray(new AST[declarations.size()]);

    }

    private AST[] variableDeclaration() throws LexerException, ParserException {
        List<AST> declarations = new ArrayList<>();
        List<AST> variables = new ArrayList<>();
        variables.add(new Var(currentToken));
        eat(ID);
        while (currentToken.getType() == COMMA) {
            eat(COMMA);
            variables.add(new Var(currentToken));
            eat(ID);
        }
        eat(COLON);
        AST typeNode = typeSpec();
        for (AST variable : variables) {
            declarations.add(new VarDecl(variable, typeNode));
        }
        return declarations.toArray(new AST[declarations.size()]);
    }

    private AST typeSpec() throws LexerException, ParserException {
        Token token = currentToken;
        if (token.getType() == INTEGER) {
            eat(INTEGER);
        } else eat(REAL);
        return new Type(token);
    }

    private AST compoundStatement() throws LexerException, ParserException {
        eat(BEGIN);
        AST[] nodes = statementList();
        eat(END);
        return new Compound(nodes);
    }

    private AST[] statementList() throws LexerException, ParserException {
        List<AST> nodes = new ArrayList<>();
        nodes.add(statement());
        while (currentToken.getType() == SEMI) {
            eat(SEMI);
            nodes.add(statement());
        }
        return nodes.toArray(new AST[nodes.size()]);
    }

    private AST statement() throws LexerException, ParserException {
        Token token = currentToken;
        if (token.getType() == BEGIN)
            return compoundStatement();
        else if (token.getType() == ID) {
            return assignmentStatement();
        } else return empty();
    }

    private AST assignmentStatement() throws LexerException, ParserException {
        AST left = variable();
        Token token = currentToken;
        AST right = expr();
        return new Assign(left, token, right);
    }

    private AST expr() throws LexerException, ParserException {
        AST node = term();
        while (currentToken.getType() == PLUS
                || currentToken.getType() == MINUS) {
            Token token = currentToken;
            if (token.getType() == PLUS) {
                eat(PLUS);
            } else if (token.getType() == MINUS) {
                eat(MINUS);
            }
            return new BinOp(node, token, term());
        }
        return node;
    }

    private AST term() throws LexerException, ParserException {
        AST node = factor();
        while (currentToken.getType() == MUL
                || currentToken.getType() == FLOAT_DIV
                || currentToken.getType() == INTEGER_DIV) {
            Token token = currentToken;
            if (token.getType() == MUL) {
                eat(MUL);
            } else if (token.getType() == FLOAT_DIV) {
                eat(FLOAT_DIV);
            } else if (token.getType() == INTEGER_DIV) {
                eat(INTEGER_DIV);
            }
            return new BinOp(node, token, factor());
        }
        return node;
    }

    private AST factor() throws LexerException, ParserException {
        Token token = currentToken;
        if (token.getType() == PLUS) {
            eat(PLUS);
            return new UnaryOp(token, factor());
        } else if (token.getType() == MINUS) {
            eat(MINUS);
            return new UnaryOp(token, factor());
        } else if (token.getType() == INTEGER_CONST) {
            eat(INTEGER_CONST);
            return new Num(token);
        } else if (token.getType() == REAL_CONST) {
            eat(REAL_CONST);
            return new Num(token);
        } else if (token.getType() == LPAREN) {
            eat(LPAREN);
            AST node = expr();
            eat(RPAREN);
            return node;
        } else return variable();
    }

    private AST variable() throws LexerException, ParserException {
        AST node = new Var(currentToken);
        eat(ID);
        return node;
    }

    private AST empty() {
        return new NoOp();
    }

    private ParserException error(Token token) {
        return new ParserException("Parse error with token: " + token);
    }
}
