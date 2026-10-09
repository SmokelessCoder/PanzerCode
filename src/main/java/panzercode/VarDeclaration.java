/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package panzercode;

public class VarDeclaration extends Statement {

    private final Token dataType;
    private final Token name;
    private final Expression initializer;

    public VarDeclaration(
            Token dataType,
            Token name,
            Expression initializer) {

        this.dataType = dataType;
        this.name = name;
        this.initializer = initializer;
    }

    public Token getDataType() {
        return dataType;
    }

    public Token getName() {
        return name;
    }

    public Expression getInitializer() {
        return initializer;
    }
}
