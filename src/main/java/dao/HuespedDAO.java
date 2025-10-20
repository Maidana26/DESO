package dao;

import models.HuespedDTO;
import java.util.Optional;
import java.util.List;

public interface HuespedDAO {
    List<HuespedDTO> listar();
    Optional<HuespedDTO> buscarPorDocumento(String tipo, int numero);
    void eliminar(HuespedDTO huesped);
}