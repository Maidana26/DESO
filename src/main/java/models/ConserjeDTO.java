package models;

public class ConserjeDTO {
    private String usuario;
    private String contrasena;
    
    //constructor
    public ConserjeDTO(String usuario, String contrasena){
        this.usuario = usuario;
        this.contrasena = contrasena;
    }
    // getters 
    public String getUsuario(){ return usuario;}
    public String getContrasena(){ return contrasena;}
    
    // si llego a necesitar otros tipos de usuarios, puedo usar herencia, Usuario y que conserje herede
    
}
