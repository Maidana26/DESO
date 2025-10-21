package exceptions;

public class UsuarioNoEncontradoException extends ExcepcionAutenticacion {
    public UsuarioNoEncontradoException(String usuario) {
        super("El usuario '" + usuario + "' no existe.");
    }
}
