package models;

public class DireccionDTO{
    private String calle;
    private String numero;
    private String localidad;
    private String provincia;
    private String pais;

    public DireccionDTO(String calle, String numero, String localidad, String provincia, String pais) {
        this.calle = calle;
        this.numero = numero;
        this.localidad = localidad;
        this.provincia = provincia;
        this.pais = pais;
    }
}