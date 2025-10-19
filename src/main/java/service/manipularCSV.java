package service;

import javax.swing.JOptionPane;
import java.io.BufferedReader;
import java.io.FileReader;

public class manipularCSV {
    
    private BufferedReader lector;
    private String linea;
    private String partes [] = null;
    
    public void leerArchivo(String nombreArchivo){
        try {
            lector = new BufferedReader(new FileReader(nombreArchivo));
            while((linea = lector.readLine()) != null){
                partes = linea.split(",");
                imprimirLinea();
                System.out.println();
            }
            lector.close();
            linea = null;
            partes = null;
        } catch (Exception e){
            JOptionPane.showMessageDialog(null,e);
        }
    }
    public void imprimirLinea(){
        for(int i = 0; i < partes.length; i++){
            System.out.print(partes[i]+" | ");
        }
    }
}