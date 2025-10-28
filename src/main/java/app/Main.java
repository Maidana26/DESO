/*package app;

import dao.ArchivoConserjeDAO;
import service.GestorAutenticacion;
import ui.LoginUI;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        var path = Paths.get("data/conserjes.csv"); // ruta relativa
        var dao = new ArchivoConserjeDAO(path);
        var gestor = new GestorAutenticacion(dao);
        var ui = new LoginUI(gestor);

        ui.mostrarPantallaLogin();
    }
}*/
package app;

import ui.*;
import dao.*;
import java.nio.file.Paths;
import service.*;
import java.util.*;

/**
 * Clase principal de la aplicación de gestión hotelera.
 * Este programa permite gestionar huéspedes mediante un flujo de:
 * <ul>
 *     <li>Inicio de sesión de usuarios (conserjes) mediante LoginUI.</li>
 *     <li>Dar de alta huéspedes en el sistema.</li>
 *     <li>Dar de baja huéspedes existentes.</li>
 *     <li>Buscar huéspedes en la base de datos.</li>
 * </ul>
 *
 * <p>El flujo principal sigue los pasos:</p>
 * <ol>
 *     <li>Se solicita inicio de sesión mediante usuario y contraseña.</li>
 *     <li>Si la autenticación es exitosa, se muestra un menú principal.</li>
 *     <li>El usuario puede elegir entre dar de alta, dar de baja o buscar huéspedes.</li>
 *     <li>Se permite repetir acciones hasta que el usuario decida salir.</li>
 * </ol>
 */
public class Main {
    /**
     * Método principal que inicializa y ejecuta la aplicación.
     * <p>
     * Configura los DAOs y gestores necesarios, instancia las interfaces de usuario
     * y controla el flujo de operaciones según la elección del usuario.
     * </p>
     *
     * @param args Argumentos de línea de comandos (no utilizados en esta aplicación).
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        DarDeAltaHuespedUI a = new DarDeAltaHuespedUI();
        
        // Configuración de DAOs y gestores
        var path = Paths.get("data/conserjes.csv"); // ruta relativa
        var dao = new ArchivoConserjeDAO(path);
        var gestor = new GestorAutenticacion(dao);
        var LoginUI = new LoginUI(gestor);
        var BusquedaUI = new BusquedaUI();
        
        var huespedDAO = new FileHuespedDAO();
        var gestorHuesped = new GestorHuesped(huespedDAO);
        var ui = new BajaHuespedUI(gestorHuesped);
        
        
        // ---- FLUJO PRINCIPAL ----
        System.out.println("=== SISTEMA DE GESTION HOTELERA ===");

        // Primero, iniciar sesión
        if(LoginUI.mostrarPantallaLogin()){
            int opcion;
            do {
                System.out.println("===== MENU PRINCIPAL =====");
                System.out.println("1. Dar de alta huesped");
                System.out.println("2. Dar de baja huesped");
                System.out.println("3. Buscar huesped");
                System.out.println("4. Salir");
                System.out.print("Seleccione una opcion: ");

                opcion = Integer.parseInt(sc.nextLine());

                switch (opcion) {
                    case 1 -> a.darDeAltaHuespedUI(sc);
                    case 2 -> ui.mostrarPantallaBaja();
                    case 3 -> BusquedaUI.menuBusquedaHuesped();
                    case 4 -> System.out.println("Saliendo del sistema...");
                    default -> System.out.println("Opcion invalida, intente nuevamente.");
                }

            } while (opcion != 4);

            sc.close();
        }
    }
}
