package dao;

import java.io.*;
import models.HuespedDTO;

public class HuespedDAO {
    private BufferedReader lector;
    private String linea;
    private String partes[] = null;

    public void guardar(HuespedDTO huesped) {
        // pendiente implementar
    }

    public void actualizar(HuespedDTO huesped) {
        // pendiente implementar
    }

    public void eliminar(HuespedDTO huesped) {
        // pendiente implementar
    }

    public void buscarHuesped(String nombre, String apellido, String tipoDocumento, String numeroDocumento) {
        File archivo = new File("data/ListaHuespedes.csv");
        File archivoSalida = new File("data/huespedesEncontrados.csv");

        try (
            BufferedReader lector = new BufferedReader(new FileReader(archivo));
            BufferedWriter escritor = new BufferedWriter(new FileWriter(archivoSalida))
        ) {
            while ((linea = lector.readLine()) != null) {
                partes = linea.split(",");

                // Aseguramos que la línea tenga suficientes columnas
                if (partes.length < 4) continue;

                String nom = partes[0].trim();
                String ape = partes[1].trim();
                String tipoDoc = partes[2].trim();
                String numDoc = partes[3].trim();

                boolean coincide =
                    (nombre.isEmpty() || nom.equalsIgnoreCase(nombre)) &&
                    (apellido.isEmpty() || ape.equalsIgnoreCase(apellido)) &&
                    (tipoDocumento.isEmpty() || tipoDoc.equalsIgnoreCase(tipoDocumento)) &&
                    (numeroDocumento.isEmpty() || numDoc.equalsIgnoreCase(numeroDocumento));

                if (coincide) {
                    escritor.write(linea);
                    escritor.newLine();
                    System.out.println(linea);
                }
            }

            System.out.println("\n✅ Búsqueda completada. Archivo creado en: " + archivoSalida.getAbsolutePath());

        } catch (IOException e) {
            System.out.println("⚠️ Error al procesar el archivo: " + e.getMessage());
        }
    }
}

