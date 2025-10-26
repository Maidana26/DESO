package exceptions;

/**
 * Excepción que se lanza cuando el usuario existe pero la contraseña
 * ingresada no coincide con la registrada.
 *
 * <p>Forma parte de la jerarquía de excepciones de autenticación.</p>
 */
public class ContrasenaIncorrectaException extends ExcepcionAutenticacion {
    /**
     * Crea una nueva excepción con un mensaje predefinido indicando
     * que la contraseña ingresada es incorrecta.
     */
    public ContrasenaIncorrectaException() {
        super("La contraseña ingresada es incorrecta.");
    }
}