package service;

import dao.*;
import java.io.*;
import java.util.*;
import models.*;
import exceptions.ExcepcionHuesped;


import java.util.Scanner;

public class GestorHuesped {
    private HuespedDAO hDAO;
    private FileHuespedDAO huespedDAO = new FileHuespedDAO();

    public GestorHuesped(HuespedDAO dao) {
        this.hDAO = dao;
    }
    
    @FunctionalInterface
    public interface Validador {
        boolean validar(String valor);
    }

        
    public String solicitarCampoConDefault(Scanner sc, String etiqueta, 
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

        
    public HuespedDTO buscarHuespedPorDni(String dniBuscado) {
        return hDAO.buscarHuespedPorDni(dniBuscado);
    }

    public void DarDeAltaHuesped(String nombre,
        String apellido,
        String tipoDeDocumento,
        String numeroDocumento,
        String posicionFrenteIVA,
        String cuit,
        String telefono,
        String fechaNacimiento,
        String email,
        String ocupacion,
        String nacionalidad,
        String calle,
        String numero,
        String departamento,
        String piso,
        String codigoPostal,
        String localidad,
        String provincia,
        String pais, boolean alojado){
            DireccionDTO nueva_direccion = new DireccionDTO(calle, numero, departamento, piso, codigoPostal, localidad, provincia, pais);
            HuespedDTO nuevo_huesped = new HuespedDTO(nombre, apellido, tipoDeDocumento, numeroDocumento, posicionFrenteIVA, cuit, telefono, fechaNacimiento, email, ocupacion, nacionalidad, nueva_direccion, alojado);
            hDAO.guardarOHuespedReemplazando(nuevo_huesped);
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
    
    public HuespedDTO buscarHuesped(String nroDoc) throws ExcepcionHuesped {
    HuespedDTO h = hDAO.buscarHuespedPorDni(nroDoc);
    if (h == null) {
        throw new ExcepcionHuesped("El huésped no existe en el sistema.");
    }
    return h;
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
     public void darBajaHuesped(String tipoDoc, String nroDoc) throws ExcepcionHuesped {
        var huesped = buscarHuespedPorDni(nroDoc);

        if (huesped.getAlojadoAlgunaVez()) {
            throw new ExcepcionHuesped("El huésped no puede ser eliminado pues se ha alojado en el hotel en alguna oportunidad.");
        }

        huespedDAO.eliminar(huesped);
    }
}