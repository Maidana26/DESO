package service;

import java.util.Scanner;
import models.DireccionDTO;
import models.HuespedDTO;
import dao.HuespedDAO;
        
public class GestorHuesped {
    private HuespedDAO huespedDAO;
    
    public GestorHuesped(HuespedDAO dao) {
        this.huespedDAO = dao;
    }
    
    // Interfaz funcional para referenciar los métodos del validador huesped
    @FunctionalInterface
    public interface Validador {
        boolean validar(String valor);
    }
    
    // Método genérico que pide y valida un campo
    public String solicitarCampoConDefault(Scanner sc, String etiqueta, 
            Validador validador, String valorActual) {
        String prompt = (valorActual == null || valorActual.isEmpty())
                ? etiqueta + ": "
                : String.format("%s [%s]: ", etiqueta, valorActual);

        String valor;
        boolean valido;
        do {
            System.out.print(prompt);
            valor = sc.nextLine().trim();
            // si el usuario deja vacío, se conserva el valor actual
            if (valor.isEmpty() && valorActual != null) {
                valor = valorActual;
            }
            valido = validador.validar(valor);
        } while (!valido);
        return valor;
    }
    
    // ✅ Busca un huésped por DNI y devuelve su instancia, o null si no existe
    public HuespedDTO buscarHuespedPorDni(String dniBuscado) {
        return huespedDAO.buscarHuespedPorDni(dniBuscado);
    }
    
    public void DarDeAltaHuesped(String nombre,
    String apellido,
    String tipoDeDocumento,
    String numeroDocumento,
    String posicionFrenteIVA,
    String cuit,
    String telefono,
    String fechaNacimiento,
    String email,
    String ocupacion,
    String nacionalidad,
    String calle,
    String numero,
    String departamento,
    String piso,
    String codigoPostal,
    String localidad,
    String provincia,
    String pais){
        DireccionDTO nueva_direccion = new DireccionDTO(calle, numero, departamento, piso, codigoPostal, localidad, provincia, pais);
        HuespedDTO nuevo_huesped = new HuespedDTO(nombre, apellido, tipoDeDocumento, numeroDocumento, posicionFrenteIVA, cuit, telefono, fechaNacimiento, email, ocupacion, nacionalidad, nueva_direccion);
        huespedDAO.guardarOReemplazarHuesped(nuevo_huesped);
    }
}
