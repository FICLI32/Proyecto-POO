

import java.util.Base64;
import java.util.Scanner;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class GestorContrasenias {

    public static void main(String[] args) throws Exception{
        Scanner scanner = new Scanner(System.in);
        SecretKey claveCifrado = generarClaveCifrado();
        //String contrasenia = "claveultrasecreta123";
        //String contraseniaCifrada = cifrarContrasenia(claveCifrado,contrasenia);
        //System.out.println(contraseniaCifrada);
        //System.out.println(descifrarContrasenia(claveCifrado,contraseniaCifrada));
        menu(scanner, claveCifrado);
        scanner.close();

    }

    public static SecretKey generarClaveCifrado () throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(128); // AES de 128 bits
        SecretKey secretKey = keyGen.generateKey();
        return secretKey;
    }

    public static String cifrarContrasenia (SecretKey claveCifrado, String contrasenia) throws Exception{
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, claveCifrado);
        byte[] textoCifrado = cipher.doFinal(contrasenia.getBytes());
        String textoCifradoBase64 = Base64.getEncoder().encodeToString(textoCifrado);
        return textoCifradoBase64;
    }

    public static String descifrarContrasenia (SecretKey claveCifrado, String contraseniaCifrada) throws Exception{
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, claveCifrado);
        byte[] textoDescifrado = cipher.doFinal(Base64.getDecoder().decode(contraseniaCifrada));
        return new String(textoDescifrado);
    }

    public static void menu(Scanner scanner, SecretKey claveCifrado) throws Exception {
        int opcion;
        do { 
            mostrarMenu();
            opcion = leerOpcion(scanner);
            ejecutarOpcion(opcion, scanner, claveCifrado);
        } while (opcion != 3);
    }

    public static void mostrarMenu() {
        System.out.println("╔════════════════════════════════╗");
        System.out.println("║     Bienvenido al Menu de      ║");
        System.out.println("║     Gestor de Contraseñas      ║");
        System.out.println("╠════════════════════════════════╣");
        System.out.println("║ 1. Cifrar una contraseña       ║");
        System.out.println("║ 2. Descifrar contraseña        ║");
        System.out.println("║ 3. Salir                       ║");
        System.out.println("╚════════════════════════════════╝");
        System.out.print("Seleccione una opción: ");
    }
    
    public static int leerOpcion(Scanner scanner) {
        int opcion = 0;
        while (true) { 
            if (scanner.hasNextInt()) {
                opcion = scanner.nextInt();
                if (opcion >= 1 && opcion <= 3) {
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

    public static void ejecutarOpcion(int opcion, Scanner scanner, SecretKey claveCifrado) throws Exception {
        switch (opcion) {
            case 1:
                System.out.println("Ingrese la contraseña a cifrar: ");
                String contrasenia = scanner.nextLine();
                String contraseniaCifrada = cifrarContrasenia(claveCifrado, contrasenia);
                System.out.println("Contraseña cifrada: " + contraseniaCifrada);
                break;

            case 2:
                System.out.println("Ingrese la contraseña cifrada a descifrar: ");
                String contraseniaCifradaInput = scanner.nextLine();
                String contraseniaDescifrada = descifrarContrasenia(claveCifrado, contraseniaCifradaInput);
                System.out.println("Contraseña descifrada: " + contraseniaDescifrada);
                break;

            case 3:
                System.out.println("Saliendo...");
                break;

            default:
                System.err.println("Opcion no valida. Intente nuevamente");
                break;
        }
    }
}

