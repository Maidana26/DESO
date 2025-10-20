package ui;

import dao.HuespedDAO;
import java.util.Scanner;

public class Consola {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        HuespedDAO dao = new HuespedDAO();
        int opcion;

        do {
            System.out.println("\n----- MENÚ DE HUÉSPEDES -----");
            System.out.println("1. Buscar huésped");
            System.out.println("2. Salir");
            System.out.print("Ingrese una opción: ");
            opcion = sc.nextInt();
            sc.nextLine(); // Consumimos el salto de línea

            if (opcion == 1) {
                System.out.print("Nombre: ");
                String nombre = sc.nextLine();
                System.out.print("Apellido: ");
                String apellido = sc.nextLine();
                System.out.print("Tipo Documento: ");
                String tipoDoc = sc.nextLine();
                System.out.print("Número Documento: ");
                String nroDoc = sc.nextLine();

                dao.buscarHuesped(nombre, apellido, tipoDoc, nroDoc, sc);

                // Después de la búsqueda, mostramos el menú post-búsqueda
                menuPostBusqueda(sc);
            }

        } while (opcion != 2);

        System.out.println("Saliendo del sistema...");
    }

    // Menú que aparece después de la búsqueda
    private static void menuPostBusqueda(Scanner sc) {
        int opcion = 0;

        do {
            System.out.println("\n----- MENU POST BÚSQUEDA -----");
            System.out.println("1- Modificar huésped");
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
    }
}
