package service;

import dao.ConserjeDAO;
import exceptions.ContrasenaIncorrectaException;
import exceptions.UsuarioNoEncontradoException;
import exceptions.AmbosIncorrectosException;
import models.ConserjeDTO;

/**
 * GestorAutenticacion se encarga de manejar la lógica de autenticación
 * de los conserjes del sistema. Se comunica con el DAO para validar
 * usuarios y contraseñas, lanzando excepciones específicas según el error.
 */
public class GestorAutenticacion {
    // DAO para acceder a la información de los conserjes
    private final ConserjeDAO conserjeDAO;

    /**
     * Constructor que recibe el DAO de conserjes.
     * @param conserjeDAO objeto que permite acceder a los datos de los conserjes
     */
    public GestorAutenticacion(ConserjeDAO conserjeDAO) {
        this.conserjeDAO = conserjeDAO;
    }

     /**
     * Método que autentica un usuario verificando su nombre y contraseña.
     *
     * @param usuario nombre del usuario a autenticar
     * @param contrasena contraseña proporcionada por el usuario
     * @return true si la autenticación fue exitosa
     * @throws UsuarioNoEncontradoException si el usuario no existe en el sistema
     * @throws ContrasenaIncorrectaException si la contraseña no coincide
     * @throws AmbosIncorrectosException si ambos, usuario y contraseña, son inválidos o vacíos
     */
    public boolean autenticar(String usuario, String contrasena)
            throws UsuarioNoEncontradoException, ContrasenaIncorrectaException, AmbosIncorrectosException {

        // Validaciones básicas
        if (usuario == null || usuario.isBlank() || contrasena == null || contrasena.isBlank()) {
            throw new AmbosIncorrectosException();
        }

        String usuarioTrim = usuario.trim();
        String contrasenaTrim = contrasena.trim();

        // Busco el usuario en el DAO
        var optionalConserje = conserjeDAO.findByUsuario(usuarioTrim);

        if (optionalConserje.isEmpty()) {
            // Si el usuario no existe, verifico si la contraseña pertenece a alguien más
            boolean existeContrasena = conserjeDAO.existeContrasena(contrasenaTrim);
            if (existeContrasena) {
                // La contraseña pertenece a un usuario válido, pero el nombre no existe
                throw new UsuarioNoEncontradoException(usuarioTrim);
            } else {
                // Ni el usuario ni la contraseña son válidos
                throw new AmbosIncorrectosException();
            }
        }

        // El usuario existe → verifico la contraseña
        ConserjeDTO conserje = optionalConserje.get();
        if (!conserje.getContrasena().trim().equals(contrasenaTrim)) {
            throw new ContrasenaIncorrectaException();
        }

        // Usuario y contraseña correctos
        return true;
    }
}