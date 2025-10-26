package ui;

import service.GestorHuesped;
import exceptions.ExcepcionHuesped;
import java.util.Scanner;

/**
 * Clase encargada de la interfaz de usuario para dar de baja un huésped.
 * Interactúa con el usuario vía consola y utiliza GestorHuesped para
 * realizar las operaciones de negocio.
 */
public class BajaHuespedUI {
    // Gestor de huéspedes que contiene la lógica de negocio
    private final GestorHuesped gestor;
    // Scanner para leer entrada por consola
    private final Scanner scanner = new Scanner(System.in);

    /**
     * Constructor que recibe un gestor de huéspedes.
     *
     * @param gestor instancia de GestorHuesped para realizar operaciones.
     */
    public BajaHuespedUI(GestorHuesped gestor) {
        this.gestor = gestor;
    }

    /**
     * Muestra la pantalla de baja de huésped.
     * Pide al usuario el tipo y número de documento,
     * busca el huésped en el sistema y permite eliminarlo.
     * Maneja los casos en que el huésped no existe o
     * no puede eliminarse por haber estado alojado.
     */
    public void mostrarPantallaBaja() {
        System.out.println("=== DAR DE BAJA HUÉSPED ===");

        System.out.print("Tipo de documento: ");
        String tipoDoc = scanner.nextLine().trim().toUpperCase();
        while (tipoDoc.isEmpty()) {
            System.out.print("Debe ingresar un tipo de documento válido: ");
            tipoDoc = scanner.nextLine().trim().toUpperCase();
        }

        System.out.print("Número de documento: ");
        String nroDoc = scanner.nextLine().trim();
        while (nroDoc.isEmpty()) {
            System.out.print("Debe ingresar un número de documento válido: ");
            nroDoc = scanner.nextLine().trim();
        }

        try {
            var huesped = gestor.buscarHuespedPorTipoYNumero(tipoDoc, nroDoc);

            if (huesped == null) {
                System.out.println("\nNo se encontró ningún huésped con esos datos.");
                System.out.print("PRESIONE CUALQUIER TECLA PARA CONTINUAR...");
                scanner.nextLine();
                return;
            }

            System.out.println("\nSe encontró el siguiente huésped:");
            System.out.println("→ " + huesped.getNombre() + " " + huesped.getApellido());
            System.out.print("\n¿Desea eliminar este huésped del sistema? [E]liminar / [C]ancelar: ");
            String opcion = scanner.nextLine().trim().toUpperCase();

            if (opcion.equals("E")) {
                gestor.darBajaHuesped(tipoDoc, nroDoc);
                System.out.println("\nLos datos del huésped "
                        + huesped.getNombre() + " " + huesped.getApellido() + ", "
                        + huesped.getTipoDeDocumento() + " " + huesped.getNumeroDocumento()
                        + " han sido eliminados del sistema.");
            } else {
                System.out.println("\nOperación cancelada.");
            }

        } catch (ExcepcionHuesped e) {
            System.out.println("\n" + e.getMessage());
        }

        System.out.print("PRESIONE CUALQUIER TECLA PARA CONTINUAR...");
        scanner.nextLine();
    }

}
