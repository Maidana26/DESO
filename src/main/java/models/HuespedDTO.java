package models;

import java.util.Date;

public class HuespedDTO {
    public String nombre;
    public String apellido;
    public String tipoDeDocumento;
    public String numeroDocumento;
    public String posicionFrenteIVA;
    public int cuit;
    public int telefono;
    public Date fecha;    
    public String email;
    public String ocupacion;
    public String nacionalidad;
    
    public HuespedDTO(
        String nombre, 
        String apellido,
        String tipoDeDocumento,
        String numeroDocumento,
        String posicionFrenteIVA,
        int cuit,
        int telefono,
        Date fecha,
        String email,
        String ocupacion,
        String nacionalidad
    ){
        this.nombre = nombre;
        this.apellido = apellido;
        this.tipoDeDocumento = tipoDeDocumento;
        this.numeroDocumento = numeroDocumento;
        this.posicionFrenteIVA = posicionFrenteIVA;
        this.cuit = cuit;
        this.telefono = telefono;
        this.fecha = fecha;
        this.email = email;
        this.ocupacion = ocupacion;
        this.nacionalidad = nacionalidad;
    }

    @Override
    public String toString() {
        // formato exacto para guardar en CSV
        String fechaStr = (fecha != null) ? new java.text.SimpleDateFormat("dd/MM/yyyy").format(fecha) : "";
        return nombre + ";" + apellido + ";" + tipoDeDocumento + ";" + numeroDocumento + ";" +
               posicionFrenteIVA + ";" + cuit + ";" + telefono + ";" + fechaStr + ";" +
               email + ";" + ocupacion + ";" + nacionalidad;
    }
}
