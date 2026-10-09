/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package panzercode;

public class VariableExpression extends Expression {

    private final Token name;

    public VariableExpression(Token name) {
        this.name = name;
    }

    public Token getName() {
        return name;
    }
}