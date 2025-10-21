package dao;

import java.util.Optional;
import models.Conserje;

public interface ConserjeDAO {
    Optional<Conserje> findByUsuario(String usuario);
    boolean existeContrasena(String contrasena);
}
