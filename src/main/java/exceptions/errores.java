package exceptions;

/**
 *
 * @author matum
 */
public class errores {

    public class HuespedNoEncontradoException extends Exception {
        public HuespedNoEncontradoException(String mensaje) {
            super(mensaje);
        }
    }

}
