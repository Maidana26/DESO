package dao;

import service.manipularCSV;
import java.util.Scanner;
import service.ValidadorHuesped;
import models.HuespedDTO;
import models.DireccionDTO;
import java.util.ArrayList;
import java.util.List;


public class HuespedDAO {
    public static void darDeAltaHuesped(Scanner sc) {
        boolean cargar_otro = true;
        while(cargar_otro){
            String nombre = "";
            String apellido = "";
            String tipoDoc = "";
            String dni = "";
            String posIVA = "Consumidor Final";
            String cuit = "";
            String telefono = "";
            String fechaNac = "";
            String email = "";
            String ocupacion = "";
            String nacionalidad = "";
            String calle = "";
            String numero = "";
            String departamento = "";
            String piso = "";
            String codigoPostal = "";
            String localidad = "";
            String provincia = "";
            String pais = "";

            boolean terminarAlta = false;
            while (!terminarAlta) {

                //Ingreso y verificacion de datos, tanto formato como obligatoriedad
                System.out.println("=== Complete los siguientes campos (enter para conservar valor) ===");
                nombre = solicitarCampoConDefault(sc, "Nombre ", ValidadorHuesped::validarNombre, nombre);
                apellido = solicitarCampoConDefault(sc, "Apellido ", ValidadorHuesped::validarApellido, apellido);
                tipoDoc = solicitarCampoConDefault(sc, "Tipo de Documento (DNI/LE/LC/Pasaporte)", ValidadorHuesped::validarTipoDocumento, tipoDoc);
                dni = solicitarCampoConDefault(sc, "DNI (8digitos)", ValidadorHuesped::validarNumeroDocumento, dni);
                posIVA = solicitarCampoConDefault(sc, "Posición frente al IVA, admtie (Responsable Inscripto/Monotributista/Exento/Consumidor Final), CF por omisión)", ValidadorHuesped::validarPosicionFrenteIVA, posIVA);
                cuit = solicitarCampoConDefault(sc, "CUIT (11 dígitos) (no obligatorio)", ValidadorHuesped::validarCUIT, cuit);
                telefono = solicitarCampoConDefault(sc, "Teléfono", ValidadorHuesped::validarTelefono, telefono);
                fechaNac = solicitarCampoConDefault(sc, "Fecha de nacimiento (dd/mm/aaaa)", ValidadorHuesped::validarFecha, fechaNac);
                email = solicitarCampoConDefault(sc, "Email(no obligatorio)", ValidadorHuesped::validarEmail, email);
                ocupacion = solicitarCampoConDefault(sc, "Ocupación", ValidadorHuesped::validarOcupacion, ocupacion);
                nacionalidad = solicitarCampoConDefault(sc, "Nacionalidad", ValidadorHuesped::validarNacionalidad, nacionalidad);

                System.out.println("\n=== Dirección del Huésped ===");
                calle = solicitarCampoConDefault(sc, "Calle", ValidadorHuesped::validarCalle, calle);
                numero = solicitarCampoConDefault(sc, "Número", ValidadorHuesped::validarNumero, numero);
                departamento = solicitarCampoConDefault(sc, "Departamento (opcional)", ValidadorHuesped::validarDepartamento, departamento);
                piso = solicitarCampoConDefault(sc, "Piso (opcional)", ValidadorHuesped::validarPiso, piso);
                codigoPostal = solicitarCampoConDefault(sc, "Código Postal", ValidadorHuesped::validarCodigoPostal, codigoPostal);
                localidad = solicitarCampoConDefault(sc, "Localidad", ValidadorHuesped::validarLocalidad, localidad);
                provincia = solicitarCampoConDefault(sc, "Provincia", ValidadorHuesped::validarProvincia, provincia);
                pais = solicitarCampoConDefault(sc, "País", ValidadorHuesped::validarPais, pais);

                // Submenú de confirmación
                boolean enSubmenu = true;
                while (enSubmenu) {
                    System.out.println("\nSeleccione una opción:");
                    System.out.println("1. Siguiente (confirmar alta)");
                    System.out.println("2. Cancelar (volver al menú principal)");
                    System.out.print("Opción: ");

                    String opcion_siguiente_cancelar = sc.nextLine().trim();

                    switch (opcion_siguiente_cancelar) {
                        case "1":{
                            // Buscar duplicado por DNI
                            HuespedDTO existente = manipularCSV.buscarHuespedPorDni(dni);
                            if (existente != null) {
                                System.out.println("\n ¡CUIDADO! El tipo y número de documento ya existen en el sistema:");
                                System.out.println("Huésped existente: " + existente.getNombre() + " " + existente.getApellido());
                                System.out.println("1) Aceptar igualmente (reemplaza)");
                                System.out.println("2) Corregir");
                                System.out.print("Opción: ");
                                boolean opcion_valida = true;
                                do{
                                    String opcion_dni_repetido = sc.nextLine().trim();
                                    switch(opcion_dni_repetido) {
                                        case "1": {
                                            // Reemplazar/guardar y preguntar si quiere cargar otro
                                            DireccionDTO nueva_direccion = new DireccionDTO(calle, numero, departamento, piso, codigoPostal, localidad, provincia, pais);
                                            HuespedDTO nuevo_huesped = new HuespedDTO(nombre, apellido, tipoDoc, dni, posIVA, cuit, telefono, fechaNac, email, ocupacion, nacionalidad, nueva_direccion);
                                            manipularCSV.guardarOHuespedReemplazando(nuevo_huesped);
                                            enSubmenu = false;
                                            terminarAlta = true;
                                            cargar_otro = preguntarCargarOtro(sc);
                                            break;
                                        }
                                        case "2": {
                                            // Corregir: salir del submenú y volver al formulario con valores precargados
                                            System.out.println(" Volviendo a corregir los datos...");
                                            enSubmenu = false;
                                            break;
                                        }
                                        default: {
                                            opcion_valida = false;
                                            break;
                                        }
                                    }
                                } while(!opcion_valida);
                            } else {
                                // No existe: guardar
                                DireccionDTO nueva_direccion = new DireccionDTO(calle, numero, departamento, piso, codigoPostal, localidad, provincia, pais);
                                HuespedDTO nuevo_huesped = new HuespedDTO(nombre, apellido, tipoDoc, dni,
                                posIVA, cuit, telefono, fechaNac, email, ocupacion, nacionalidad, nueva_direccion);
                                manipularCSV.guardarOHuespedReemplazando(nuevo_huesped);
                                enSubmenu = false;
                                terminarAlta = true;
                                cargar_otro = preguntarCargarOtro(sc);
                            }
                            break;
                        }
                        case "2": {
                            boolean opcion_valida = true;
                                do{

                                    System.out.println("\n¿Desea cancelar el alta del huésped?");
                                    System.out.println("1. Sí");
                                    System.out.println("2. No");
                                    System.out.print("Opción: ");
                                    String opcion_cancelar = sc.nextLine().trim();
                                    switch(opcion_cancelar) {
                                        case "1": {
                                            System.out.println("❌ Operación cancelada. Volviendo al menú principal...");
                                            return; // o terminarAlta = true + break outer as you prefer
                                        }
                                        case "2": {
                                            System.out.println("✅ Cancelación abortada. Regresando al formulario con los datos ingresados...");
                                            enSubmenu = false; // vuelve al formulario con los valores que quedaron en variables
                                            break;
                                        }
                                        default: {
                                            opcion_valida = false;
                                        }
                                    }
                                } while(!opcion_valida);
                        }
                        default:{
                            System.out.println("Opción inválida. Intente nuevamente.");
                        }
                    } // switch
                } // while en submenu
            } // while terminar alta
        } //while cargar otro
    }

