package dao;

import models.*;
import java.io.*;
import java.nio.file.*;
// import java.time.LocalDate;
// import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import exceptions.ExcepcionHuesped;


/**
 * Implementación concreta del patrón DAO (Data Access Object) para la entidad {@link HuespedDTO},
 * utilizando archivos CSV como medio de persistencia.
 * Esta clase permite realizar operaciones CRUD (crear, leer, actualizar, eliminar)
 * sobre huéspedes almacenados en un archivo plano. El formato del archivo es:
 * <pre>
 * nombre,apellido,tipoDoc,nroDoc,IVA,CUIT,teléfono,fechaNacimiento,email,ocupación,
 * nacionalidad,calle,número,departamento,piso,códigoPostal,localidad,provincia,pais,alojadoAlgunaVez
 * </pre>
 *
 * <p><b>Responsabilidad:</b> Gestionar el almacenamiento y recuperación de información
 * de huéspedes de forma persistente mediante un archivo CSV.</p>
 *
 */
public class FileHuespedDAO implements HuespedDAO {

    /** Ruta predeterminada del archivo CSV donde se almacenan los huéspedes. */
    private static final String RUTA_ARCHIVO = "data/ListaHuespedes.csv";
    
    /** Representación del archivo como un objeto {@link Path}. */
    private final Path archivo = Paths.get(RUTA_ARCHIVO);
    // private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    // -----------------------------
    // MÉTODOS DE PERSISTENCIA
    // -----------------------------
    
