package models;

import java.time.LocalDate;

public class HuespedDTO {
    private String nombres;
    private String apellido;
    private String tipoDeDocumento;
    private int numeroDeDocumento;
    private String posicionFrenteAlIVA;
    private long cuit;
    private long telefono;
    private LocalDate fechaDeNacimiento;
    private String email;
    private String ocupacion;
    private String nacionalidad;
     private boolean alojadoAlgunaVez;

    public HuespedDTO(String nombres, String apellido, String tipoDeDocumento, int numeroDeDocumento,
                   String posicionFrenteAlIVA, long cuit, long telefono, LocalDate fechaDeNacimiento,
                   String email, String ocupacion, String nacionalidad, boolean alojadoAlgunaVez) {
        this.nombres = nombres;
        this.apellido = apellido;
        this.tipoDeDocumento = tipoDeDocumento;
        this.numeroDeDocumento = numeroDeDocumento;
        this.posicionFrenteAlIVA = posicionFrenteAlIVA;
        this.cuit = cuit;
        this.telefono = telefono;
        this.fechaDeNacimiento = fechaDeNacimiento;
        this.email = email;
        this.ocupacion = ocupacion;
        this.nacionalidad = nacionalidad;
        this.alojadoAlgunaVez = alojadoAlgunaVez;
    }

    // Getters y Setters...
    public String getNombres(){return this.nombres;}
    public String getApellido(){return this.apellido;}
    public String getTipoDeDocumento(){return this.tipoDeDocumento;}
    public int getNumeroDeDocumento(){return this.numeroDeDocumento;}
    public String getPosicionFrenteAlIVA(){return this.posicionFrenteAlIVA;}
    public long getCuit(){return this.cuit;}
    public long getTelefono(){return this.telefono;}
    public LocalDate getFechaDeNacimiento(){return this.fechaDeNacimiento;}
    public String getEmail(){return this.email;}
    public String getOcupacion(){return this.ocupacion;}
    public String getNacionalidad(){return this.nacionalidad;}
    public boolean isAlojadoAlgunaVez(){return this.alojadoAlgunaVez;}
    
    @Override
    public String toString() {
        return nombres + "," + apellido + "," + tipoDeDocumento + "," + numeroDeDocumento + ","
                + posicionFrenteAlIVA + "," + cuit + "," + telefono + "," + fechaDeNacimiento + ","
                + email + "," + ocupacion + "," + nacionalidad + "," + (alojadoAlgunaVez ? "1" : "0");
    }
}
