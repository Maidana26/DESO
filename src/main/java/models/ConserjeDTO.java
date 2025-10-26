package models;

/**
 * Representa un conserje dentro del sistema.
 * 
 * <p>Esta clase funciona como un objeto de transferencia de datos (DTO)
 * que encapsula la información de autenticación del conserje, incluyendo
 * su usuario y contraseña.</p>
 */
public class ConserjeDTO {
    private String usuario;
    private String contrasena;
    
    /**
     * Crea un nuevo {@code ConserjeDTO} con los datos proporcionados.
     *
     * @param usuario nombre de usuario del conserje.
     * @param contrasena contraseña asociada al usuario.
     */
    public ConserjeDTO(String usuario, String contrasena){
        this.usuario = usuario;
        this.contrasena = contrasena;
    }
    /**
     * Devuelve el nombre de usuario del conserje.
     *
     * @return el nombre de usuario.
     */
    public String getUsuario(){ return usuario;}
    /**
     * Devuelve la contraseña del conserje.
     *
     * @return la contraseña.
     */
    public String getContrasena(){ return contrasena;}
    // Si se agregan otros tipos de usuarios, se puede aplicar herencia:
    // una clase base Usuario y subclases específicas como Conserje.
}
