package service;

import dao.HuespedDAO;
import exceptions.ExcepcionHuesped;
import models.HuespedDTO;

public class GestorHuesped {
    private final HuespedDAO huespedDAO;

    public GestorHuesped(HuespedDAO huespedDAO) {
        this.huespedDAO = huespedDAO;
    }

    public HuespedDTO buscarHuesped(String tipoDoc, int nroDoc) throws ExcepcionHuesped {
        return huespedDAO.buscarPorDocumento(tipoDoc, nroDoc)
                .orElseThrow(() -> new ExcepcionHuesped("El huésped no existe en el sistema."));
    }

    public void darBajaHuesped(String tipoDoc, int nroDoc) throws ExcepcionHuesped {
        var huesped = buscarHuesped(tipoDoc, nroDoc);

        if (huesped.isAlojadoAlgunaVez()) {
            throw new ExcepcionHuesped("El huésped no puede ser eliminado pues se ha alojado en el hotel en alguna oportunidad.");
        }

        huespedDAO.eliminar(huesped);
    }
}
