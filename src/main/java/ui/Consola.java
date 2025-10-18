package ui;

import dao.HuespedDAO;

public class Consola {
    public static void main(String[] args) {
        HuespedDAO dao = new HuespedDAO();

        dao.buscarHuesped("Juan", "Perez", "", "");
        dao.buscarHuesped("", "", "DNI", "");
        dao.buscarHuesped("Juan", "", "", "");
    }
}
