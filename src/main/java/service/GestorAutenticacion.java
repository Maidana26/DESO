package service;

import dao.ConserjeDAO;
import exceptions.ExcepcionAutenticacion;
import models.Conserje;

public class GestorAutenticacion {
    private final ConserjeDAO conserjeDAO;

    public GestorAutenticacion(ConserjeDAO conserjeDAO) {
        this.conserjeDAO = conserjeDAO;
    }

    public boolean autenticar(String usuario, String contrasena) throws ExcepcionAutenticacion {
    if (usuario == null || contrasena == null || usuario.isBlank() || contrasena.isBlank()) {
        throw new ExcepcionAutenticacion("Debe ingresar usuario y contraseña.");
    }

    var conserje = conserjeDAO.findByUsuario(usuario.trim())
            .orElseThrow(() -> new ExcepcionAutenticacion("Usuario no encontrado."));

    String contrasenaIngresada = contrasena.trim();
    String contrasenaGuardada = conserje.getContrasena().trim();

    if (!conserje.getUsuario().equalsIgnoreCase(usuario.trim()) ||
        !contrasenaGuardada.equals(contrasenaIngresada)) {
        throw new ExcepcionAutenticacion("El usuario o la contraseña no son válidos.");
    }

    return true;
    }
}
