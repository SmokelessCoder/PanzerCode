/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */


package panzercode;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class PanzerCode {

    public static void main(String[] args) {
        Path sourcePath = Path.of("PanzerCode.panzer");

        try {
            String source = Files.readString(
                    sourcePath,
                    StandardCharsets.UTF_8
            );

            // Step 1: Convert source code into tokens.
            Lexer lexer = new Lexer(source);
            List<Token> tokens = lexer.scanTokens();

            // Step 2: Parse the tokens into an AST.
            Parser parser = new Parser(tokens);
            Program program = parser.parse();

            // Step 3: Inspect the resulting AST.
            System.out.println("Parsing successful!");
            System.out.println(
                    "Number of statements: "
                    + program.getStatements().size()
            );

            for (Statement statement : program.getStatements()) {

                if (statement instanceof VarDeclaration) {
                    VarDeclaration declaration =
                            (VarDeclaration) statement;
                    System.out.println("Variable declaration:");
                    System.out.println(
                            "  Type: "
                            + declaration.getDataType().getLexeme()
                    );
                    System.out.println(
                            "  Name: "
                            + declaration.getName().getLexeme()
                    );
                    Expression initializer =
                            declaration.getInitializer();
                    if (initializer == null) {

                        System.out.println("  Initializer: none");

                    } else if (
                            initializer instanceof LiteralExpression) {

                        LiteralExpression literal =
                                (LiteralExpression) initializer;

                        System.out.println(
                                "  Initializer: "
                                + literal.getValue().getLexeme()
                        );

                    } else if (
                            initializer instanceof VariableExpression) {

                        VariableExpression variable =
                                (VariableExpression) initializer;

                        System.out.println(
                                "  Initializer: variable "
                                + variable.getName().getLexeme()
                        );
                    }
                }
            }

        } catch (IOException e) {
            System.err.println(
                    "Could not read PanzerCode source file: "
                    + sourcePath.toAbsolutePath()
            );

            System.err.println(e.getMessage());
        }
    }
}
