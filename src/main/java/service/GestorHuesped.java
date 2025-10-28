package service;

import dao.*;
import java.io.*;
import java.util.*;
import models.*;
import exceptions.ExcepcionHuesped;
import java.util.Scanner;

/**
 * GestorHuesped se encarga de manejar la lógica relacionada con
 * los huéspedes: alta, modificación, búsqueda y baja.
 * Interactúa con el DAO correspondiente y con archivos CSV
 * para persistencia de datos.
 */
public class GestorHuesped {
    // DAO de huéspedes para operaciones persistentes
    private HuespedDAO hDAO;
    // DAO basado en archivos para operaciones adicionales
    private FileHuespedDAO huespedDAO = new FileHuespedDAO();

    /**
     * Constructor que recibe un DAO de huéspedes.
     * @param dao objeto HuespedDAO para persistencia
     */
    public GestorHuesped(HuespedDAO dao) {
        this.hDAO = dao;
    }
    
    /**
     * Interfaz funcional para validar campos ingresados por el usuario.
     */
    @FunctionalInterface
    public interface Validador {
        boolean validar(String valor);
    }

    /**
     * Solicita un valor al usuario, mostrando un valor por defecto si existe.
     * Permite validar el ingreso mediante un validador.
     *
     * @param sc Scanner para leer datos desde consola
     * @param etiqueta etiqueta que se muestra al usuario
     * @param validador función para validar el valor ingresado
     * @param valorActual valor por defecto que se conserva si el usuario deja vacío
     * @return valor ingresado o valorActual si se dejó vacío
     */ 
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

    /**
     * Busca un huésped por su DNI utilizando el DAO.
     * @param dniBuscado DNI del huésped a buscar
     * @return objeto HuespedDTO encontrado, o null si no existe
     */
    public HuespedDTO buscarHuespedPorDni(String dniBuscado) {
        return hDAO.buscarHuespedPorDni(dniBuscado);
    }
    
    /**
     * Da de alta un nuevo huésped en el sistema.
     * Construye un objeto HuespedDTO con todos los parámetros y lo guarda/reemplaza en el DAO.
     *
     * @param nombre Nombre del huésped
     * @param apellido Apellido del huésped
     * @param tipoDeDocumento Tipo de documento (DNI, PAS, etc.)
     * @param numeroDocumento Número del documento
     * @param posicionFrenteIVA Posición frente al IVA
     * @param cuit CUIT del huésped
     * @param telefono Teléfono de contacto
     * @param fechaNacimiento Fecha de nacimiento
     * @param email Email de contacto
     * @param ocupacion Ocupación laboral
     * @param nacionalidad Nacionalidad
     * @param calle Calle de la dirección
     * @param numero Número de la dirección
     * @param departamento Departamento de la dirección
     * @param piso Piso de la dirección
     * @param codigoPostal Código postal
     * @param localidad Localidad
     * @param provincia Provincia
     * @param pais País
     * @param alojado Indica si el huésped está alojado actualmente
     */
    public void DarDeAltaHuesped(String nombre,String apellido,String tipoDeDocumento,
                                 String numeroDocumento,String posicionFrenteIVA,String cuit,
                                 String telefono,String fechaNacimiento,String email,
                                 String ocupacion,String nacionalidad,String calle,
                                 String numero,String departamento,String piso,
                                 String codigoPostal,String localidad,String provincia,String pais, boolean alojado){
        // Construye dirección    
        DireccionDTO nueva_direccion = new DireccionDTO(calle, numero, departamento, piso, codigoPostal, localidad, provincia, pais);
        // Construye huésped completo    
        HuespedDTO nuevo_huesped = new HuespedDTO(nombre, apellido, tipoDeDocumento, numeroDocumento, posicionFrenteIVA, cuit, telefono, fechaNacimiento, email, ocupacion, nacionalidad, nueva_direccion, alojado);
        // Guardar o reemplazar en DAO    
        hDAO.guardarOHuespedReemplazando(nuevo_huesped);
    }

    /**
     * Busca huéspedes en el archivo CSV que coincidan con los parámetros dados.
     * Muestra resultados numerados y permite seleccionar un huésped para modificar.
     */
    public void buscarHuesped(String nombre, String apellido, String tipoDocumento, String numeroDocumento, Scanner sc) {
        String rutaLista = "data/ListaHuespedes.csv";
        String rutaEncontrados = "data/huespedesEncontrados.csv";
        List<String> encontrados = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(rutaLista))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(",", -1); // <-- mantiene todas las columnas
                if (partes.length < 19) continue;

