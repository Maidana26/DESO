package dao;

import models.HuespedDTO;

public interface HuespedDAO {
    HuespedDTO buscarPorDni(String dni);
    void guardarOReemplazar(HuespedDTO huesped);
}