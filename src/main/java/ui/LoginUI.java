package ui;

import service.GestorAutenticacion;
import util.LectorContrasena;
// import util.ValidarContrasena;  //Bloque de validación desactivado porque no lo necesitamos por ahora
import exceptions.UsuarioNoEncontradoException;
import exceptions.ContrasenaIncorrectaException;
import exceptions.AmbosIncorrectosException;
import java.util.Scanner;

/**
 * Interfaz de usuario para el login del sistema.
 * Solicita usuario y contraseña, controla el número máximo de intentos
 * y maneja excepciones relacionadas con la autenticación.
 */
public class LoginUI {
    // Gestor que maneja la lógica de autenticación
    private final GestorAutenticacion gestor;
    // Scanner para leer entrada del usuario
    private final Scanner scanner = new Scanner(System.in);

    /**
     * Constructor de la interfaz de login.
     * 
     * @param gestor Gestor de autenticación que valida usuario y contraseña
     */
    public LoginUI(GestorAutenticacion gestor) {
        this.gestor = gestor;
    }

    /**
     * Muestra la pantalla de login, solicita usuario y contraseña,
     * valida los datos y controla los intentos fallidos.
     * 
     * @return true si la autenticación fue exitosa, false si se alcanzó el límite de intentos
     */
    public boolean mostrarPantallaLogin() {
        final int MAX_INTENTOS = 3;
        int intentos = 0;
        boolean autenticado = false;

        while (intentos < MAX_INTENTOS && !autenticado) {
            System.out.print("Usuario: ");
            String usuario = scanner.nextLine();
            String contrasena = LectorContrasena.leerContrasena("Contraseña: ");

            try {
                /*
                //VALIDACIÓN DE FORMATO DE CONTRASEÑA (DESACTIVADA POR AHORA)
                if (!ValidarContrasena.esValido(contrasena)) {
                    System.out.println("Error de formato: " + ValidarContrasena.rules());
                    continue; // no cuenta como intento fallido
                }
                */

                //Autenticación principal
                if (gestor.autenticar(usuario, contrasena)) {
                    System.out.println("Inicio de sesión exitoso. ¡Bienvenido, " + usuario + "!");
                    autenticado = true;
                }

            } catch (UsuarioNoEncontradoException e) {
                intentos++;
                System.out.println(e.getMessage());
                System.out.println("Intento " + intentos + " de " + MAX_INTENTOS + ".");

            } catch (ContrasenaIncorrectaException e) {
                intentos++;
                System.out.println(e.getMessage());
                System.out.println("Intento " + intentos + " de " + MAX_INTENTOS + ".");

            } catch (AmbosIncorrectosException e) {
                intentos++;
                System.out.println(e.getMessage());
                System.out.println("Intento " + intentos + " de " + MAX_INTENTOS + ".");
            }

            if (intentos == MAX_INTENTOS && !autenticado) {
                System.out.println("Se alcanzó el número máximo de intentos. El acceso fue bloqueado.");
            }
        }
        return autenticado;
    }
}