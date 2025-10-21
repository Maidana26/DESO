package app;

import dao.ArchivoConserjeDAO;
import service.GestorAutenticacion;
import ui.LoginUI;
import java.nio.file.Paths;

public class HotelApp {
    public static void main(String[] args) {
        var path = Paths.get("data/conserjes.csv"); // ruta relativa
        var dao = new ArchivoConserjeDAO(path);
        var gestor = new GestorAutenticacion(dao);
        var ui = new LoginUI(gestor);

        ui.mostrarPantallaLogin();
    }
}
