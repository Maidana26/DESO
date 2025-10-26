package exceptions;

/**
 * Excepción que se utiliza para indicar fallos en la validación de datos.
 *
 * <p>Puede usarse tanto para validar campos de entrada como para validar
 * reglas de negocio dentro del sistema.</p>
 */
public class ExcepcionValidacion extends Exception {
    /**
     * Crea una nueva excepción con el mensaje especificado.
     *
     * @param message el detalle del error de validación.
     */
    public ExcepcionValidacion(String message) {
        super(message);
    }

    /**
     * Crea una nueva excepción con un mensaje y una causa subyacente.
     *
     * @param message el detalle del error.
     * @param cause la excepción original que provocó este error.
     */
    public ExcepcionValidacion(String message, Throwable cause) {
        super(message, cause);
    }
}
