package service;

import dao.*;
import java.io.*;
import java.util.*;
import models.*;
import exceptions.*;
import ui.*;



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
                DarDeAltaHuespedUI a = new DarDeAltaHuespedUI();
                System.out.println("No se encontraron huéspedes con esos criterios, se procede a dar de alta huesped");
                a.darDeAltaHuespedUI(sc);
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
                case 1 -> {
                    System.out.print("Ingrese el número del huésped a modificar: ");
                    int seleccion = sc.nextInt();
                    sc.nextLine();
                    if (seleccion > 0 && seleccion <= encontrados.size()) {
                        modificarHuesped(seleccion, sc);
                    } else {
                        System.out.println("Número inválido.");
                    }
                }

                case 2 -> {
                    System.out.println("No selecciono ningun huesped, se ejecuta dar de alta huesped");
                    DarDeAltaHuespedUI a = new DarDeAltaHuespedUI();
                    a.darDeAltaHuespedUI(sc);
                }

                default -> System.out.println("Opción no válida.");
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
     * @param numeroLinea
     * @param sc
     */
   public void modificarHuesped(int numeroLinea, Scanner sc) {
        String rutaLista = "data/ListaHuespedes.csv";
        String rutaEncontrados = "data/huespedesEncontrados.csv";
        List<String> listaCompleta = new ArrayList<>();

        try {
            // Leer archivo completo
            try (BufferedReader br = new BufferedReader(new FileReader(rutaLista))) {
                String linea;
                while ((linea = br.readLine()) != null) listaCompleta.add(linea);
            } catch (FileNotFoundException fnf) {
                System.out.println("El archivo principal no existe.");
                return;
            }

            // Leer archivo de encontrados
            List<String> encontrados = new ArrayList<>();
            try (BufferedReader br2 = new BufferedReader(new FileReader(rutaEncontrados))) {
                String linea;
                while ((linea = br2.readLine()) != null) encontrados.add(linea);
            } catch (FileNotFoundException fnf) {
                System.out.println("No se encontró el archivo de resultados ('huespedesEncontrados.csv').");
                return;
            }

            if (numeroLinea < 1 || numeroLinea > encontrados.size()) {
                System.out.println("Número de línea inválido.");
                return;
            }

            String lineaSeleccionada = encontrados.get(numeroLinea - 1);
            String[] partes = lineaSeleccionada.split(",", -1); // mantiene campos vacíos

            // Aseguro 20 campos
            final int EXPECTED = 20;
            if (partes.length < EXPECTED) {
                String[] tmp = new String[EXPECTED];
                for (int i = 0; i < EXPECTED; i++) tmp[i] = (i < partes.length) ? partes[i] : "";
                partes = tmp;
            }

            System.out.println("\n--- MODIFICAR HUÉSPED ---");
            System.out.println("Dejar vacío para mantener el valor actual.");

            // Inicializo valores actuales (trim para seguridad)
            String nombre = partes[0].trim();
            String apellido = partes[1].trim();
            String tipoDoc = partes[2].trim();
            String nroDoc = partes[3].trim();
            String posIVA = partes[4].trim();
            String cuit = partes[5].trim();
            String telefono = partes[6].trim();
            String fechaNac = partes[7].trim();
            String email = partes[8].trim();
            String ocupacion = partes[9].trim();
            String nacionalidad = partes[10].trim();
            String calle = partes[11].trim();
            String numeroCalle = partes[12].trim();
            String departamento = partes[13].trim();
            String piso = partes[14].trim();
            String codigoPostal = partes[15].trim();
            String localidad = partes[16].trim();
            String provincia = partes[17].trim();
            String pais = partes[18].trim();
            String alojado = partes[19].trim();

            // 1) Nombre (obligatorio)
            nombre = pedirCampo(sc, nombre, "Nombre", util.ValidadorHuesped::validarNombre, true);

            // 2) Apellido (obligatorio)
            apellido = pedirCampo(sc, apellido, "Apellido", util.ValidadorHuesped::validarApellido, true);

            // 3) Tipo de documento (obligatorio)
            tipoDoc = pedirCampo(sc, tipoDoc, "Tipo Documento [DNI/LE/LC/Pasaporte]", util.ValidadorHuesped::validarTipoDocumento, true);

            // 4) Número de documento (obligatorio) + duplicado
            while (true) {
                String entrada = pedirCampo(sc, nroDoc, "Número Documento (8 dígitos)", util.ValidadorHuesped::validarNumeroDocumento, true);
                // si volverá a pedir y entrada es la misma que actual, queda
                nroDoc = entrada;

                // Copias finales para lambda
                final String nroDocFinal = nroDoc;
                final String lineaSeleccionadaFinal = lineaSeleccionada;

                boolean existe = listaCompleta.stream()
                        .anyMatch(l -> {
                            String[] d = l.split(",", -1);
                            return d.length > 3 && d[3].trim().equals(nroDocFinal) && !l.equals(lineaSeleccionadaFinal);
                        });

                if (existe) {
                    System.out.println("ATENCIÓN: Ese número de documento ya existe en el sistema.");
                    System.out.println("1) Continuar con este DNI (aceptar duplicado)");
                    System.out.println("2) Reingresar DNI");
                    System.out.print("Opción: ");
                    String opt = sc.nextLine().trim();
                    if (opt.equals("1")) {
                        break;
                    } else {
                        // loop para reingresar
                        continue;
                    }
                } else {
                    break;
                }
            }

            // 5) Posición frente al IVA (obligatorio)
            posIVA = pedirCampo(sc, posIVA, "Posición frente al IVA", util.ValidadorHuesped::validarPosicionFrenteIVA, true);

            // 6) CUIT (opcional: vacío o 11 dígitos)
            cuit = pedirCampo(sc, cuit, "CUIT (11 dígitos o vacío)", util.ValidadorHuesped::validarCUIT, false);

            // 7) Teléfono (obligatorio)
            telefono = pedirCampo(sc, telefono, "Teléfono (7-15 dígitos)", util.ValidadorHuesped::validarTelefono, true);

            // 8) Fecha de nacimiento (obligatorio dd/mm/aaaa)
            fechaNac = pedirCampo(sc, fechaNac, "Fecha de Nacimiento (dd/mm/aaaa)", util.ValidadorHuesped::validarFecha, true);

            // 9) Email (opcional)
            email = pedirCampo(sc, email, "Email", util.ValidadorHuesped::validarEmail, false);

            // 10) Ocupación (obligatorio)
            ocupacion = pedirCampo(sc, ocupacion, "Ocupación", util.ValidadorHuesped::validarOcupacion, true);

            // 11) Nacionalidad (obligatorio)
            nacionalidad = pedirCampo(sc, nacionalidad, "Nacionalidad", util.ValidadorHuesped::validarNacionalidad, true);

            // 12) Calle (obligatorio)
            calle = pedirCampo(sc, calle, "Calle", util.ValidadorHuesped::validarCalle, true);

            // 13) Número de calle (obligatorio numeric)
            numeroCalle = pedirCampo(sc, numeroCalle, "Número de calle", util.ValidadorHuesped::validarNumero, true);

            // 14) Departamento (opcional)
            departamento = pedirCampo(sc, departamento, "Departamento", util.ValidadorHuesped::validarDepartamento, false);

            // 15) Piso (opcional)
            piso = pedirCampo(sc, piso, "Piso", util.ValidadorHuesped::validarPiso, false);

            // 16) Código Postal (obligatorio)
            codigoPostal = pedirCampo(sc, codigoPostal, "Código Postal", util.ValidadorHuesped::validarCodigoPostal, true);

            // 17) Localidad (obligatorio)
            localidad = pedirCampo(sc, localidad, "Localidad", util.ValidadorHuesped::validarLocalidad, true);

            // 18) Provincia (obligatorio)
            provincia = pedirCampo(sc, provincia, "Provincia", util.ValidadorHuesped::validarProvincia, true);

            // 19) País (obligatorio)
            pais = pedirCampo(sc, pais, "País", util.ValidadorHuesped::validarPais, true);

            // 20) Alojado (0/1) obligatorio
            while (true) {
                System.out.print("Se alojo (0 = No, 1 = Si) [" + (alojado.isEmpty() ? "-" : alojado) + "]: ");
                String in = sc.nextLine().trim();
                if (in.isEmpty()) {
                    if (alojado == null || alojado.isEmpty()) {
                        System.out.println("Debe indicar 0 o 1.");
                        continue;
                    }
                    break;
                }
                if (in.equals("0") || in.equals("1")) {
                    alojado = in;
                    break;
                } else {
                    System.out.println("Ingrese 0 o 1.");
                }
            }

            // Reconstruir línea modificada
            String lineaModificada = String.join(",", Arrays.asList(
                    nombre, apellido, tipoDoc, nroDoc, posIVA, cuit, telefono, fechaNac, email, ocupacion,
                    nacionalidad, calle, numeroCalle, departamento, piso, codigoPostal, localidad, provincia, pais, alojado
            ));

            // Menú final (switch)
            System.out.println("\nOpciones:");
            System.out.println("1) Confirmar modificación");
            System.out.println("2) Cancelar modificación");
            System.out.println("3) Borrar huésped");
            System.out.print("Opción: ");
            String optFinal = sc.nextLine().trim();
            switch (optFinal) {
                case "1" -> {
                    // Reemplazo la primera ocurrencia exacta de la línea original
                    boolean reemplazado = false;
                    for (int i = 0; i < listaCompleta.size(); i++) {
                        if (listaCompleta.get(i).equals(lineaSeleccionada)) {
                            listaCompleta.set(i, lineaModificada);
                            reemplazado = true;
                            break;
                        }
                    }
                    if (reemplazado) {
                        try (BufferedWriter bw = new BufferedWriter(new FileWriter(rutaLista))) {
                            for (String l : listaCompleta) {
                                bw.write(l);
                                bw.newLine();
                            }
                        }
                        System.out.println("Modificación guardada correctamente.");
                    } else {
                        System.out.println("No se encontró la línea original para reemplazar.");
                    }
                }

                case "2" -> System.out.println("Modificación cancelada. No se realizaron cambios.");

                case "3" -> {
                    System.out.println("Llamando a la función de dar de baja huesped");
                    var huespedDAO = new FileHuespedDAO();
                    var gestorHuesped = new GestorHuesped(huespedDAO);
                    var ui = new BajaHuespedUI(gestorHuesped);
                    ui.mostrarPantallaBaja();
                }

                default -> System.out.println("Opción inválida. No se realizaron cambios.");
            }

        } catch (IOException e) {
            System.out.println("Error modificando el huésped: " + e.getMessage());
        }
    }

/**
 * Función auxiliar: pide un campo con validación.
 *
 * @param sc Scanner
 * @param valorActual valor actual del campo (si el usuario deja vacío se conserva)
 * @param etiqueta texto a mostrar al pedir el campo
 * @param validador Predicate<String> que devuelve true si el valor es válido
 * @param obligatorio si true, no acepta valor vacío
 * @return valor final válido
 */
private String pedirCampo(Scanner sc, String valorActual, String etiqueta,
                          java.util.function.Predicate<String> validador, boolean obligatorio) {
    while (true) {
        System.out.print(etiqueta + " (" + (valorActual == null || valorActual.isEmpty() ? "-" : valorActual) + "): ");
        String entrada = sc.nextLine().trim();
        if (entrada.isEmpty()) {
            if (obligatorio && (valorActual == null || valorActual.isEmpty())) {
                System.out.println(etiqueta + " es obligatorio.");
                continue;
            }
            return (valorActual == null) ? "" : valorActual;
        }
        if (validador.test(entrada)) return entrada;
        // si no pasa validación, el validador ya imprime el mensaje de error
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