package models;

public class DireccionDTO {
    
    // Atributos
    public String calle;
    public String numero;
    public String departamento;
    public String piso;
    public String codigoPostal;
    public String localidad;
    public String provincia;
    public String pais;
    
    // Constructor vacío
    public DireccionDTO() {
    }

    // Constructor completo
    public DireccionDTO(String calle, String numero, String departamento, String piso, 
                        String codigoPostal, String localidad, String provincia, String pais) {
        this.calle = calle;
        this.numero = numero;
        this.departamento = departamento;
        this.piso = piso;
        this.codigoPostal = codigoPostal;
        this.localidad = localidad;
        this.provincia = provincia;
        this.pais = pais;
    }

    // Getters y Setters
    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getPiso() {
        return piso;
    }

    public void setPiso(String piso) {
        this.piso = piso;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    // Representación en texto (útil para depurar)
    @Override
    public String toString() {
        return "DireccionDTO{" +
                "calle='" + calle + '\'' +
                ", numero='" + numero + '\'' +
                ", departamento='" + departamento + '\'' +
                ", piso='" + piso + '\'' +
                ", codigoPostal='" + codigoPostal + '\'' +
                ", localidad='" + localidad + '\'' +
                ", provincia='" + provincia + '\'' +
                ", pais='" + pais + '\'' +
                '}';
    }

    // Método opcional para exportar a CSV
    public String toCSV() {
        return String.join(",", 
                calle, numero, departamento, piso, 
                codigoPostal, localidad, provincia, pais);
    }
}