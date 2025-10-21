package dao;

import models.Conserje;
import java.io.IOException;
import java.nio.file.*;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

public class ArchivoConserjeDAO implements ConserjeDAO {
    private final Path path;

    // Constructor: recibe la ruta del archivo CSV
    public ArchivoConserjeDAO(Path path) {
        this.path = path;
    }

    /**
     * Busca un conserje por su nombre de usuario (no distingue mayúsculas/minúsculas)
     * @param usuario nombre de usuario a buscar
     * @return Optional<Conserje> con el conserje si existe
     */
    @Override
    public Optional<Conserje> findByUsuario(String usuario) {
        try (Stream<String> lines = Files.lines(path)) {
            return lines
                    .map(this::toConserje)
                    .filter(Objects::nonNull)
                    .filter(c -> c.getUsuario().equalsIgnoreCase(usuario.trim()))
                    .findFirst();
        } catch (IOException e) {
            System.err.println("⚠️ Error leyendo archivo de conserjes: " + e.getMessage());
            return Optional.empty();
        }
    }

    /**
     * Verifica si la contraseña existe en algún usuario del archivo
     * (aunque no coincida con el usuario pasado)
     */
    public boolean existeContrasena(String contrasena) {
        try (Stream<String> lines = Files.lines(path)) {
            return lines
                    .map(this::toConserje)
                    .filter(Objects::nonNull)
                    .anyMatch(c -> c.getContrasena().equals(contrasena.trim()));
        } catch (IOException e) {
            System.err.println("⚠️ Error leyendo archivo de conserjes: " + e.getMessage());
            return false;
        }
    }

    /**
     * Convierte una línea del CSV en un objeto Conserje.
     * Formato esperado: usuario,contraseña
     * Si la línea no cumple el formato, devuelve null.
     */
    private Conserje toConserje(String line) {
        if (line == null || line.isBlank()) return null;
        String[] parts = line.split(",");
        if (parts.length < 2) {
            System.err.println("⚠️ Línea mal formateada en CSV: " + line);
            return null;
        }
        return new Conserje(parts[0].trim(), parts[1].trim());
    }
}
