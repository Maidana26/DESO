package exceptions;

/**
 * Excepción que se lanza cuando el sistema no encuentra un usuario
 * con el nombre especificado durante la autenticación.
 */
public class UsuarioNoEncontradoException extends ExcepcionAutenticacion {
    /**
     * Crea una nueva excepción indicando qué usuario no fue encontrado.
     *
     * @param usuario el nombre de usuario que no existe en el sistema.
     */
    public UsuarioNoEncontradoException(String usuario) {
        super("El usuario '" + usuario + "' no existe.");
    }
}
