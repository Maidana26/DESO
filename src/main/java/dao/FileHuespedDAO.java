package dao;

import models.*;
import java.io.*;
import java.nio.file.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class FileHuespedDAO implements HuespedDAO {

    private static final String RUTA_ARCHIVO = "data/ListaHuespedes.csv";
    private final Path archivo = Paths.get(RUTA_ARCHIVO);
    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    // ======================================================
    // GUARDAR O REEMPLAZAR HUÉSPED EXISTENTE / AGREGAR NUEVO
    // ======================================================
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
            lineas.add(convertirAHuespedCSV(nuevoHuesped)); // agregar nuevo
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

    // ======================================================
    // BUSCAR HUÉSPED POR DNI
    // ======================================================
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

    // ======================================================
    // CONVERSIÓN HUESPED → CSV
    // ======================================================
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

    // ======================================================
    // LISTAR TODOS LOS HUÉSPEDES
    // ======================================================
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

    // ======================================================
    // PARSEAR UNA LÍNEA CSV → OBJETO HuespedDTO
    // ======================================================
    private HuespedDTO parseHuesped(String linea) {
        String[] p = linea.split(",", -1);
        return parseHuesped(p);
    }

    private HuespedDTO parseHuesped(String[] datos) {
        HuespedDTO h = new HuespedDTO();
        try {
            h.setNombre(datos[0].trim());
            h.setApellido(datos[1].trim());
            h.setTipoDeDocumento(datos[2].trim());
            h.setNumeroDocumento(datos[3].trim());
            h.setPosicionFrenteIVA(datos[4].trim());
            h.setCuit(datos[5].trim());
            h.setTelefono(datos[6].trim());
            h.setFechaNacimiento(datos[7].trim());
            h.setEmail(datos[8].trim());
            h.setOcupacion(datos[9].trim());
            h.setNacionalidad(datos[10].trim());

            DireccionDTO direccion = new DireccionDTO();
            direccion.setCalle(datos[11].trim());
            direccion.setNumero(datos[12].trim());
            direccion.setDepartamento(datos[13].trim());
            direccion.setPiso(datos[14].trim());
            direccion.setCodigoPostal(datos[15].trim());
            direccion.setLocalidad(datos[16].trim());
            direccion.setProvincia(datos[17].trim());
            direccion.setPais(datos[18].trim());

            h.setDireccion(direccion);
            h.setAlojadoAlgunaVez(datos.length > 19 && datos[19].trim().equals("1"));

        } catch (Exception e) {
            System.err.println("Error parseando línea de huésped: " + Arrays.toString(datos));
        }
        return h;
    }

    // ======================================================
    // ELIMINAR HUÉSPED DEL ARCHIVO
    // ======================================================
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
