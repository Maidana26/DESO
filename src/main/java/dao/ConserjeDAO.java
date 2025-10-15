package dao;

import java.util.Optional;
import models.Conserje;
//INTERFAZ
public interface ConserjeDAO {
    Optional<Conserje> findByUsuario(String usuario);
}
