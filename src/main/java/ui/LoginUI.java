package ui;

import service.GestorAutenticacion;
import util.LectorContrasena;
import util.ValidarContrasena;
import exceptions.ExcepcionAutenticacion;
import exceptions.ExcepcionValidacion;
import java.util.Scanner;

public class LoginUI {
    private final GestorAutenticacion gestor;
    private final Scanner scanner = new Scanner(System.in);

    public LoginUI(GestorAutenticacion gestor) {
        this.gestor = gestor;
    }

    public void mostrarPantallaLogin() {
        final int MAX_INTENTOS = 3;
        int intentos = 0;
        boolean autenticado = false;

        while (intentos < MAX_INTENTOS && !autenticado) {
            System.out.print("Usuario: ");
            String usuario = scanner.nextLine();
            String contrasena = LectorContrasena.leerContrasena("Contraseña: ");

            try {
                // Valido formato antes de autenticar
                if (!ValidarContrasena.esValido(contrasena)) {
                    throw new ExcepcionValidacion(ValidarContrasena.rules());
                }

                if (gestor.autenticar(usuario, contrasena)) {
                    System.out.println("Inicio de sesión exitoso. Bienvenido, " + usuario + "!");
                    autenticado = true;
                }

            } catch (ExcepcionValidacion | ExcepcionAutenticacion e) {
                intentos++;
                System.out.println(e.getMessage());
                System.out.println("Intento " + intentos + " de " + MAX_INTENTOS + ".");
                if (intentos == MAX_INTENTOS) {
                    System.out.println("Se alcanzó el número máximo de intentos.");
                }
            }
        }
    }
}
