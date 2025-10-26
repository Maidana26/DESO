package models;

/**
 * Representa a un huésped registrado en el sistema.
 *
 * <p>Contiene datos personales, información de contacto,
 * nacionalidad, ocupación y su dirección de residencia.</p>
 */
public class HuespedDTO {

    private String nombre;
    private String apellido;
    private String tipoDeDocumento;
    private String numeroDocumento;
    private String posicionFrenteIVA;
    private String cuit;
    private String telefono;
    private String fechaNacimiento;
    private String email;
    private String ocupacion;
    private String nacionalidad;
    private DireccionDTO direccion;
    private boolean alojadoAlgunaVez;
    
    /**
     * Constructor vacío utilizado para crear un huésped sin valores iniciales.
     */
    public HuespedDTO() {}

    /**
     * Crea un nuevo huésped con todos los datos especificados.
     *
     * @param nombre nombre del huésped.
     * @param apellido apellido del huésped.
     * @param tipoDeDocumento tipo de documento (DNI, Pasaporte, etc.).
     * @param numeroDocumento número de documento.
     * @param posicionFrenteIVA condición frente al IVA.
     * @param cuit número de CUIT o CUIL.
     * @param telefono número de teléfono de contacto.
     * @param fechaNacimiento fecha de nacimiento del huésped.
     * @param email correo electrónico.
     * @param ocupacion ocupación o profesión.
     * @param nacionalidad nacionalidad del huésped.
     * @param direccion dirección completa del huésped.
     * @param alojadoAlgunaVez indica si se alojó alguna vez en el hotel.
     */
    public HuespedDTO(String nombre, String apellido, String tipoDeDocumento, String numeroDocumento,
                      String posicionFrenteIVA, String cuit, String telefono, String fechaNacimiento,
                      String email, String ocupacion, String nacionalidad, DireccionDTO direccion, boolean alojadoAlgunaVez) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.tipoDeDocumento = tipoDeDocumento;
        this.numeroDocumento = numeroDocumento;
        this.posicionFrenteIVA = posicionFrenteIVA;
        this.cuit = cuit;
        this.telefono = telefono;
        this.fechaNacimiento = fechaNacimiento;
        this.email = email;
        this.ocupacion = ocupacion;
        this.nacionalidad = nacionalidad;
        this.direccion = direccion;
        this.alojadoAlgunaVez = alojadoAlgunaVez;
    }

    // Getters y Setters

    public String getNombre() {return nombre;}
    public void setNombre(String nombre) {this.nombre = nombre;}

    public String getApellido() {return apellido;}
    public void setApellido(String apellido) {this.apellido = apellido;}

    public String getTipoDeDocumento() {return tipoDeDocumento;}
    public void setTipoDeDocumento(String tipoDeDocumento) {this.tipoDeDocumento = tipoDeDocumento;}

    public String getNumeroDocumento() {return numeroDocumento;}
    public void setNumeroDocumento(String numeroDocumento) {this.numeroDocumento = numeroDocumento;}

    public String getPosicionFrenteIVA() {return posicionFrenteIVA;}
    public void setPosicionFrenteIVA(String posicionFrenteIVA) {this.posicionFrenteIVA = posicionFrenteIVA;}

    public String getCuit() {return cuit;}
    public void setCuit(String cuit) {this.cuit = cuit;}

    public String getTelefono() {return telefono;}
    public void setTelefono(String telefono) {this.telefono = telefono;}

    public String getFechaNacimiento() {return fechaNacimiento;}
    public void setFechaNacimiento(String fechaNacimiento) {this.fechaNacimiento = fechaNacimiento;}

    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}

    public String getOcupacion() {return ocupacion;}
    public void setOcupacion(String ocupacion) {this.ocupacion = ocupacion;}

    public String getNacionalidad() {return nacionalidad;}
    public void setNacionalidad(String nacionalidad) {this.nacionalidad = nacionalidad;}
    
    public DireccionDTO getDireccion() {return direccion;}
    public void setDireccion(DireccionDTO direccion) {this.direccion = direccion;}
    
    public void setAlojadoAlgunaVez(boolean al){this.alojadoAlgunaVez = al;}
    public boolean getAlojadoAlgunaVez() {return alojadoAlgunaVez;}
    
    /**
     * Devuelve una representación en texto del huésped, útil para depuración.
     *
     * @return cadena de texto con los valores principales del huésped.
     */
    @Override
    public String toString() {
        return "HuespedDTO{" +
                "nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", tipoDeDocumento='" + tipoDeDocumento + '\'' +
                ", numeroDocumento='" + numeroDocumento + '\'' +
                ", posicionFrenteIVA='" + posicionFrenteIVA + '\'' +
                ", cuit='" + cuit + '\'' +
                ", telefono='" + telefono + '\'' +
                ", fechaNacimiento='" + fechaNacimiento + '\'' +
                ", email='" + email + '\'' +
                ", ocupacion='" + ocupacion + '\'' +
                ", nacionalidad='" + nacionalidad + '\'' +
                ", calle='" + direccion.getCalle() + '\'' +
                ", numero='" + direccion.getNumero() + '\'' +
                ", departamento='" + direccion.getDepartamento() + '\'' +
                ", piso='" + direccion.getPiso() + '\'' +
                ", codigoPostal='" + direccion.getCodigoPostal() + '\'' +
                ", localidad='" + direccion.getLocalidad() + '\'' +
                ", provincia='" + direccion.getProvincia() + '\'' +
                ", pais='" + direccion.getPais() + '\'' +
                (alojadoAlgunaVez ? "1" : "0") +
                '}';
    }
}