package dao;

import models.HuespedDTO;
import java.util.Optional;
import java.util.List;

public interface HuespedDAO {
    HuespedDTO buscarHuespedPorDni(String dni);
    void guardarOHuespedReemplazando(HuespedDTO huesped);
    public String convertirAHuespedCSV(HuespedDTO h);
    //Optional<HuespedDTO> buscarHuespedPorDni(String tipoDoc, String nroDoc);
    List<HuespedDTO> listar();
    void eliminar(HuespedDTO huesped);
}