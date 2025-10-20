package dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HuespedDAO {

    public void buscarHuesped(String nombre, String apellido, String tipoDocumento, String numeroDocumento) {
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

            // Mostrar resultados por consola
            System.out.println("\n--- RESULTADOS DE LA BÚSQUEDA ---");
            if (encontrados.isEmpty()) {
                System.out.println("No se encontraron huéspedes con esos criterios.");
            } else {
                for (String l : encontrados) {
                    System.out.println(l);
                }
                System.out.println("\nBúsqueda finalizada. Resultados guardados en 'huespedesEncontrados.csv'.");
            }

        } catch (IOException e) {
            System.out.println("Error leyendo o escribiendo archivos: " + e.getMessage());
        }
    }
}
