package dao;

import models.Conserje;
import java.io.IOException;
import java.nio.file.*;
import java.util.Optional;
import java.util.stream.Stream;

// IMPLEMENTACION
public class ArchivoConserjeDAO implements ConserjeDAO{
    private final Path path;
    
    // Constructor
    public ArchivoConserjeDAO(Path path){
       this.path = path;
    }   
    
    @Override
    public Optional<Conserje> findByUsuario(String usuario){
         try (Stream<String> lines = Files.lines(path)) {
            return lines.map(this::toConserje)
                        .filter(c -> c.getUsuario().equalsIgnoreCase(usuario))
                        .findFirst();
        } catch (IOException e) {
            System.err.println("Error leyendo archivo de conserjes: " + e.getMessage());
            return Optional.empty();
        }
    }
    
    private Conserje toConserje(String line){
        String[] parts = line.split(",");
        return new Conserje(parts[0],parts[1]); //usuario, constrasena
    }
    
}
