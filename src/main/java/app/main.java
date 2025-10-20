package app;

import java.util.Scanner;
import dao.HuespedDAO;

public class main {

    public static void main(String[] args) {
        HuespedDAO dao = new HuespedDAO();
        Scanner sc = new Scanner(System.in);
        boolean salir = false;

        System.out.println("==========================================");
        System.out.println("   🏨 Bienvenido al Sistema de Hostelería  ");
        System.out.println("==========================================");

        while (!salir) {
            System.out.println("\nSeleccione una opción:");
            System.out.println("1. Autenticar usuario (CU01)");
            System.out.println("2. Buscar huésped (CU02)");
            System.out.println("3. Dar de alta huésped (CU09)");
            System.out.println("4. Modificar huésped (CU10)");
            System.out.println("0. Salir");

            System.out.print("\nOpción: ");
            String opcion = sc.nextLine();

            switch (opcion) {
                case "1":
                    //autenticarUsuario(sc);
                    break;
                case "2":
                    //buscarHuesped(sc);
                    break;
                case "3":
                    dao.darDeAltaHuesped(sc);
                    break;
                case "4":
                   //modificarHuesped(sc);
                    break;
                case "5":
                    //darDeBajaHuesped(sc);
                    break;
                case "0":
                    System.out.println("\n👋 Gracias por usar el sistema. ¡Hasta pronto!");
                    salir = true;
                    break;
                default:
                    System.out.println("⚠️ Opción inválida. Intente nuevamente.");
            }
        }

        sc.close();
    }
}
