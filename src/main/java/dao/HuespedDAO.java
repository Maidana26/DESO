package dao;

import models.HuespedDTO;

public interface HuespedDAO {
    HuespedDTO buscarHuespedPorDni(String dni);
    void guardarOHuespedReemplazando(HuespedDTO huesped);
    public String convertirAHuespedCSV(HuespedDTO h);
}