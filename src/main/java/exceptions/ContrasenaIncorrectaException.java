package exceptions;

public class ContrasenaIncorrectaException extends ExcepcionAutenticacion {
    public ContrasenaIncorrectaException() {
        super("La contraseña ingresada es incorrecta.");
    }
}