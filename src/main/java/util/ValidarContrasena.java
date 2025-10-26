package util;

/**
 * Clase de utilidades para validar contraseñas.
 * Contiene métodos estáticos que verifican si una contraseña cumple con
 * reglas de seguridad específicas y proporciona las reglas en formato de texto.
 * 
 * <p>Reglas de contraseña:</p>
 * <ul>
 *     <li>Al menos 5 letras.</li>
 *     <li>Al menos 3 números.</li>
 *     <li>Los números no pueden repetirse ni ser consecutivos en orden creciente o decreciente.</li>
 * </ul>
 */
public class ValidarContrasena {

    /**
     * Verifica si una contraseña es válida según las reglas definidas.
     *
     * <p>La contraseña se considera válida si:</p>
     * <ul>
     *     <li>Tiene al menos 5 letras.</li>
     *     <li>Tiene al menos 3 números.</li>
     *     <li>Los números no se repiten ni son consecutivos.</li>
     * </ul>
     *
     * @param password La contraseña a validar. Puede ser null.
     * @return true si la contraseña cumple con las reglas; false en caso contrario.
     */
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

    /**
     * Devuelve una descripción de las reglas que debe cumplir la contraseña.
     *
     * @return Un texto explicativo con las reglas de validación de contraseñas.
     */
    public static String rules() {
        return "La contraseña debe tener al menos 5 letras y 3 números " +
               "no iguales ni consecutivos en forma creciente o decreciente.";
    }
}
