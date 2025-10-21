package exceptions;

public class AmbosIncorrectosException extends ExcepcionAutenticacion {
    public AmbosIncorrectosException() {
        super("Usuario y contraseña incorrectos. Vuelva a ingresarlos.");
    }
}
