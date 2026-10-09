/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package panzercode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Lexer {

    private static final Map<String, TokenType> KEYWORDS
            = new HashMap<String, TokenType>();

    static {
        // Commands
        KEYWORDS.put("Panzer", TokenType.PANZER);
        KEYWORDS.put("Laden", TokenType.LADEN);

        // Datatypes
        KEYWORDS.put("zahl", TokenType.ZAHL);
        KEYWORDS.put("doblet", TokenType.DOBLET);
        KEYWORDS.put("wahr", TokenType.WAHR);
        KEYWORDS.put("zis", TokenType.ZIS);
        KEYWORDS.put("Saite", TokenType.SAITE);
        
        // Boolean
        KEYWORDS.put("true", TokenType.BOOLEAN);
        KEYWORDS.put("false", TokenType.BOOLEAN);
    }

    private final String source;
    private final List<Token> tokens = new ArrayList<Token>();

    private int current = 0;
    private int line = 1;

    public Lexer(String source) {
        this.source = source;
    }

    public List<Token> scanTokens() {
        while (current < source.length()) {
            char c = source.charAt(current);
            
            if (Character.isWhitespace(c)) {
                // Count new lines.
                if (c == '\n') {
                    line++;
                }
                current++;
            } else if (c == '(') {
                addToken(TokenType.LPAREN, "(");
                current++;
            } else if (c == ')') {
                addToken(TokenType.RPAREN, ")");
                current++;
            } else if (c == ';') {
                addToken(TokenType.SEMICOLON, ";");
                current++;
            } else if (c == '=') {
                addToken(TokenType.EQUALS, "=");
                current++;
            } else if (c == '"') {
                scanString();
            } else if (c == '\'') {
                scanCharacter();
            } else if (Character.isDigit(c)) {
                scanNumber();
            } else if (Character.isLetter(c)) {
                scanIdentifierOrKeyword();
            } else {
                throw new RuntimeException(
                        "Unexpected character '" + c
                        + "' on line " + line);
            }
        }

        tokens.add(new Token(TokenType.EOF, ""));
        return tokens;
    }

    private void scanIdentifierOrKeyword() {
        int start = current;
        
        // Read letters and digits until the word ends.
        while (current < source.length()
                && Character.isLetterOrDigit(
                        source.charAt(current))) {
            current++;
        }
        
        String word = source.substring(start, current);
        // Check whether the word is a reserved keyword.
        TokenType type = KEYWORDS.get(word);
        
        // If it is not a keyword, it is an identifier.
        if (type == null) {
            type = TokenType.IDENTIFIER;
        }
        addToken(type, word);
    }

    private void scanNumber() {
        int start = current;
        // Read the whole-number portion.
        while (current < source.length()
                && Character.isDigit(source.charAt(current))) {
            current++;
        }

        // If a decimal point is followed by a digit,
        // read the fractional portion too.
        if (current + 1 < source.length()
                && source.charAt(current) == '.'
                && Character.isDigit(source.charAt(current + 1))) {

            current++; // Skip the decimal point.

            while (current < source.length()
                    && Character.isDigit(source.charAt(current))) {
                current++;
            }
        }

        String number = source.substring(start, current);
        addToken(TokenType.NUMBER, number);
    }

    private void scanString() {
        int start = current;
        int startLine = line;
        current++; // Skip the opening quotation mark.

        while (current < source.length()
                && source.charAt(current) != '"') {
            
            // For now, strings cannot span multiple lines.
            if (source.charAt(current) == '\n') {
                throw new RuntimeException(
                        "Unterminated string starting on line "
                        + startLine);
            }
            current++;
        }

        // No closing quotation mark was found.
        if (current >= source.length()) {
            throw new RuntimeException(
                    "Unterminated string starting on line "
                    + startLine);
        }

        current++; // Include the closing quotation mark.
        String text = source.substring(start, current);
        addToken(TokenType.STRING, text);
    }
    
        private void scanCharacter() {
        int start = current;
        int startLine = line;

        current++; // Skip opening single quote.

        if (current >= source.length()
                || source.charAt(current) == '\n'
                || source.charAt(current) == '\'') {
            throw new RuntimeException(
                    "Invalid character literal on line " + startLine);
        }

        current++; // Read the character itself.

        if (current >= source.length()
                || source.charAt(current) != '\'') {
            throw new RuntimeException(
                    "Character literal must contain exactly one character"
                    + " on line " + startLine);
        }

        current++; // Skip closing single quote.

        String text = source.substring(start, current);
        addToken(TokenType.CHARACTER, text);
    }

    private void addToken(TokenType type, String lexeme) {
        tokens.add(new Token(type, lexeme));
    }
}
