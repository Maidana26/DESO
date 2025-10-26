package exceptions;

/**
 * Excepción genérica para representar errores relacionados
 * con operaciones sobre huéspedes (por ejemplo, búsqueda, registro o eliminación).
 */
public class ExcepcionHuesped extends Exception {
     /**
     * Crea una nueva excepción con el mensaje especificado.
     *
     * @param mensaje el detalle del error relacionado con huéspedes.
     */
    public ExcepcionHuesped(String mensaje) {
        super(mensaje);
    }
    /**
    * Crea una nueva instancia de {@link ExcepcionHuesped} con un mensaje
    * descriptivo y una causa subyacente.
    *
    * <p>Este constructor permite encadenar excepciones, preservando la
    * excepción original que provocó el error, lo cual es útil para
    * depuración y trazabilidad.</p>
    *
    * @param mensaje descripción del error ocurrido.
    * @param causa la excepción original que provocó esta {@code ExcepcionHuesped}.
    */
    public ExcepcionHuesped(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
