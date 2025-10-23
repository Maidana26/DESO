package ui;

import java.util.*;
import util.*;
import models.*;
import dao.*;
import service.*;

public class DarDeAltaHuespedUI {
    
    HuespedDAO dao = new FileHuespedDAO();
    GestorHuesped gh = new GestorHuesped(dao);
    
    
    public void darDeAltaHuespedUI(Scanner sc) {
        boolean cargar_otro = true;
        while(cargar_otro){
            String nombre = "";
            String apellido = "";
            String tipoDeDocumento = "";
            String numeroDocumento = "";
            String posicionFrenteIVA = "Consumidor Final";
            String cuit = "";
            String telefono = "";
            String fechaNacimiento = "";
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
                nombre = gh.solicitarCampoConDefault(sc, "Nombre ", ValidadorHuesped::validarNombre, nombre);
                apellido = gh.solicitarCampoConDefault(sc, "Apellido ", ValidadorHuesped::validarApellido, apellido);
                tipoDeDocumento = gh.solicitarCampoConDefault(sc, "Tipo de Documento (DNI/LE/LC/Pasaporte)", ValidadorHuesped::validarTipoDocumento, tipoDeDocumento);
                numeroDocumento = gh.solicitarCampoConDefault(sc, "DNI (8 digitos)", ValidadorHuesped::validarNumeroDocumento, numeroDocumento);
                posicionFrenteIVA = gh.solicitarCampoConDefault(sc, "Posición frente al IVA, admtie (Responsable Inscripto/Monotributista/Exento/Consumidor Final)", ValidadorHuesped::validarPosicionFrenteIVA, posicionFrenteIVA);
                cuit = gh.solicitarCampoConDefault(sc, "CUIT (11 dígitos) (no obligatorio)", ValidadorHuesped::validarCUIT, cuit);
                telefono = gh.solicitarCampoConDefault(sc, "Teléfono", ValidadorHuesped::validarTelefono, telefono);
                fechaNacimiento = gh.solicitarCampoConDefault(sc, "Fecha de nacimiento (dd/mm/aaaa)", ValidadorHuesped::validarFecha, fechaNacimiento);
                email = gh.solicitarCampoConDefault(sc, "Email(no obligatorio)", ValidadorHuesped::validarEmail, email);
                ocupacion = gh.solicitarCampoConDefault(sc, "Ocupación", ValidadorHuesped::validarOcupacion, ocupacion);
                nacionalidad = gh.solicitarCampoConDefault(sc, "Nacionalidad", ValidadorHuesped::validarNacionalidad, nacionalidad);

                System.out.println("\n=== Dirección del Huésped ===");
                calle = gh.solicitarCampoConDefault(sc, "Calle", ValidadorHuesped::validarCalle, calle);
                numero = gh.solicitarCampoConDefault(sc, "Número", ValidadorHuesped::validarNumero, numero);
                departamento = gh.solicitarCampoConDefault(sc, "Departamento (opcional)", ValidadorHuesped::validarDepartamento, departamento);
                piso = gh.solicitarCampoConDefault(sc, "Piso (opcional)", ValidadorHuesped::validarPiso, piso);
                codigoPostal = gh.solicitarCampoConDefault(sc, "Código Postal", ValidadorHuesped::validarCodigoPostal, codigoPostal);
                localidad = gh.solicitarCampoConDefault(sc, "Localidad", ValidadorHuesped::validarLocalidad, localidad);
                provincia = gh.solicitarCampoConDefault(sc, "Provincia", ValidadorHuesped::validarProvincia, provincia);
                pais = gh.solicitarCampoConDefault(sc, "País", ValidadorHuesped::validarPais, pais);

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
                            HuespedDTO existente = gh.buscarHuespedPorDni(numeroDocumento);
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
                                            gh.DarDeAltaHuesped(nombre, apellido, tipoDeDocumento, numeroDocumento, posicionFrenteIVA, cuit, telefono, fechaNacimiento, email, ocupacion, nacionalidad,
                                            calle, numero, departamento, piso, codigoPostal, localidad, provincia, pais);
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
                                gh.DarDeAltaHuesped(nombre, apellido, tipoDeDocumento, numeroDocumento, posicionFrenteIVA, cuit, telefono, fechaNacimiento, email, ocupacion, nacionalidad,
                                calle, numero, departamento, piso, codigoPostal, localidad, provincia, pais);
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