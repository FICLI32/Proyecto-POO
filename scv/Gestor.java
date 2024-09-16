package scv;

import java.security.SecureRandom;

public class Gestor {
    public static void main(String[] args ) {
        String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()_+[]{}|;:',.<>?/";
        int longitud = 10;

        String contraseña = generarContraseña(longitud, caracteres);
        System.out.println("Contraseña generada: " + contraseña);

        }
    
    public static String generarContraseña(int longitud, String caracteres) {
        SecureRandom random = new SecureRandom();
        StringBuilder contraseña = new StringBuilder(longitud);

        for (int i = 0; i < longitud; i++) {
            int indice = random.nextInt(caracteres.length());
            contraseña.append(caracteres.charAt(indice));
        }

        return contraseña.toString();
    }

}
