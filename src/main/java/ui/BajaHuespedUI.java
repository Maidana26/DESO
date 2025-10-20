package ui;

import service.GestorHuesped;
import exceptions.ExcepcionHuesped;
import java.util.Scanner;

public class BajaHuespedUI {
    private final GestorHuesped gestor;
    private final Scanner scanner = new Scanner(System.in);

    public BajaHuespedUI(GestorHuesped gestor) {
        this.gestor = gestor;
    }

    public void mostrarPantallaBaja() {
        System.out.println("=== DAR DE BAJA HUÉSPED ===");
        System.out.print("Tipo de documento: ");
        String tipoDoc = scanner.nextLine().trim();
        System.out.print("Número de documento: ");
        int nroDoc = Integer.parseInt(scanner.nextLine().trim());

        try {
            var huesped = gestor.buscarHuesped(tipoDoc, nroDoc);

            System.out.println("\nSe encontró el siguiente huésped:");
            System.out.println("→ " + huesped.getNombres() + " " + huesped.getApellido());
            System.out.print("\n¿Desea eliminar este huésped del sistema? [E]liminar / [C]ancelar: ");
            String opcion = scanner.nextLine().trim().toUpperCase();

            if (opcion.equals("E")) {
                gestor.darBajaHuesped(tipoDoc, nroDoc);
                System.out.println("\nLos datos del huésped "
                        + huesped.getNombres() + " " + huesped.getApellido() + ", "
                        + huesped.getTipoDeDocumento() + " " + huesped.getNumeroDeDocumento()
                        + " han sido eliminados del sistema.");
                System.out.print("PRESIONE CUALQUIER TECLA PARA CONTINUAR...");
                scanner.nextLine();
            } else {
                System.out.println("\nOperación cancelada.");
            }

        } catch (ExcepcionHuesped e) {
            System.out.println("\n" + e.getMessage());
            if (e.getMessage().contains("ha alojado")) {
                System.out.print("PRESIONE CUALQUIER TECLA PARA CONTINUAR...");
                scanner.nextLine();
            }
        }
    }
}
