/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package panzercode;

/**
 *
 * @author Raikes
 */

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.io.IOException;
import java.nio.file.Path;

public class PanzerCode {

    public static void main(String[] args) {
        Path sourcePath = Path.of("PanzerCode.panzer");

        try {
            String source = Files.readString(
                    sourcePath,
                    StandardCharsets.UTF_8
            );

            Lexer lexer = new Lexer(source);

            for (Token token : lexer.scanTokens()) {
                System.out.println(token);
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
