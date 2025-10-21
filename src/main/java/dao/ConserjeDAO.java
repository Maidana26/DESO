package dao;

import java.util.Optional;
import models.ConserjeDTO;

public interface ConserjeDAO {
    Optional<ConserjeDTO> findByUsuario(String usuario);
    boolean existeContrasena(String contrasena);
}