                String hNombre = partes[0].trim();
                String hApellido = partes[1].trim();
                String hTipoDoc = partes[2].trim();
                String hNumeroDoc = partes[3].trim();

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

            try (BufferedWriter bw = new BufferedWriter(new FileWriter(rutaEncontrados))) {
                for (String l : encontrados) {
                    bw.write(l);
                    bw.newLine();
                }
            }

            System.out.println("\n--- RESULTADOS DE LA BÚSQUEDA ---");
            if (encontrados.isEmpty()) {
                System.out.println("No se encontraron huéspedes con esos criterios.");
                return;
            }

            int index = 1;
            for (String l : encontrados) {
                String[] partes = l.split(",", -1);
                if (partes.length >= 4) {
                    System.out.println(index + " - " + partes[0] + " " + partes[1] + " - " + partes[2] + " " + partes[3]);
                }
                index++;
            }

            System.out.println("Seleccione una opción:");
            System.out.println("1. Modificar huésped");
            System.out.println("2. Siguiente");
            System.out.println("Opción: ");
            int opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1:
                    System.out.print("Ingrese el número del huésped a modificar: ");
                    int seleccion = sc.nextInt();
                    sc.nextLine();
                    if (seleccion > 0 && seleccion <= encontrados.size()) {
                        modificarHuesped(seleccion, sc);
                    } else {
                        System.out.println("Número inválido.");
                    }
                    break;

                case 2:
                    System.out.println("→ Ir a 'Dar de alta huésped' (a implementar)");
                    // acá después conectás con tu método de alta
                    break;

                default:
                    System.out.println("Opción no válida.");
            }

        } catch (IOException e) {
            System.out.println("Error leyendo o escribiendo archivos: " + e.getMessage());
        }
    }


    
    /**
     * Busca un huésped por número de documento usando el DAO.
     * @param nroDoc número de documento del huésped
     * @return HuespedDTO encontrado
     * @throws ExcepcionHuesped si el huésped no existe
     */
    public HuespedDTO buscarHuesped(String nroDoc) throws ExcepcionHuesped {
    HuespedDTO h = hDAO.buscarHuespedPorDni(nroDoc);
    if (h == null) {
        throw new ExcepcionHuesped("El huésped no existe en el sistema.");
    }
    return h;
    }
    
    /**
     * Busca un huésped en el sistema según su tipo y número de documento.
     *
     * <p>Este método actúa como intermediario entre la capa de negocio y la capa de acceso
     * a datos, delegando la búsqueda al objeto {@link HuespedDAO}. Permite recuperar
     * la información completa de un huésped previamente registrado, identificándolo
     * de forma única mediante la combinación de tipo y número de documento.</p>
     *
     * @param tipoDoc el tipo de documento del huésped (por ejemplo, "DNI", "Pasaporte", etc.).
     * @param nroDoc el número de documento correspondiente al huésped.
     * @return el objeto {@link HuespedDTO} que coincide con el tipo y número de documento,
     *         o {@code null} si no se encuentra ningún huésped con esos datos.
     * @throws ExcepcionHuesped si ocurre un error durante la lectura o búsqueda en la fuente de datos.
     */
    public HuespedDTO buscarHuespedPorTipoYNumero(String tipoDoc, String nroDoc) throws ExcepcionHuesped {
        return huespedDAO.buscarHuespedPorTipoYNumero(tipoDoc, nroDoc);
    }
    
    /**
     * Permite modificar los datos de un huésped seleccionado de la lista temporal.
     * Los cambios se reflejan en el archivo principal CSV.
     */
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
    
    /**
     * Da de baja un huésped, si nunca se alojó en el hotel.
     * @param tipoDoc tipo de documento
     * @param nroDoc número de documento
     * @throws ExcepcionHuesped si el huésped se alojó alguna vez
     */
    public void darBajaHuesped(String tipoDoc, String nroDoc) throws ExcepcionHuesped {
        var huesped = buscarHuespedPorTipoYNumero(tipoDoc, nroDoc);

        if (huesped == null) {
            throw new ExcepcionHuesped("No se encontró ningún huésped con el tipo y número de documento indicados.");
        }

        if (huesped.getAlojadoAlgunaVez()) {
            throw new ExcepcionHuesped("El huésped no puede ser eliminado pues se ha alojado en el hotel en alguna oportunidad.");
        }

        huespedDAO.eliminar(huesped);
    }
}