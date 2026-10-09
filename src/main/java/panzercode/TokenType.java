/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package panzercode;

public enum TokenType {
    // Keywords
    PANZER,
    LADEN,

    // Datatypes
    ZAHL,   // int
    DOBLET, // double
    WAHR,   // boolean
    ZIS,    // char
    SAITE,  // String

    // Identifiers
    IDENTIFIER,

    // Literals
    NUMBER,
    STRING,

    // Operators
    EQUALS,

    // Symbols / punctuation
    LPAREN,
    RPAREN,
    SEMICOLON,

    // End of input
    EOF
}