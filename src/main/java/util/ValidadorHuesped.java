package util;

public class ValidadorHuesped {

    public static boolean validarNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
        System.out.println("❌ El nombre es obligatorio.");
        return false;
        }

        if (!nombre.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
            System.out.println("❌ El nombre solo puede contener letras y espacios.");
            return false;
        }

        return true;
    }

    public static boolean validarApellido(String apellido) {
        if (apellido == null || apellido.trim().isEmpty()) {
        System.out.println("❌ El apellido es obligatorio.");
        return false;
        }

        if (!apellido.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
            System.out.println("❌ El appellido solo puede contener letras y espacios.");
            return false;
        }

        return true;
    }

    public static boolean validarTipoDocumento(String tipoDoc) {
        if(tipoDoc.equalsIgnoreCase("DNI") ||
               tipoDoc.equalsIgnoreCase("LE") ||
               tipoDoc.equalsIgnoreCase("LC") ||    
               tipoDoc.equalsIgnoreCase("Pasaporte"))
                return true;
        else {
            System.out.println("❌ Solo admite (DNI, LE, LC o Pasaporte)");
            return false;
        }
    }
    
    public static boolean validarNumeroDocumento(String dni) {
        if(dni.matches("\\d{8}"))return true;
        else {
            System.out.println("❌ Ingrese un documento de 8 digitos");
            return false;
        }
    }
    
    public static boolean validarPosicionFrenteIVA(String posicionIVA) {
           if(posicionIVA.equalsIgnoreCase("Responsable Inscripto") ||
           posicionIVA.equalsIgnoreCase("Monotributista") ||
           posicionIVA.equalsIgnoreCase("Exento") ||
           posicionIVA.equalsIgnoreCase("Consumidor Final")) 
               return true;
           else{
               System.out.println("❌ Solo admite (Responsable Inscripto, Monotributista, Exento, Consumidor Final)");
            return false;
           }
    }
    
    public static boolean validarCUIT(String cuit) {
        if(cuit.matches("\\d{11}"))return true;
        else {
            System.out.println("❌ Ingrese un cuit de 11 digitos");
            return false;
        }
    }

    public static boolean validarTelefono(String telefono) {
        if(telefono.matches("\\d{7,15}")) return true;
        else{
            System.out.println("❌ El telefono solo admite numeros de 7 a 15 digitos");
            return false;
        }
    }

    public static boolean validarEmail(String email) {
        if(email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$") || email.trim().isEmpty())
            return true;
        else{
            System.out.println("❌ Ingrese un email valido o no complete el campo");
            return false;
        }
    }

    public static boolean validarFecha(String fecha) {
        if(fecha.matches("\\d{2}/\\d{2}/\\d{4}")) return true;
        else{
            System.out.println("❌ Ingrese una fecha valida");
            return false;
        }
    }

    public static boolean validarOcupacion(String ocupacion) {
        if (ocupacion != null &&ocupacion.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) 
            return true;
        else{
            System.out.println("❌ Ingrese una ocupacion valida");
            return false;
        }
    }

    public static boolean validarNacionalidad(String nacionalidad) {
        if(nacionalidad != null && nacionalidad.matches("[A-Za-zÁÉÍÓÚáéíóúñÑ\\s]+")) 
            return true;
        else{
            System.out.println("❌ Ingrese una nacionalidad valida");
            return false;
        }
            
    }
    
    public static boolean validarCalle(String calle) {
        if(calle != null && !calle.trim().isEmpty()) 
            return true;
        else{
             System.out.println("❌ Ingrese una calle valida");
            return false;
        }
    }

    public static boolean validarNumero(String numero) {
        if(numero != null && numero.matches("\\d+"))
            return true;
        else{
             System.out.println("❌ Ingrese un numero valido");
            return false;
        }
    }

    public static boolean validarDepartamento(String departamento) {
        // puede ser vacío
        if(departamento == null || departamento.trim().isEmpty() || departamento.matches("[A-Za-z0-9]+"))
            return true;
        else{
            System.out.println("❌ Ingrese un departamento valido");
            return false;
        }
    }

    public static boolean validarPiso(String piso) {
        if(piso == null || piso.trim().isEmpty() || piso.matches("\\d+"))
            return true;
        else{
             System.out.println("❌ Ingrese una piso valido");
            return false;
        }
    }

    public static boolean validarCodigoPostal(String codigoPostal) {
        if(codigoPostal != null && codigoPostal.matches("\\d{4,8}"))
            return true;
        else{
            System.out.println("❌ Ingrese un codigo postal valido, 4 a 8 digitos");
            return false;
        }
        
    }

    public static boolean validarLocalidad(String localidad) {
        if(localidad != null && localidad.matches("[A-Za-zÁÉÍÓÚáéíóúñÑ\\s]+"))
            return true;
        else{
            System.out.println("❌ Ingrese una localidad valida");
            return false;
        }
    }

    public static boolean validarProvincia(String provincia) {
        if(provincia != null && provincia.matches("[A-Za-zÁÉÍÓÚáéíóúñÑ\\s]+"))
            return true;
        else{
             System.out.println("❌ Ingrese una provincia valida");
            return false;
        }
    }

    public static boolean validarPais(String pais) {
        if(pais != null && pais.matches("[A-Za-zÁÉÍÓÚáéíóúñÑ\\s]+"))
            return true;
        else{
             System.out.println("❌ Ingrese un pais valido");
            return false;
        }
    }
}