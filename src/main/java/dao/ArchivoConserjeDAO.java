package dao;

import models.ConserjeDTO;
import java.io.IOException;
import java.nio.file.*;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Implementación del patrón DAO (Data Access Object) para la gestión de conserjes.
 * <p>
 * Esta clase se encarga de acceder a los datos de conserjes almacenados
 * en un archivo CSV. Permite buscar un conserje por su usuario y verificar
 * la existencia de una contraseña.
 * </p>
 *
 * <p><b>Responsabilidad:</b> Proveer una interfaz de acceso a los datos
 * persistidos de los conserjes, encapsulando los detalles de lectura del archivo.</p>
 *
 */ 
public class ArchivoConserjeDAO implements ConserjeDAO {
    /** Ruta al archivo CSV donde se almacenan los datos de los conserjes. */
    private final Path path;

    /**
     * Crea una nueva instancia del DAO de conserjes basada en un archivo CSV.
     *
     * @param path ruta al archivo CSV que contiene los registros de conserjes.
     */
    // Constructor: recibe la ruta del archivo CSV
    public ArchivoConserjeDAO(Path path) {
        this.path = path;
    }

    /**
     * Busca un conserje en el archivo CSV según su nombre de usuario.
     *
     * @param usuario nombre de usuario del conserje a buscar.
     * @return un {@link Optional} que contiene el {@link ConserjeDTO} encontrado,
     *         o un Optional vacío si no existe el usuario.
     */
    @Override
    public Optional<ConserjeDTO> findByUsuario(String usuario) {
        try (Stream<String> lines = Files.lines(path)) {
            return lines
                    .map(this::toConserje)
                    .filter(Objects::nonNull)
                    .filter(c -> c.getUsuario().equalsIgnoreCase(usuario.trim()))
                    .findFirst();
        } catch (IOException e) {
            System.err.println("Error leyendo archivo de conserjes: " + e.getMessage());
            return Optional.empty();
        }
    }

    /**
     * Verifica si una contraseña existe en algún registro del archivo CSV.
     * <p>
     * Este método no verifica la correspondencia entre usuario y contraseña,
     * solo comprueba si la contraseña está presente en la base de datos.
     * </p>
     *
     * @param contrasena contraseña a verificar.
     * @return {@code true} si la contraseña existe en el archivo, {@code false} en caso contrario.
     */
    @Override
    public boolean existeContrasena(String contrasena) {
        try (Stream<String> lines = Files.lines(path)) {
            return lines
                    .map(this::toConserje)
                    .filter(Objects::nonNull)
                    .anyMatch(c -> c.getContrasena().equals(contrasena.trim()));
        } catch (IOException e) {
            System.err.println("Error leyendo archivo de conserjes: " + e.getMessage());
            return false;
        }
    }

    /**
     * Convierte una línea del archivo CSV en un objeto {@link ConserjeDTO}.
     * <p>
     * El formato esperado para cada línea es: <code>usuario,contraseña</code>.
     * Si la línea no cumple con este formato, se devuelve {@code null}.
     * </p>
     *
     * @param line línea leída del archivo CSV.
     * @return instancia de {@link ConserjeDTO} correspondiente, o {@code null} si la línea es inválida.
     */
    private ConserjeDTO toConserje(String line) {
        if (line == null || line.isBlank()) return null;
        String[] parts = line.split(",");
        if (parts.length < 2) {
            System.err.println("Línea mal formateada en CSV: " + line);
            return null;
        }
        return new ConserjeDTO(parts[0].trim(), parts[1].trim());
    }
}