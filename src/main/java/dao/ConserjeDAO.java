package dao;

import java.util.Optional;
import models.ConserjeDTO;

/**
 * Interfaz del patrón DAO (Data Access Object) para la entidad {@link ConserjeDTO}.
 * <p>
 * Define las operaciones de acceso a datos relacionadas con los conserjes,
 * independientemente del mecanismo de persistencia utilizado (archivos, base de datos, etc.).
 * </p>
 *
 * <p><b>Responsabilidad:</b> Proveer una abstracción para la búsqueda y validación
 * de credenciales de conserjes.</p>
 *
 */
public interface ConserjeDAO {
    
    /**
     * Busca un conserje por su nombre de usuario.
     *
     * @param usuario nombre de usuario del conserje a buscar.
     * @return un {@link Optional} que contiene el {@link ConserjeDTO} correspondiente
     *         si fue encontrado, o un Optional vacío si no existe.
     */
    Optional<ConserjeDTO> findByUsuario(String usuario);
    
    /**
     * Verifica si una contraseña existe en algún registro de conserje.
     * <p>
     * Este método no valida la combinación usuario-contraseña, solo la existencia
     * de la contraseña en el sistema de persistencia.
     * </p>
     *
     * @param contrasena contraseña a verificar.
     * @return {@code true} si la contraseña existe, {@code false} en caso contrario.
     */
    boolean existeContrasena(String contrasena);
}
