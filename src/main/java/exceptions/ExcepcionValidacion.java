package exceptions;

public class ExcepcionValidacion extends Exception {

    public ExcepcionValidacion(String message) {
        super(message);
    }

    public ExcepcionValidacion(String message, Throwable cause) {
        super(message, cause);
    }
}
