package util;

/**
 * Clase de utilidades para validar datos ingresados para un huésped.
 * 
 * Cada método valida un campo específico y, en caso de error, imprime un mensaje explicativo.
 * - Los campos obligatorios no pueden estar vacíos.
 * - Los campos opcionales permiten valores vacíos.
 * - Se utilizan expresiones regulares para validar formato y contenido.
 */
public class ValidadorHuesped {
    /**
     * Valida que el nombre no esté vacío y contenga solo letras y espacios.
     *
     * @param nombre El nombre del huésped a validar.
     * @return true si el nombre es válido; false en caso contrario.
     */
    public static boolean validarNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
        System.out.println("El nombre es obligatorio.");
        return false;
        }

        if (!nombre.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
            System.out.println("El nombre solo puede contener letras y espacios.");
            return false;
        }

        return true;
    }
    /**
     * Valida que el apellido no esté vacío y contenga solo letras y espacios.
     *
     * @param apellido El apellido del huésped a validar.
     * @return true si el apellido es válido; false en caso contrario.
     */
    public static boolean validarApellido(String apellido) {
        if (apellido == null || apellido.trim().isEmpty()) {
        System.out.println("El apellido es obligatorio.");
        return false;
        }

        if (!apellido.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
            System.out.println("El appellido solo puede contener letras y espacios.");
            return false;
        }

        return true;
    }
    /**
     * Valida que el tipo de documento sea uno de los permitidos: DNI, LE, LC o Pasaporte.
     *
     * @param tipoDoc Tipo de documento a validar.
     * @return true si es válido; false en caso contrario.
     */
    public static boolean validarTipoDocumento(String tipoDoc) {
        if(tipoDoc.equalsIgnoreCase("DNI") ||
               tipoDoc.equalsIgnoreCase("LE") ||
               tipoDoc.equalsIgnoreCase("LC") ||    
               tipoDoc.equalsIgnoreCase("Pasaporte"))
                return true;
        else {
            System.out.println("Solo admite (DNI, LE, LC o Pasaporte)");
            return false;
        }
    }
    /**
     * Valida que el número de documento tenga exactamente 8 dígitos.
     *
     * @param dni Número de documento a validar.
     * @return true si es válido; false en caso contrario.
     */
    public static boolean validarNumeroDocumento(String dni) {
        if(dni.matches("\\d{8}"))return true;
        else {
            System.out.println("Ingrese un documento de 8 digitos");
            return false;
        }
    }
    /**
     * Valida que la posición frente al IVA sea una de las permitidas.
     *
     * @param posicionIVA Posición frente al IVA a validar.
     * @return true si es válida; false en caso contrario.
     */
    public static boolean validarPosicionFrenteIVA(String posicionIVA) {
           if(posicionIVA.equalsIgnoreCase("Responsable Inscripto") ||
           posicionIVA.equalsIgnoreCase("Monotributista") ||
           posicionIVA.equalsIgnoreCase("Exento") ||
           posicionIVA.equalsIgnoreCase("Consumidor Final")) 
               return true;
           else{
               System.out.println("Solo admite (Responsable Inscripto, Monotributista, Exento, Consumidor Final)");
            return false;
           }
    }
    /**
     * Valida el CUIT, puede estar vacío o tener 11 dígitos.
     *
     * @param cuit CUIT a validar.
     * @return true si es válido; false en caso contrario.
     */
    public static boolean validarCUIT(String cuit) {
        if(cuit.trim().isEmpty() || cuit.matches("\\d{11}"))return true;
        else {
            System.out.println("Ingrese un cuit de 11 digitos");
            return false;
        }
    }
    /**
     * Valida que el teléfono tenga entre 7 y 15 dígitos.
     *
     * @param telefono Teléfono a validar.
     * @return true si es válido; false en caso contrario.
     */
    public static boolean validarTelefono(String telefono) {
        if(telefono.matches("\\d{7,15}")) return true;
        else{
            System.out.println("El telefono solo admite numeros de 7 a 15 digitos");
            return false;
        }
    }
    /**
     * Valida el formato de email o permite dejarlo vacío.
     *
     * @param email Email a validar.
     * @return true si es válido; false en caso contrario.
     */
    public static boolean validarEmail(String email) {
        if(email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$") || email.trim().isEmpty())
            return true;
        else{
            System.out.println("Ingrese un email valido o no complete el campo");
            return false;
        }
    }
    /**
     * Valida que la fecha tenga el formato dd/mm/aaaa.
     *
     * @param fecha Fecha a validar.
     * @return true si es válida; false en caso contrario.
     */
    public static boolean validarFecha(String fecha) {
        if(fecha.matches("\\d{2}/\\d{2}/\\d{4}")) return true;
        else{
            System.out.println("Ingrese una fecha valida");
            return false;
        }
    }
    /**
     * Valida que la ocupación contenga solo letras y espacios.
     *
     * @param ocupacion Ocupación a validar.
     * @return true si es válida; false en caso contrario.
     */
    public static boolean validarOcupacion(String ocupacion) {
        if (ocupacion != null &&ocupacion.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) 
            return true;
        else{
            System.out.println("Ingrese una ocupacion valida");
            return false;
        }
    }
    /**
     * Valida que la nacionalidad contenga solo letras y espacios.
     *
     * @param nacionalidad Nacionalidad a validar.
     * @return true si es válida; false en caso contrario.
     */
    public static boolean validarNacionalidad(String nacionalidad) {
        if(nacionalidad != null && nacionalidad.matches("[A-Za-zÁÉÍÓÚáéíóúñÑ\\s]+")) 
            return true;
        else{
            System.out.println("Ingrese una nacionalidad valida");
            return false;
        }
            
    }
    /**
     * Valida que la calle no esté vacía.
     *
     * @param calle Calle a validar.
     * @return true si es válida; false en caso contrario.
     */
    public static boolean validarCalle(String calle) {
        if(calle != null && !calle.trim().isEmpty()) 
            return true;
        else{
             System.out.println("Ingrese una calle valida");
            return false;
        }
    }
    /**
     * Valida número de calle: solo dígitos.
     *
     * @param numero Número a validar.
     * @return true si es válido; false en caso contrario.
     */
    public static boolean validarNumero(String numero) {
        if(numero != null && numero.matches("\\d+"))
            return true;
        else{
             System.out.println("Ingrese un numero valido");
            return false;
        }
    }
    /**
     * Valida departamento: puede estar vacío o contener letras/números.
     *
     * @param departamento Departamento a validar.
     * @return true si es válido; false en caso contrario.
     */
    public static boolean validarDepartamento(String departamento) {
        // puede ser vacío
        if(departamento == null || departamento.trim().isEmpty() || departamento.matches("[A-Za-z0-9]+"))
            return true;
        else{
            System.out.println("Ingrese un departamento valido");
            return false;
        }
    }
    /**
     * Valida piso: puede estar vacío o ser un número.
     *
     * @param piso Piso a validar.
     * @return true si es válido; false en caso contrario.
     */
    public static boolean validarPiso(String piso) {
        if(piso == null || piso.trim().isEmpty() || piso.matches("\\d+"))
            return true;
        else{
             System.out.println("Ingrese una piso valido");
            return false;
        }
    }
    /**
     * Valida código postal: entre 4 y 8 dígitos.
     *
     * @param codigoPostal Código postal a validar.
     * @return true si es válido; false en caso contrario.
     */
    public static boolean validarCodigoPostal(String codigoPostal) {
        if(codigoPostal != null && codigoPostal.matches("\\d{4,8}"))
            return true;
        else{
            System.out.println("Ingrese un codigo postal valido, 4 a 8 digitos");
            return false;
        }
        
    }
    /**
     * Valida localidad solo con letras y espacios.
     *
     * @param localidad Localidad a validar.
     * @return true si es válida; false en caso contrario.
     */
    public static boolean validarLocalidad(String localidad) {
        if(localidad != null && localidad.matches("[A-Za-zÁÉÍÓÚáéíóúñÑ\\s]+"))
            return true;
        else{
            System.out.println("Ingrese una localidad valida");
            return false;
        }
    }
    /**
     * Valida provincia solo con letras y espacios.
     *
     * @param provincia Provincia a validar.
     * @return true si es válida; false en caso contrario.
     */
    public static boolean validarProvincia(String provincia) {
        if(provincia != null && provincia.matches("[A-Za-zÁÉÍÓÚáéíóúñÑ\\s]+"))
            return true;
        else{
             System.out.println("Ingrese una provincia valida");
            return false;
        }
    }
    /**
     * Valida país solo con letras y espacios.
     *
     * @param pais País a validar.
     * @return true si es válido; false en caso contrario.
     */
    public static boolean validarPais(String pais) {
        if(pais != null && pais.matches("[A-Za-zÁÉÍÓÚáéíóúñÑ\\s]+"))
            return true;
        else{
             System.out.println("Ingrese un pais valido");
            return false;
        }
    }
}