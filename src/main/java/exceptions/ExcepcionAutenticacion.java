package exceptions;

/**
 * Clase base para todas las excepciones relacionadas con errores de autenticación.
 *
 * <p>Permite manejar de forma genérica los distintos fallos
 * que pueden ocurrir durante el inicio de sesión.</p>
 */
public class ExcepcionAutenticacion extends Exception {
    /**
     * Crea una nueva excepción de autenticación con el mensaje especificado.
     *
     * @param message el detalle del error.
     */
    public ExcepcionAutenticacion(String message){
        super(message);
    }
}
