package app;

import dao.FileHuespedDAO;
import service.GestorHuesped;
import ui.BajaHuespedUI;

public class main {
    public static void main(String[] args) {
        var huespedDAO = new FileHuespedDAO();
        var gestorHuesped = new GestorHuesped(huespedDAO);
        var ui = new BajaHuespedUI(gestorHuesped);
        ui.mostrarPantallaBaja();
    }
}
