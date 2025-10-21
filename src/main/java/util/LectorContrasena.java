package util;

import java.io.IOException;
import java.io.Console;
import java.util.Arrays;
import javax.swing.JPasswordField;
import javax.swing.JOptionPane;

public class LectorContrasena {

    public static String leerContrasena(String prompt) {
    Console console = System.console();
    if (console != null) {
        char[] ch = console.readPassword(prompt);
        String pwd = new String(ch);
        Arrays.fill(ch, ' ');
        return pwd;
    } else {
        // fallback GUI en NetBeans
        JPasswordField field = new JPasswordField();
        Object[] msg = { prompt, field };
        int option = JOptionPane.showConfirmDialog(null, msg, "Ingrese contraseña", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (option == JOptionPane.OK_OPTION) {
            return new String(field.getPassword());
        } else {
            return null;
        }
    }
}

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
}
