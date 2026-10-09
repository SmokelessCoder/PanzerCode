/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package panzercode;

public class LiteralExpression extends Expression {

    private final Token value;

    public LiteralExpression(Token value) {
        this.value = value;
    }

    public Token getValue() {
        return value;
    }
}