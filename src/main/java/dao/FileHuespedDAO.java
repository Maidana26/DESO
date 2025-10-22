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

public class FileHuespedDAO implements HuespedDAO {
    private static final String RUTA_ARCHIVO = "C:\\Users\\lucab\\Documents\\NetBeansProjects\\DESO\\src\\main\\java\\data\\ListaHuespedes.csv";
    
    @Override
    public void guardarOHuespedReemplazando(HuespedDTO nuevoHuesped) {
        List<String> lineas = new ArrayList<>();
        boolean reemplazado = false;

        try (BufferedReader br = new BufferedReader(new FileReader(RUTA_ARCHIVO))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] campos = linea.split(",");
                if (campos.length > 3 && campos[3].equals(nuevoHuesped.getNumeroDocumento())) {
                    // Reemplazamos la línea por el nuevo huésped
                    lineas.add(convertirAHuespedCSV(nuevoHuesped));
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
            lineas.add(convertirAHuespedCSV(nuevoHuesped));
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
    
    @Override
    // ✅ Busca un huésped por DNI y devuelve su instancia, o null si no existe
    public HuespedDTO buscarHuespedPorDni(String dniBuscado) {
        try (BufferedReader br = new BufferedReader(new FileReader(RUTA_ARCHIVO))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length < 19) continue; // evita errores por líneas incompletas
                
                String dni = datos[3].trim(); // el campo 4 es el DNI

                if (dni.equals(dniBuscado)) {
                    // Crear y retornar el HuespedDTO con todos los datos
                    HuespedDTO huesped = new HuespedDTO();
                    huesped.setNombre(datos[0].trim());
                    huesped.setApellido(datos[1].trim());
                    huesped.setTipoDeDocumento(datos[2].trim());
                    huesped.setNumeroDocumento(datos[3].trim());
                    huesped.setPosicionFrenteIVA(datos[4].trim());
                    huesped.setCuit(datos[5].trim());
                    huesped.setTelefono(datos[6].trim());
                    huesped.setFechaNacimiento(datos[7].trim());
                    huesped.setEmail(datos[8].trim());
                    huesped.setOcupacion(datos[9].trim());
                    huesped.setNacionalidad(datos[10].trim());
                    
                    DireccionDTO direccion = new DireccionDTO();
                    direccion.setCalle(datos[11].trim());
                    direccion.setNumero(datos[12].trim());
                    direccion.setDepartamento(datos[13].trim());
                    direccion.setPiso(datos[14].trim());
                    direccion.setCodigoPostal(datos[15].trim());
                    direccion.setLocalidad(datos[16].trim());
                    direccion.setProvincia(datos[17].trim());
                    direccion.setPais(datos[18].trim());
                   
                    huesped.setDireccion(direccion);
                    
                    return huesped;
                }
            }
        } catch (IOException e) {
            System.out.println("❌ Error al leer el archivo CSV: " + e.getMessage());
        }
        return null; // no se encontró
    }
    
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
            h.getDireccion().getPais()
        );
    }
}