    /**
     * Guarda un huésped en el archivo CSV.  
     * Si el huésped ya existe (mismo número de documento), lo reemplaza.
     *
     * @param nuevoHuesped el objeto {@link HuespedDTO} a guardar o reemplazar.
     */
    @Override
    public void guardarOHuespedReemplazando(HuespedDTO nuevoHuesped) {
        List<String> lineas = new ArrayList<>();
        boolean reemplazado = false;

        try (BufferedReader br = new BufferedReader(new FileReader(RUTA_ARCHIVO))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] campos = linea.split(",");
                if (campos.length > 3 && campos[3].equals(nuevoHuesped.getNumeroDocumento())) {
                    lineas.add(convertirAHuespedCSV(nuevoHuesped)); // reemplazar
                    reemplazado = true;
                } else {
                    lineas.add(linea);
                }
            }
        } catch (IOException e) {
            System.out.println("No se pudo leer el archivo, se creará uno nuevo.");
        }

        if (!reemplazado) {
            lineas.add(convertirAHuespedCSV(nuevoHuesped)); // agrega nuevo
        }

        try (PrintWriter pw = new PrintWriter(new FileWriter(RUTA_ARCHIVO))) {
            for (String l : lineas) {
                pw.println(l);
            }
            System.out.println(reemplazado ? "Huésped actualizado." : "Huésped agregado.");
        } catch (IOException e) {
            System.err.println("Error al escribir el archivo: " + e.getMessage());
        }
    }
    
    // ------------------------------
    // MÉTODOS DE BÚSQUEDA Y CONSULTA
    // ------------------------------
    
    /**
     * Busca un huésped en el archivo CSV por su número de documento.
     *
     * @param dniBuscado número de documento a buscar.
     * @return un objeto {@link HuespedDTO} si se encuentra; de lo contrario, {@code null}.
     */
    // === BUSCAR HUÉSPED POR DNI ===
    @Override
    public HuespedDTO buscarHuespedPorDni(String dniBuscado) {
        try (BufferedReader br = new BufferedReader(new FileReader(RUTA_ARCHIVO))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length < 20) continue; // validación

                String dni = datos[3].trim(); // columna 4: DNI
                if (dni.equals(dniBuscado)) {
                    return parseHuesped(datos);
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo CSV: " + e.getMessage());
        }
        return null;
    }
    
    /**
    * Busca un huésped por tipo y número de documento en el archivo CSV.
    *
    * <p>Este método maneja correctamente campos vacíos y valores entre comillas.
    * Retorna el primer huésped que coincida exactamente con el tipo y número de documento.</p>
    *
    * @param tipoDoc el tipo de documento (DNI, Pasaporte, LC, LE, etc.).
    * @param nroDoc  el número de documento del huésped.
    * @return el objeto {@link HuespedDTO} que coincide, o {@code null} si no se encuentra.
    */
    @Override
    public HuespedDTO buscarHuespedPorTipoYNumero(String tipoDoc, String nroDoc) {
       try (BufferedReader br = new BufferedReader(new FileReader(RUTA_ARCHIVO))) {
           String linea;
           while ((linea = br.readLine()) != null) {
               // separar manteniendo campos vacíos
               String[] datos = linea.split(",", -1);

               if (datos.length < 4) continue; // necesitamos al menos tipo y número

               String tipo = datos[2].replace("\"", "").trim();   // quita comillas y espacios
               String numero = datos[3].replace("\"", "").trim();

               if (tipo.equalsIgnoreCase(tipoDoc.trim()) && numero.equals(nroDoc.trim())) {
                   return parseHuesped(datos);
               }
           }
       } catch (IOException e) {
           System.err.println("Error al leer archivo CSV: " + e.getMessage());
       }
       return null;
   }


    /**
     * Devuelve una lista con todos los huéspedes registrados en el archivo CSV.
     *
     * @return lista de {@link HuespedDTO}; lista vacía si el archivo no existe o está vacío.
     */
    @Override
    public List<HuespedDTO> listar() {
        try {
            if (!Files.exists(archivo)) return new ArrayList<>();
            return Files.lines(archivo)
                    .map(String::trim)
                    .filter(line -> !line.isEmpty())
                    .map(this::parseHuesped)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException("Error leyendo archivo de huéspedes", e);
        }
    }
    
    // ------------------------------
    // MÉTODOS AUXILIARES
    // ------------------------------
    
     /**
     * Convierte un objeto {@link HuespedDTO} en una línea CSV lista para guardar.
     *
     * @param h el huésped a convertir.
     * @return una cadena de texto con los campos separados por coma.
     */
    @Override
    public String convertirAHuespedCSV(HuespedDTO h) {
        return String.join(",",
                h.getNombre(),
                h.getApellido(),
                h.getTipoDeDocumento(),
                h.getNumeroDocumento(),
                h.getPosicionFrenteIVA(),
                h.getCuit(),
                h.getTelefono(),
                h.getFechaNacimiento(),
                h.getEmail(),
                h.getOcupacion(),
                h.getNacionalidad(),
                h.getDireccion().getCalle(),
                h.getDireccion().getNumero(),
                h.getDireccion().getDepartamento(),
                h.getDireccion().getPiso(),
                h.getDireccion().getCodigoPostal(),
                h.getDireccion().getLocalidad(),
                h.getDireccion().getProvincia(),
                h.getDireccion().getPais(),
                h.getAlojadoAlgunaVez() ? "1" : "0"
        );
    }

    /**
     * Convierte una línea CSV a un objeto {@link HuespedDTO}.
     *
     * @param linea línea completa del archivo CSV.
     * @return el objeto {@link HuespedDTO} correspondiente.
     */
    private HuespedDTO parseHuesped(String linea) {
        String[] p = linea.split(",", -1);
        return parseHuesped(p);
    }

    /**
    * Convierte un arreglo de cadenas de un CSV a un objeto {@link HuespedDTO}.
    *
    * <p>Maneja correctamente campos vacíos y opcionales, inicializando valores faltantes
    * con cadenas vacías o valores por defecto.</p>
    *
    * @param datos arreglo con los campos del huésped.
    * @return objeto {@link HuespedDTO} completo.
    */
    private HuespedDTO parseHuesped(String[] datos) {
       HuespedDTO h = new HuespedDTO();

       h.setNombre(datos.length > 0 ? datos[0].replace("\"","").trim() : "");
       h.setApellido(datos.length > 1 ? datos[1].replace("\"","").trim() : "");
       h.setTipoDeDocumento(datos.length > 2 ? datos[2].replace("\"","").trim() : "");
       h.setNumeroDocumento(datos.length > 3 ? datos[3].replace("\"","").trim() : "");
       h.setPosicionFrenteIVA(datos.length > 4 ? datos[4].replace("\"","").trim() : "");
       h.setCuit(datos.length > 5 ? datos[5].replace("\"","").trim() : "");
       h.setTelefono(datos.length > 6 ? datos[6].replace("\"","").trim() : "");
       h.setFechaNacimiento(datos.length > 7 ? datos[7].replace("\"","").trim() : "");
       h.setEmail(datos.length > 8 ? datos[8].replace("\"","").trim() : "");
       h.setOcupacion(datos.length > 9 ? datos[9].replace("\"","").trim() : "");
       h.setNacionalidad(datos.length > 10 ? datos[10].replace("\"","").trim() : "");
       h.setAlojadoAlgunaVez(datos.length > 19 && "1".equals(datos[19].replace("\"","").trim()));

       // Dirección opcional
       DireccionDTO direccion = new DireccionDTO();
       direccion.setCalle(datos.length > 11 ? datos[11].replace("\"","").trim() : "");
       direccion.setNumero(datos.length > 12 ? datos[12].replace("\"","").trim() : "");
       direccion.setDepartamento(datos.length > 13 ? datos[13].replace("\"","").trim() : "");
       direccion.setPiso(datos.length > 14 ? datos[14].replace("\"","").trim() : "");
       direccion.setCodigoPostal(datos.length > 15 ? datos[15].replace("\"","").trim() : "");
       direccion.setLocalidad(datos.length > 16 ? datos[16].replace("\"","").trim() : "");
       direccion.setProvincia(datos.length > 17 ? datos[17].replace("\"","").trim() : "");
       direccion.setPais(datos.length > 18 ? datos[18].replace("\"","").trim() : "");
       h.setDireccion(direccion);

       return h;
    }


    // ------------------------------
    // MÉTODOS DE ELIMINACIÓN
    // ------------------------------
    
    /**
     * Elimina un huésped del archivo CSV según su tipo y número de documento.
     *
     * @param huesped el huésped a eliminar.
     * @throws RuntimeException si ocurre un error de escritura durante la eliminación.
     */
    @Override
    public void eliminar(HuespedDTO huesped) {
        List<HuespedDTO> todos = listar().stream()
                .filter(h -> !(h.getTipoDeDocumento().equalsIgnoreCase(huesped.getTipoDeDocumento())
                        && h.getNumeroDocumento().equals(huesped.getNumeroDocumento())))
                .collect(Collectors.toList());

        try (BufferedWriter writer = Files.newBufferedWriter(archivo)) {
            for (HuespedDTO h : todos) {
                writer.write(convertirAHuespedCSV(h));
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Error al eliminar huésped del archivo", e);
        }
    }
}
