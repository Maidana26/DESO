package dao;

import service.manipularCSV;
import java.util.Scanner;
import service.ValidadorHuesped;

public class HuespedDAO {
    public static void darDeAltaHuesped(Scanner sc) {
        System.out.println("=== Alta de Huésped ===");
        System.out.println("=== Complete los siguientes campos:  ===");
        
        String nombre = solicitarCampo(sc, "Nombre", ValidadorHuesped::validarNombre);
        String apellido = solicitarCampo(sc, "Apellido", ValidadorHuesped::validarApellido);
        String tipoDoc = solicitarCampo(sc, "Tipo de Documento (DNI/Pasaporte/Libreta Cívica)", ValidadorHuesped::validarTipoDocumento);
        String dni = solicitarCampo(sc, "DNI", ValidadorHuesped::validarNumeroDocumento);
        String posIVA = solicitarCampo(sc, "Posición frente al IVA", ValidadorHuesped::validarTipoDocumento);
        String cuit = solicitarCampo(sc, "CUIT (11 dígitos)", ValidadorHuesped::validarCUIT);
        String telefono = solicitarCampo(sc, "Teléfono", ValidadorHuesped::validarTelefono);
        String fechaNac = solicitarCampo(sc, "Fecha de nacimiento (dd/mm/aaaa)", ValidadorHuesped::validarFecha);
        String email = solicitarCampo(sc, "Email", ValidadorHuesped::validarEmail);
        String ocupacion = solicitarCampo(sc, "Ocupación", ValidadorHuesped::validarOcupacion);
        String nacionalidad = solicitarCampo(sc, "Nacionalidad", ValidadorHuesped::validarNacionalidad);
        
        System.out.println("\n=== Dirección del Huésped ===");

        String calle = solicitarCampo(sc, "Calle", ValidadorHuesped::validarCalle);
        String numero = solicitarCampo(sc, "Número", ValidadorHuesped::validarNumero);
        String departamento = solicitarCampo(sc, "Departamento (opcional)", ValidadorHuesped::validarDepartamento);
        String piso = solicitarCampo(sc, "Piso (opcional)", ValidadorHuesped::validarPiso);
        String codigoPostal = solicitarCampo(sc, "Código Postal", ValidadorHuesped::validarCodigoPostal);
        String localidad = solicitarCampo(sc, "Localidad", ValidadorHuesped::validarLocalidad);
        String provincia = solicitarCampo(sc, "Provincia", ValidadorHuesped::validarProvincia);
        String pais = solicitarCampo(sc, "País", ValidadorHuesped::validarPais);
        
        // Submenú de confirmación
        boolean continuar = false;
        boolean salirSubmenu = false;

        while (!salirSubmenu) {
            System.out.println("\nSeleccione una opción:");
            System.out.println("1. Siguiente (confirmar alta)");
            System.out.println("2. Cancelar (volver al menú principal)");
            System.out.print("Opción: ");
            String opcion = sc.nextLine();

            switch (opcion) {
                case "1":
                    continuar = true;
                    salirSubmenu = true;
                    break;
                case "2":
                    continuar = false;
                    salirSubmenu = true;
                    break;
                default:
                    System.out.println("Opción inválida. Intente nuevamente.");
            }
        }

        if (continuar) {
            manipularCSV archivo = new manipularCSV();
            archivo.leerArchivo("C:\\Users\\lucab\\Documents\\NetBeansProjects\\DESO\\src\\main\\java\\data");
            System.out.println("Funcionalidad de alta en desarrollo.");
            
        } else {
            System.out.println("\n❌ Operación cancelada. Volviendo al menú principal...");
        }
    }
    // Método genérico que pide y valida un campo
    private static String solicitarCampo(Scanner sc, String etiqueta, Validador validador) {
        String valor;
        boolean valido;
        do {
            System.out.print(etiqueta + ": ");
            valor = sc.nextLine();
            valido = validador.validar(valor);
            if (!valido) {
                System.out.println("⚠️ Valor inválido. Intente nuevamente.");
            }
        } while (!valido);
        return valor;
    }

    // Interfaz funcional para referenciar los métodos del validador huesped
    @FunctionalInterface
    interface Validador {
        boolean validar(String valor);
    }
}
