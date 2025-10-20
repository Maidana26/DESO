package service;

public class ValidadorHuesped {

    public static boolean validarNombre(String nombre) {
        return nombre != null && !nombre.trim().isEmpty() && nombre.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+");
    }

    public static boolean validarApellido(String apellido) {
        return apellido != null && !apellido.trim().isEmpty() && apellido.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+");
    }

    public static boolean validarTipoDocumento(String tipoDoc) {
        return tipoDoc.equalsIgnoreCase("DNI") ||
               tipoDoc.equalsIgnoreCase("Pasaporte") ||
               tipoDoc.equalsIgnoreCase("Libreta Cívica");
    }
    
    public static boolean validarNumeroDocumento(String dni) {
        return dni.matches("\\d{7,8}");
    }
    
    public static boolean validarPosicionFrenteIVA(String posicionIVA) {
    return posicionIVA.equalsIgnoreCase("Responsable Inscripto") ||
           posicionIVA.equalsIgnoreCase("Monotributista") ||
           posicionIVA.equalsIgnoreCase("Exento") ||
           posicionIVA.equalsIgnoreCase("Consumidor Final");
    }
    
    public static boolean validarCUIT(String cuit) {
        return cuit.matches("\\d{11}");
    }

    public static boolean validarTelefono(String telefono) {
        return telefono.matches("\\d{7,15}");
    }

    public static boolean validarEmail(String email) {
        return email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    }

    public static boolean validarFecha(String fecha) {
        return fecha.matches("\\d{2}/\\d{2}/\\d{4}");
    }

    public static boolean validarOcupacion(String ocupacion) {
        return ocupacion != null && !ocupacion.trim().isEmpty();
    }

    public static boolean validarNacionalidad(String nacionalidad) {
        return nacionalidad != null && nacionalidad.matches("[A-Za-zÁÉÍÓÚáéíóúñÑ\\s]+");
    }
    
    public static boolean validarCalle(String calle) {
        return calle != null && !calle.trim().isEmpty();
    }

    public static boolean validarNumero(String numero) {
        return numero != null && numero.matches("\\d+");
    }

    public static boolean validarDepartamento(String departamento) {
        // puede ser vacío
        return departamento == null || departamento.trim().isEmpty() || departamento.matches("[A-Za-z0-9]+");
    }

    public static boolean validarPiso(String piso) {
        return piso == null || piso.trim().isEmpty() || piso.matches("\\d+");
    }

    public static boolean validarCodigoPostal(String codigoPostal) {
        return codigoPostal != null && codigoPostal.matches("\\d{4,8}");
    }

    public static boolean validarLocalidad(String localidad) {
        return localidad != null && localidad.matches("[A-Za-zÁÉÍÓÚáéíóúñÑ\\s]+");
    }

    public static boolean validarProvincia(String provincia) {
        return provincia != null && provincia.matches("[A-Za-zÁÉÍÓÚáéíóúñÑ\\s]+");
    }

    public static boolean validarPais(String pais) {
        return pais != null && pais.matches("[A-Za-zÁÉÍÓÚáéíóúñÑ\\s]+");
    }
}