    // Interfaz funcional para referenciar los métodos del validador huesped
    @FunctionalInterface
    interface Validador {
        boolean validar(String valor);
    }
    // Método genérico que pide y valida un campo
    private static String solicitarCampoConDefault(Scanner sc, String etiqueta, 
            Validador validador, String valorActual) {
        String prompt = (valorActual == null || valorActual.isEmpty())
                ? etiqueta + ": "
                : String.format("%s [%s]: ", etiqueta, valorActual);

        String valor;
        boolean valido;
        do {
            System.out.print(prompt);
            valor = sc.nextLine().trim();
            // si el usuario deja vacío, se conserva el valor actual
            if (valor.isEmpty() && valorActual != null) {
                valor = valorActual;
            }
            valido = validador.validar(valor);
        } while (!valido);
        return valor;
    }
    public static boolean preguntarCargarOtro(Scanner sc) {
        while (true) {
            System.out.println("\n¿Qué desea hacer ahora?");
            System.out.println("1. Cargar otro huésped");
            System.out.println("2. Volver al menú principal");
            System.out.println("3. Salir del sistema");
            System.out.print("Opción: ");
            String opcion = sc.nextLine();

            switch (opcion) {
                case "1":
                    return true; // vuelve a cargar otro huésped
                case "2":
                    System.out.println("\n↩ Volviendo al menú principal...");
                    return false; // vuelve al menú principal
                case "3":
                    System.out.println("\n👋 Gracias por usar el sistema. ¡Hasta pronto!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("⚠ Opción inválida. Intente nuevamente.\n");
            }
        }
    } 
}
