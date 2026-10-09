/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package panzercode;

import java.util.List;

public class Parser {

    private final List<Token> tokens;
    private int current = 0;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
    }

    // Parse the entire program.
    public Program parse() {
        java.util.List<Statement> statements =
                new java.util.ArrayList<Statement>();

        while (!isAtEnd()) {
            statements.add(declaration());
        }

        return new Program(statements);
    }

    // Parse a variable declaration.
    private Statement declaration() {

        Token dataType = advance();

        Token name = consume(
                TokenType.IDENTIFIER,
                "Expected a variable name after datatype."
        );

        Expression initializer = null;

        if (match(TokenType.EQUALS)) {
            initializer = expression();
        }

        consume(
                TokenType.SEMICOLON,
                "Expected ';' after variable declaration."
        );

        return new VarDeclaration(
                dataType,
                name,
                initializer
        );
    }

    // Parse a literal or variable reference.
    private Expression expression() {

        if (match(TokenType.NUMBER, TokenType.STRING)) {
            return new LiteralExpression(previous());
        }

        if (match(TokenType.IDENTIFIER)) {
            return new VariableExpression(previous());
        }

        throw error(
                "Expected a number, string, or variable reference."
        );
    }

    // Check whether the current token matches any supplied type.
    private boolean match(TokenType... types) {

        for (TokenType type : types) {
            if (check(type)) {
                advance();
                return true;
            }
        }

        return false;
    }

    // Check the current token without consuming it.
    private boolean check(TokenType type) {

        if (isAtEnd()) {
            return type == TokenType.EOF;
        }

        return peek().getType() == type;
    }

    // Consume a token only if it has the expected type.
    private Token consume(
            TokenType type,
            String message) {

        if (check(type)) {
            return advance();
        }

        throw error(message);
    }

    // Move to the next token and return the one we passed.
    private Token advance() {

        if (!isAtEnd()) {
            current++;
        }

        return previous();
    }

    // Get the token we most recently consumed.
    private Token previous() {
        return tokens.get(current - 1);
    }

    // Look at the current token without consuming it.
    private Token peek() {
        return tokens.get(current);
    }

    // Check whether we have reached the end of the token list.
    private boolean isAtEnd() {
        return peek().getType() == TokenType.EOF;
    }

    // Create a readable syntax error.
    private RuntimeException error(String message) {

        return new RuntimeException(
                message + " Found: " + peek()
        );
    }
}