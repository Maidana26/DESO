package ui;

import dao.*;
import service.*;
import java.util.Scanner;

/**
 * Clase de interfaz de usuario para la búsqueda de huéspedes.
 * Permite al usuario ingresar criterios de búsqueda y luego
 * acceder a un menú post-búsqueda para modificar o volver al menú principal.
 */
public class BusquedaUI {

    /**
     * Muestra el menú principal de búsqueda de huéspedes.
     * Permite buscar huéspedes por nombre, apellido, tipo y número de documento.
     * Después de la búsqueda, llama al menú post-búsqueda.
     */
    public static void menuBusquedaHuesped() {
        Scanner sc = new Scanner(System.in);
        HuespedDAO dao = new FileHuespedDAO();
        GestorHuesped gh = new GestorHuesped(dao);
        int opcion;

        do {
            System.out.println("\n----- MENÚ DE HUÉSPEDES -----");
            System.out.println("1. Buscar huésped");
            System.out.println("2. Salir");
            System.out.print("Ingrese una opción: ");
            opcion = sc.nextInt();
            sc.nextLine(); // Consumimos el salto de línea

            if (opcion == 1) {
                // solicita criterios
                System.out.print("Nombre: ");
                String nombre = sc.nextLine();
                System.out.print("Apellido: ");
                String apellido = sc.nextLine();
                System.out.print("Tipo Documento: ");
                String tipoDoc = sc.nextLine();
                System.out.print("Número Documento: ");
                String nroDoc = sc.nextLine();
                // Ejecuta la búsqueda usando el gestor
                gh.buscarHuesped(nombre, apellido, tipoDoc, nroDoc, sc);
            }

        } while (opcion != 2);// Mantener el menú hasta que el usuario decida salir

        System.out.println("Saliendo del sistema...");
    }

     /**
     * Menú que aparece después de realizar una búsqueda.
     * Permite modificar un huésped o volver al menú principal.
     *
     * @param sc Scanner para leer entrada del usuario
     */
    /*private static void menuPostBusqueda(Scanner sc) {
        int opcion = 0;

        do {
            System.out.println("\n----- MENU POST BÚSQUEDA -----");
            System.out.println("1- Buscar Huesped");
            System.out.println("2- Volver al menú principal");
            System.out.print("Ingrese una opción: ");

            opcion = sc.nextInt();
            sc.nextLine(); 

            switch (opcion) {
                case 1:
                    System.out.println("Opción modificar huésped seleccionada (pendiente implementar).");
                    break;
                case 2:
                    System.out.println("Volviendo al menú principal...");
                    break;
                default:
                    System.out.println("Opción inválida. Intente nuevamente.");
            }

        } while (opcion != 2);
    }*/
}
