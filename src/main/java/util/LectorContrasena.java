package util;
/*
import java.io.IOException;
import java.io.Console;
import java.util.Arrays;
import javax.swing.JPasswordField;
import javax.swing.JOptionPane;
*/

import java.io.Console;
import java.util.Scanner;

public class LectorContrasena {

    public static String leerContrasena(String prompt) {
        Console console = System.console();

        if (console != null) {
            // Consola real: oculta los caracteres
            char[] pwd = console.readPassword(prompt);
            String password = new String(pwd);
            java.util.Arrays.fill(pwd, ' '); // limpiar arreglo
            return password;
        } else {
            // IDE (NetBeans, Eclipse): usamos Scanner
            System.out.print(prompt);
            Scanner sc = new Scanner(System.in);
            return sc.nextLine();
        }
    }
    /*
    // Fallback: leer caracter por caracter y mostrar *
    private static String leerContrasenaAsteriscos(String prompt) {
        System.out.print(prompt);
        StringBuilder password = new StringBuilder();
        try {
            while (true) {
                int ch = System.in.read();
                if (ch == '\n' || ch == '\r') break;
                if (ch == 8 || ch == 127) { // backspace
                    if (password.length() > 0) {
                        password.deleteCharAt(password.length() - 1);
                        System.out.print("\b \b");
                    }
                } else {
                    password.append((char) ch);
                    System.out.print('*');
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println();
        return password.toString();
    }
*/
}
