package service;

import dao.ConserjeDAO;
import exceptions.ContrasenaIncorrectaException;
import exceptions.UsuarioNoEncontradoException;
import exceptions.AmbosIncorrectosException;
import models.Conserje;

public class GestorAutenticacion {
    private final ConserjeDAO conserjeDAO;

    public GestorAutenticacion(ConserjeDAO conserjeDAO) {
        this.conserjeDAO = conserjeDAO;
    }

    public boolean autenticar(String usuario, String contrasena)
            throws UsuarioNoEncontradoException, ContrasenaIncorrectaException, AmbosIncorrectosException {

        // Validaciones básicas
        if (usuario == null || usuario.isBlank() || contrasena == null || contrasena.isBlank()) {
            throw new AmbosIncorrectosException();
        }

        String usuarioTrim = usuario.trim();
        String contrasenaTrim = contrasena.trim();

        // Busco el usuario en el archivo CSV
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
        Conserje conserje = optionalConserje.get();
        if (!conserje.getContrasena().trim().equals(contrasenaTrim)) {
            throw new ContrasenaIncorrectaException();
        }

        // Usuario y contraseña correctos
        return true;
    }
}
