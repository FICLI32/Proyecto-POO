import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.SecureRandom;

public class GestorContrasenias {

    public static void main(String[] args) throws Exception{
        Scanner scanner = new Scanner(System.in);
        SecretKey claveCifrado = generarClaveCifrado();
        Map<String, String> contrasenias = new HashMap<>();
        menu(scanner, claveCifrado, contrasenias);
        scanner.close();

    }

    public static SecretKey generarClaveCifrado() throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(128);
        SecretKey secretKey = keyGen.generateKey();
        return secretKey;
    }

    public static String cifrarContrasenia(SecretKey claveCifrado, String contrasenia) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, claveCifrado);
        byte[] textoCifrado = cipher.doFinal(contrasenia.getBytes());
        String textoCifradoBase64 = Base64.getEncoder().encodeToString(textoCifrado);
        return textoCifradoBase64;
    }

    public static String descifrarContrasenia(SecretKey claveCifrado, String contraseniaCifrada) throws Exception {
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
        System.out.println("╔═════════════════════════════════════╗");
        System.out.println("║         Bienvenido al Menu de       ║");
        System.out.println("║         Gestor de Contraseñas       ║");
        System.out.println("╠═════════════════════════════════════╣");
        System.out.println("║ 1. Crear una o modificar contraseña ║");
        System.out.println("║ 2. Eliminar una contraseña          ║");
        System.out.println("║ 3. Mostrar una contraseña           ║");
        System.out.println("║ 4. Listar todas las contraseñas     ║");
        System.out.println("║ 5. Salir                            ║");
        System.out.println("╚═════════════════════════════════════╝");
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
                System.out.println("¿Desea generar una contraseña segura automaticamente? (S/N)");
                String generarAutomaticamnete = scanner.nextLine();
                String crearContrasenia = eleccionGenerarContrasenias(generarAutomaticamnete,scanner);
                aniadirContrasenia(crearEtiqueta, crearContrasenia, claveCifrado, contrasenias);
                break;

            case 2:
                System.out.println("Ingrese la etiqueta de la constraseña a eliminar: ");
                String eliminarEtiqueta = scanner.nextLine();
                eliminarContrasenia(eliminarEtiqueta, contrasenias);
                break;

            case 3:
                System.out.println("Ingrese la etiqueta de la contraseña a buscar: ");
                String mostrarEtiqueta = scanner.nextLine();
                mostrarContrasenia(mostrarEtiqueta,claveCifrado,contrasenias);
                break;

            case 4:
                listarContrasenias(contrasenias);
                break;

            case 5:
                System.out.println("Saliendo...");
                break;

            default:
                System.err.println("Opcion no valida. Intente nuevamente");
                break;
        }
    }

    public static String generarContrasenia (int longitud) {
        String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()_+[]{}|;:',.<>?/";
        SecureRandom random = new SecureRandom();
        StringBuilder contrasenia = new StringBuilder(longitud);

        for (int i = 0; i < longitud; i++) {
            int indice = random.nextInt(caracteres.length());
            contrasenia.append(caracteres.charAt(indice));
        }

        return contrasenia.toString();
    }

    public static void aniadirContrasenia(String nombreEtiqueta, String crearContrasenia, SecretKey claveCifrado, Map<String, String> contrasenias) throws Exception {
        String contraseniaCifrada = cifrarContrasenia(claveCifrado, crearContrasenia);
        contrasenias.put(nombreEtiqueta, contraseniaCifrada);
        System.out.println("Contraseña creada y cifrada. ");
    }

    public static void eliminarContrasenia(String etiquetaContrasenia, Map<String, String> contrasenias) {
        if (contrasenias.containsKey(etiquetaContrasenia)) {
            contrasenias.remove(etiquetaContrasenia);
            System.out.println("Contraseña eliminada. ");
        } else {
            System.out.println("Etiqueta no encontrada. ");
        }
    }

    public static void mostrarContrasenia(String etiquetaContrasenia, SecretKey claveCifrado, Map<String, String> contrasenias) throws Exception {
        if (contrasenias.containsKey(etiquetaContrasenia)) {
            String contraseniaCifradaMostrar = contrasenias.get(etiquetaContrasenia);
            String contraseniaDescifrada = descifrarContrasenia(claveCifrado, contraseniaCifradaMostrar);
            System.out.println("Contraseña descifrada: " + contraseniaDescifrada);
        } else {
            System.out.println("Etiqueta no encontrada. ");
        }
    }

    public static void listarContrasenias(Map<String, String> contrasenias) {
        if (contrasenias.isEmpty()) {
            System.out.println("No hay contraseñas.");
        }else {
            System.out.println("Lista de contraseñas:");
            for (Map.Entry<String,String> entry : contrasenias.entrySet()){
                String etiqueta = entry.getKey();
                String contrasenia = entry.getValue();
                System.out.println("Etiqueta: "+etiqueta+"|"+"Contrasenia: " + contrasenia);
            }
        }
    }

    public static String eleccionGenerarContrasenias (String autoGenerar, Scanner scanner) {
        String crearContrasenia;

        if (autoGenerar.equalsIgnoreCase("S")) {
            int longitud;
            do{
                System.out.println("Ingrese la longitud de la contrasenia (minimo 6 caracteres)");
                longitud = scanner.nextInt();
                scanner.nextLine();
                if (longitud < 6) {
                    System.out.println("ingrese mas de 6 caracteres de longitud");
                }
            }while (longitud < 6);

            crearContrasenia = generarContrasenia(longitud);
            System.out.println("Contraseña generada: " + crearContrasenia);
            return crearContrasenia;

        } else {
            System.out.println("Ingrese la contraseña a crear: ");
            crearContrasenia = scanner.nextLine();
            return crearContrasenia;
        }
    }

}

