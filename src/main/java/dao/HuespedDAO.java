package dao;

import models.HuespedDTO;

public interface HuespedDAO {
    HuespedDTO buscarHuespedPorDni(String dni);
    void guardarOReemplazarHuesped(HuespedDTO huesped);
    public String convertirHuespedCSV(HuespedDTO h);
}