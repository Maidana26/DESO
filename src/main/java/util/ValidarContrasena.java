package util;

public class ValidarContrasena {

    public static boolean esValido(String password) {
    if (password == null) return false;

    int letters = 0;
    int[] digits = new int[password.length()];
    int dIdx = 0;

    for (char c : password.toCharArray()) {
        if (Character.isLetter(c)) letters++;
        if (Character.isDigit(c)) {
            digits[dIdx++] = Character.getNumericValue(c);
        }
    }

    if (letters < 5) return false;
    if (dIdx < 3) return false;

    for (int i = 0; i < dIdx - 1; i++) {
        int a = digits[i], b = digits[i+1];
        if (a == b) return false;
        if (Math.abs(a - b) == 1) return false;
    }
    return true;
}

    public static String rules() {
        return "La contraseña debe tener al menos 5 letras y 3 números " +
               "no iguales ni consecutivos en forma creciente o decreciente.";
    }
}
