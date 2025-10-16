package dao;


import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import models.HuespedDTO;

public class HuespedDAO {
    private BufferedReader lector;
    private String linea;
    private String partes[] = null;
    
    public void guardar(HuespedDTO huesped){
        
    }
    public void actualizar(HuespedDTO huesped){
        
    }
    public void eliminar(HuespedDTO huesped){
        
    }
    public void buscarHuesped(String nombre, String apellido, String dni,String numero){
        File archivo = new File("data/ListaHuespedes.csv");
        
        try {
            lector = new BufferedReader(new FileReader(archivo));
            while ((linea = lector.readLine())!= null){
                partes = linea.split(",");
                
            }
            
            
        }catch(Exepcion e){
            
        }
    }
}
