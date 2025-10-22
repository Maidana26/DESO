package service;

import dao.HuespedDAO;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import models.HuespedDTO;
import models.DireccionDTO;
import util.ValidadorHuesped;

import java.util.Scanner;

public class GestorHuesped {

    private final HuespedDAO huespedDAO;

    public GestorHuesped(HuespedDAO huespedDAO) {
        this.huespedDAO = huespedDAO;
    }

    public void buscarHuesped(String nombre, String apellido, String tipoDocumento, String numeroDocumento, Scanner sc) {
        String rutaLista = "data/ListaHuespedes.csv";
        String rutaEncontrados = "data/huespedesEncontrados.csv";
        List<String> encontrados = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(rutaLista))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length < 11) continue;

                String hNombre = partes[0].trim();
                String hApellido = partes[1].trim();
                String hTipoDoc = partes[2].trim();
                String hNumeroDoc = partes[3].trim();

                // Normalizamos los parámetros
                String nombreParam = nombre == null ? "" : nombre.trim();
                String apellidoParam = apellido == null ? "" : apellido.trim();
                String tipoDocParam = tipoDocumento == null ? "" : tipoDocumento.trim();
                String numeroDocParam = numeroDocumento == null ? "" : numeroDocumento.trim();

                boolean coincide =
                        (nombreParam.isEmpty() || hNombre.equalsIgnoreCase(nombreParam)) &&
                        (apellidoParam.isEmpty() || hApellido.equalsIgnoreCase(apellidoParam)) &&
                        (tipoDocParam.isEmpty() || hTipoDoc.equalsIgnoreCase(tipoDocParam)) &&
                        (numeroDocParam.isEmpty() || hNumeroDoc.equalsIgnoreCase(numeroDocParam));

                if (coincide) {
                    encontrados.add(linea);
                }
            }

            // Guardar resultados en huespedesEncontrados.csv
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(rutaEncontrados))) {
                for (String l : encontrados) {
                    bw.write(l);
                    bw.newLine();
                }
            }

            // Mostrar resultados numerados
            System.out.println("\n--- RESULTADOS DE LA BÚSQUEDA ---");
            if (encontrados.isEmpty()) {
                System.out.println("No se encontraron huéspedes con esos criterios.");
            } else {
                int index = 1;
                for (String l : encontrados) {
                    System.out.println(index + " - " + l);
                    index++;
                }
                System.out.println("\nBúsqueda finalizada. Resultados guardados en 'huespedesEncontrados.csv'.");
                
                // Menú post-búsqueda para seleccionar huésped a modificar
                System.out.print("\nIngrese el número del huésped a modificar (0 para cancelar): ");
                int seleccion = sc.nextInt();
                sc.nextLine(); // Consumir salto de línea
                if (seleccion > 0 && seleccion <= encontrados.size()) {
                    modificarHuesped(seleccion, sc);
                } else {
                    System.out.println("Modificación cancelada.");
                }
            }

        } catch (IOException e) {
            System.out.println("Error leyendo o escribiendo archivos: " + e.getMessage());
        }
    }

    public void modificarHuesped(int numeroLinea, Scanner sc) {
        String rutaLista = "data/ListaHuespedes.csv";
        String rutaEncontrados = "data/huespedesEncontrados.csv";
        List<String> listaCompleta = new ArrayList<>();

        try {
            // Leer archivo completo
            try (BufferedReader br = new BufferedReader(new FileReader(rutaLista))) {
                String linea;
                while ((linea = br.readLine()) != null) {
                    listaCompleta.add(linea);
                }
            }

            // Leer archivo de encontrados
            List<String> encontrados = new ArrayList<>();
            try (BufferedReader br2 = new BufferedReader(new FileReader(rutaEncontrados))) {
                String linea;
                while ((linea = br2.readLine()) != null) {
                    encontrados.add(linea);
                }
            }

            if (numeroLinea < 1 || numeroLinea > encontrados.size()) {
                System.out.println("Número de línea inválido.");
                return;
            }

            String lineaSeleccionada = encontrados.get(numeroLinea - 1);
            String[] partes = lineaSeleccionada.split(",");

            System.out.println("\n--- MODIFICAR HUÉSPED ---");
            System.out.println("Dejar vacío para mantener el valor actual.");

            System.out.print("Nombre (" + partes[0] + "): ");
            String nombre = sc.nextLine().trim();
            if (nombre.isEmpty()) nombre = partes[0];

            System.out.print("Apellido (" + partes[1] + "): ");
            String apellido = sc.nextLine().trim();
            if (apellido.isEmpty()) apellido = partes[1];

            System.out.print("Tipo Documento (" + partes[2] + "): ");
            String tipoDoc = sc.nextLine().trim();
            if (tipoDoc.isEmpty()) tipoDoc = partes[2];

            System.out.print("Número Documento (" + partes[3] + "): ");
            String nroDoc = sc.nextLine().trim();
            if (nroDoc.isEmpty()) nroDoc = partes[3];

            System.out.print("Posición frente al IVA (" + partes[4] + "): ");
            String posIVA = sc.nextLine().trim();
            if (posIVA.isEmpty()) posIVA = partes[4];

            System.out.print("CUIT (" + partes[5] + "): ");
            String cuit = sc.nextLine().trim();
            if (cuit.isEmpty()) cuit = partes[5];

            System.out.print("Teléfono (" + partes[6] + "): ");
            String telefono = sc.nextLine().trim();
            if (telefono.isEmpty()) telefono = partes[6];

            System.out.print("Fecha (" + partes[7] + "): ");
            String fecha = sc.nextLine().trim();
            if (fecha.isEmpty()) fecha = partes[7];

            System.out.print("Email (" + partes[8] + "): ");
            String email = sc.nextLine().trim();
            if (email.isEmpty()) email = partes[8];

            System.out.print("Ocupación (" + partes[9] + "): ");
            String ocupacion = sc.nextLine().trim();
            if (ocupacion.isEmpty()) ocupacion = partes[9];

            System.out.print("Nacionalidad (" + partes[10] + "): ");
            String nacionalidad = sc.nextLine().trim();
            if (nacionalidad.isEmpty()) nacionalidad = partes[10];

            // Reconstruir la línea
            String lineaModificada = String.join(",", nombre, apellido, tipoDoc, nroDoc, posIVA, cuit, telefono, fecha, email, ocupacion, nacionalidad);

            // Actualizar lista completa
            for (int i = 0; i < listaCompleta.size(); i++) {
                String l = listaCompleta.get(i);
                if (l.equals(lineaSeleccionada)) {
                    listaCompleta.set(i, lineaModificada);
                    break;
                }
            }

            // Guardar cambios en ListaHuespedes.csv
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(rutaLista))) {
                for (String l : listaCompleta) {
                    bw.write(l);
                    bw.newLine();
                }
            }

            System.out.println("\nLa operación ha culminado con éxito.");

        } catch (IOException e) {
            System.out.println("Error modificando el huésped: " + e.getMessage());
        }
    }
    
    public void darDeAltaHuesped(Scanner sc) {
        boolean cargar_otro = true;
        while (cargar_otro) {
            String nombre = "", apellido = "", tipoDoc = "", dni = "", posIVA = "Consumidor Final", cuit = "", 
                   telefono = "", fechaNac = "", email = "", ocupacion = "", nacionalidad = "";
            String calle = "", numero = "", departamento = "", piso = "", codigoPostal = "", 
                   localidad = "", provincia = "", pais = "";

            boolean terminarAlta = false;
            while (!terminarAlta) {

                // Pedir datos y validar
                nombre = solicitarCampoConDefault(sc, "Nombre", ValidadorHuesped::validarNombre, nombre);
                apellido = solicitarCampoConDefault(sc, "Apellido", ValidadorHuesped::validarApellido, apellido);
                tipoDoc = solicitarCampoConDefault(sc, "Tipo de Documento", ValidadorHuesped::validarTipoDocumento, tipoDoc);
                dni = solicitarCampoConDefault(sc, "DNI", ValidadorHuesped::validarNumeroDocumento, dni);
                posIVA = solicitarCampoConDefault(sc, "Posición frente al IVA", ValidadorHuesped::validarPosicionFrenteIVA, posIVA);
                cuit = solicitarCampoConDefault(sc, "CUIT", ValidadorHuesped::validarCUIT, cuit);
                telefono = solicitarCampoConDefault(sc, "Teléfono", ValidadorHuesped::validarTelefono, telefono);
                fechaNac = solicitarCampoConDefault(sc, "Fecha de nacimiento", ValidadorHuesped::validarFecha, fechaNac);
                email = solicitarCampoConDefault(sc, "Email", ValidadorHuesped::validarEmail, email);
                ocupacion = solicitarCampoConDefault(sc, "Ocupación", ValidadorHuesped::validarOcupacion, ocupacion);
                nacionalidad = solicitarCampoConDefault(sc, "Nacionalidad", ValidadorHuesped::validarNacionalidad, nacionalidad);

                // Dirección
                calle = solicitarCampoConDefault(sc, "Calle", ValidadorHuesped::validarCalle, calle);
                numero = solicitarCampoConDefault(sc, "Número", ValidadorHuesped::validarNumero, numero);
                departamento = solicitarCampoConDefault(sc, "Departamento", ValidadorHuesped::validarDepartamento, departamento);
                piso = solicitarCampoConDefault(sc, "Piso", ValidadorHuesped::validarPiso, piso);
                codigoPostal = solicitarCampoConDefault(sc, "Código Postal", ValidadorHuesped::validarCodigoPostal, codigoPostal);
                localidad = solicitarCampoConDefault(sc, "Localidad", ValidadorHuesped::validarLocalidad, localidad);
                provincia = solicitarCampoConDefault(sc, "Provincia", ValidadorHuesped::validarProvincia, provincia);
                pais = solicitarCampoConDefault(sc, "País", ValidadorHuesped::validarPais, pais);

                // Submenú confirmación
                boolean enSubmenu = true;
                while (enSubmenu) {
                    System.out.println("\n1. Siguiente (confirmar alta)");
                    System.out.println("2. Cancelar");
                    String opcion = sc.nextLine().trim();
                    switch (opcion) {
                        case "1": {
                            HuespedDTO existente = huespedDAO.buscarHuespedPorDni(dni);
                            HuespedDTO nuevoHuesped = new HuespedDTO(
                                nombre, apellido, tipoDoc, dni, posIVA, cuit, telefono,
                                fechaNac, email, ocupacion, nacionalidad,
                                new DireccionDTO(calle, numero, departamento, piso, codigoPostal, localidad, provincia, pais)
                            );

                            if (existente != null) {
                                System.out.println("Huésped existente: " + existente.getNombre() + " " + existente.getApellido());
                                System.out.println("1) Reemplazar  2) Corregir");
                                String opcionDup = sc.nextLine().trim();
                                if ("1".equals(opcionDup)) {
                                    huespedDAO.guardarOHuespedReemplazando(nuevoHuesped);
                                    enSubmenu = false;
                                    terminarAlta = true;
                                    cargar_otro = preguntarCargarOtro(sc);
                                } else {
                                    enSubmenu = false; // volver al formulario
                                }
                            } else {
                                huespedDAO.guardarOHuespedReemplazando(nuevoHuesped);
                                enSubmenu = false;
                                terminarAlta = true;
                                cargar_otro = preguntarCargarOtro(sc);
                            }
                            break;
                        }
                        case "2": {
                            enSubmenu = false; // volver al formulario o cancelar según lógica
                            break;
                        }
                        default:
                            System.out.println("Opción inválida");
                    }
                }
            }
        }
    }

    // Método para pedir y validar campo (igual que antes)
    @FunctionalInterface
    interface Validador { 
        boolean validar(String valor); 
    }
    
    private String solicitarCampoConDefault(Scanner sc, String etiqueta, Validador validador, String valorActual) {
        String prompt = (valorActual == null || valorActual.isEmpty()) ? etiqueta + ": " : String.format("%s [%s]: ", etiqueta, valorActual);
        String valor;
        boolean valido;
        do {
            System.out.print(prompt);
            valor = sc.nextLine().trim();
            if (valor.isEmpty() && valorActual != null) valor = valorActual;
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