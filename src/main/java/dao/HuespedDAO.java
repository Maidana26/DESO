package dao;

import models.HuespedDTO;
// import java.util.Optional;
import java.util.List;
import exceptions.ExcepcionHuesped;

/**
 * Interfaz que define las operaciones de acceso a datos (DAO)
 * para la entidad {@link HuespedDTO}.
 * 
 * Proporciona métodos para buscar, guardar, listar y eliminar huéspedes,
 * así como para convertir un huésped a formato CSV.
 */
public interface HuespedDAO {
    
    /**
     * Busca un huésped en la fuente de datos utilizando su número de documento.
     *
     * @param dni número de documento del huésped a buscar.
     * @return el huésped correspondiente si existe, o {@code null} en caso contrario.
     */
    HuespedDTO buscarHuespedPorDni(String dni);
    
    /**
     * Guarda un huésped en la fuente de datos.  
     * Si ya existe un huésped con el mismo identificador, lo reemplaza.
     *
     * @param huesped el huésped que se desea guardar o reemplazar.
     */
    void guardarOHuespedReemplazando(HuespedDTO huesped);
    
    /**
     * Convierte un objeto {@link HuespedDTO} a una representación CSV.
     * 
     * @param h huésped que se desea convertir.
     * @return una cadena con los datos del huésped en formato CSV.
     */
    public String convertirAHuespedCSV(HuespedDTO h);
    // Optional<HuespedDTO> buscarHuespedPorDni(String tipoDoc, String nroDoc);
    
    /**
     * Obtiene una lista con todos los huéspedes almacenados en la fuente de datos.
     *
     * @return una lista de objetos {@link HuespedDTO}.
     */
    List<HuespedDTO> listar();
    
    /**
     * Elimina un huésped de la fuente de datos.
     *
     * @param huesped el huésped que se desea eliminar.
     */
    void eliminar(HuespedDTO huesped);
    
    /**
     * Busca un huésped en el sistema según su tipo y número de documento.
     *
     * <p>Este método permite localizar un registro específico de huésped,
     * combinando ambos parámetros como criterio de búsqueda. Se utiliza
     * típicamente para verificar si un huésped ya existe antes de registrarlo
     * o para acceder a su información en operaciones posteriores.</p>
     *
     * @param tipoDoc el tipo de documento del huésped (por ejemplo, "DNI", "Pasaporte", etc.).
     * @param nroDoc el número de documento correspondiente al huésped.
     * @return el objeto {@link HuespedDTO} que coincide con el tipo y número de documento,
     *         o {@code null} si no se encuentra ningún huésped que cumpla esos criterios.
     * @throws ExcepcionHuesped si ocurre un error durante la búsqueda o lectura de los datos.
     */
    HuespedDTO buscarHuespedPorTipoYNumero(String tipoDoc, String nroDoc) throws ExcepcionHuesped;
}