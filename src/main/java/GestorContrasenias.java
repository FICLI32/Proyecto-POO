

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
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
        Map<String, String> contrasenias = new HashMap<>(); //mapa para almacenar contraseñas cifradas
        menu(scanner, claveCifrado, contrasenias);
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

    public static void menu(Scanner scanner, SecretKey claveCifrado, Map<String, String> contrasenias) throws Exception {
        int opcion;
        do { 
            mostrarMenu();
            opcion = leerOpcion(scanner);
            ejecutarOpcion(opcion, scanner, claveCifrado, contrasenias);
        } while (opcion != 5);
    }

    public static void mostrarMenu() {
        System.out.println("╔══════════════════════════════════╗");
        System.out.println("║      Bienvenido al Menu de       ║");
        System.out.println("║      Gestor de Contraseñas       ║");
        System.out.println("╠══════════════════════════════════╣");
        System.out.println("║ 1. Crear una contraseña          ║");
        System.out.println("║ 2. Eliminar una contraseña       ║");
        System.out.println("║ 3. Buscar una contraseña         ║");
        System.out.println("║ 4. Listar todas las contraseñas  ║");
        System.out.println("║ 5. Salir                         ║");
        System.out.println("╚══════════════════════════════════╝");
        System.out.print("Seleccione una opción: ");
    }
    
    public static int leerOpcion(Scanner scanner) {
        int opcion = 0;
        while (true) { 
            if (scanner.hasNextInt()) {
                opcion = scanner.nextInt();
                if (opcion >= 1 && opcion <= 5) {
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

    public static void ejecutarOpcion(int opcion, Scanner scanner, SecretKey claveCifrado, Map<String, String> contrasenias) throws Exception {
        switch (opcion) {
            case 1:
                System.out.println("Ingrese una etiqueta para la contraseña: ");
                String crearEtiqueta = scanner.nextLine();
                System.out.println("Ingrese la contraseña a crear: ");
                String crearContrasenia = scanner.nextLine();
                String contraseniaCifrada = cifrarContrasenia(claveCifrado,crearContrasenia);
                contrasenias.put(crearEtiqueta, contraseniaCifrada);
                System.out.println("Contraseña creada y cifrada");
                break;

            case 2:
                System.out.println("Ingrese la etiqueta de la constraseña a eliminar: ");
                String eliminarEtiqueta = scanner.nextLine();
                if (contrasenias.containsKey(eliminarEtiqueta)) {
                    contrasenias.remove(eliminarEtiqueta);
                    System.out.println("Contraseña eliminada");
                } else {
                    System.out.println("Etiqueta no encontrada");
                }
                break;

            case 3:
                System.out.println("Ingrese la etiqueta de la contraseña a buscar: ");
                String mostrarEtiqueta = scanner.nextLine();
                if (contrasenias.containsKey(mostrarEtiqueta)) {
                    String contraseniaCifradaMostrar = contrasenias.get(mostrarEtiqueta);
                    String contraseniaDescifrada = descifrarContrasenia(claveCifrado, contraseniaCifradaMostrar);
                    System.out.println("Contraseña descifrada: " + contraseniaDescifrada);
                } else {
                    System.out.println("Etiqueta no encontrada");
                }
                break;

            case 4:
                System.out.println("Lista de contraseñas: ");
                for (String etiqueta : contrasenias.keySet()) {
                    System.out.println("Etiqueta: " + etiqueta);
                }  
                break;

            case 5:
                System.out.println("Saliendo...");
                break;

            default:
                System.err.println("Opcion no valida. Intente nuevamente");
                break;
        }
    }
}

