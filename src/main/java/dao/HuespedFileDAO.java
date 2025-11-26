package dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import models.HuespedDTO;
import models.DireccionDTO;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class HuespedFileDAO implements HuespedDAO {
    
    private static final String RUTA_ARCHIVO = "data/ListaHuespedes.csv";
    
    @Override
    public void guardarOReemplazarHuesped(HuespedDTO nuevoHuesped) {
        List<String> lineas = new ArrayList<>();
        boolean reemplazado = false;

        try (BufferedReader br = new BufferedReader(new FileReader(RUTA_ARCHIVO))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] campos = linea.split(",");
                if (campos.length > 3 && campos[3].equals(nuevoHuesped.getNumeroDocumento())) {
                    // Reemplazamos la línea por el nuevo huésped
                    lineas.add(convertirHuespedCSV(nuevoHuesped));
                    reemplazado = true;
                } else {
                    lineas.add(linea);
                }
            }
        } catch (IOException e) {
            System.out.println("⚠️ No se pudo leer el archivo, se creará uno nuevo.");
        }

        // Si no se reemplazó, agregamos el huésped nuevo al final
        if (!reemplazado) {
            lineas.add(convertirHuespedCSV(nuevoHuesped));
        }

        try (PrintWriter pw = new PrintWriter(new FileWriter(RUTA_ARCHIVO))) {
            for (String l : lineas) {
                pw.println(l);
            }
            System.out.println(reemplazado ? "🔁 Huésped actualizado." : "✅ Huésped agregado.");
        } catch (IOException e) {
            System.err.println("❌ Error al escribir el archivo: " + e.getMessage());
        }
    }
    
    // ✅ Busca un huésped por DNI y devuelve su instancia, o null si no existe
    @Override
    public HuespedDTO buscarHuespedPorDni(String dniBuscado) {
        try (BufferedReader br = new BufferedReader(new FileReader(RUTA_ARCHIVO))) {
            return br.lines() // Stream<String>
                    .map(linea -> linea.split(",")) // Stream<String[]>
                    .filter(campos -> campos.length > 3 && campos[3].equals(dniBuscado)) // filtro por DNI
                    .findFirst() // devuelve el primero que cumpla
                    .map(datos -> { // convierte a HuespedDTO
                        HuespedDTO h = new HuespedDTO();
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

                        DireccionDTO d = new DireccionDTO();
                        d.setCalle(datos[11].trim());
                        d.setNumero(datos[12].trim());
                        d.setDepartamento(datos[13].trim());
                        d.setPiso(datos[14].trim());
                        d.setCodigoPostal(datos[15].trim());
                        d.setLocalidad(datos[16].trim());
                        d.setProvincia(datos[17].trim());
                        d.setPais(datos[18].trim());
                        h.setDireccion(d);
                        return h;
                    })
                    .orElse(null); // si no lo encuentra
        } catch (IOException e) {
            System.err.println("❌ Error al leer archivo CSV: " + e.getMessage());
            return null;
        }
    }
    
    @Override
    public String convertirHuespedCSV(HuespedDTO h) {
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
            h.getDireccion().getPais()
        );
    }
}