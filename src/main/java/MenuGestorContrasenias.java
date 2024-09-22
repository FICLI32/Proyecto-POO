import java.util.Scanner;

import javax.crypto.SecretKey;

public class MenuGestorContrasenias {
    public static void main(String[] args) throws Exception {
        //genera la clave de cifrado
        SecretKey claveCifrado = GestorContrasenias.generarClaveCifrado();
        //Y crear el escaner para leer entradas
        Scanner scanner = new Scanner(System.in);
        menu(scanner);
        scanner.close();
    }

    public static void menu(Scanner scanner) {
        int opcion;
        do {
            mostrarMenu();
            opcion = leerOpcion(scanner);
            ejecutarOpcion(opcion, scanner);
        }while(opcion != 3);

    }

    public static void mostrarMenu() {
        System.out.println("╔════════════════════════════════╗");
        System.out.println("║     Bienvenido al Menu de      ║");
        System.out.println("║     Gestor de Contraseñas      ║"    );
        System.out.println("╠════════════════════════════════╣");
        System.out.println("║ 1. Cifrar una contraseña       ║");
        System.out.println("║ 2. Descifrar contraseña        ║");
        System.out.println("║ 3. Salir                       ║");
        System.out.println("╚════════════════════════════════╝"); 
        System.out.print("Selecione una opcion: ");   
    }

    public static int leerOpcion(Scanner scanner) {
        int opcion = 0;
        while (true) { 
            if(scanner.hasNextInt()) {
                opcion = scanner.nextInt();
                if (opcion >= 1 && opcion <=7) {
                    break;
                } else {
                    System.out.println("Opcion invalida, ingrese nuevamente");
                    scanner.next();
                }
            } else {
                System.out.println("Entrada no valida, ingrese nuevamente");
                scanner.next();
            }
        } 
        scanner.nextLine();
        return opcion;
    }

    public static void ejecutarOpcion(int opcion, Scanner scanner) {
        switch (opcion) { //hacer para cada caso
            case 1:

        }
    }
}