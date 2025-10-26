package exceptions;

/**
 * Excepción que se lanza cuando tanto el usuario como la contraseña
 * ingresados son incorrectos durante el proceso de autenticación.
 *
 * <p>Extiende {@link ExcepcionAutenticacion} para mantener la coherencia
 * en la jerarquía de errores de autenticación.</p>
 */
public class AmbosIncorrectosException extends ExcepcionAutenticacion {
    /**
     * Crea una nueva excepción con un mensaje predefinido indicando
     * que el usuario y la contraseña son incorrectos.
     */
    public AmbosIncorrectosException() {
        super("Usuario y contraseña incorrectos. Vuelva a ingresarlos.");
    }
}
