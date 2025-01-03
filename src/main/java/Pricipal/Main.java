package Pricipal;

import Data.GestionArchivos;
import Modelo.GestorContrasenias;

import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class Main {
    public static void main(String[] args) {
        try {
            // Configuración de la clave de cifrado (puedes almacenarla de forma segura)
            String claveCifradoBase64 = "12345678901234567890123456789012"; // Debe ser de 32 caracteres para AES
            byte[] claveCifradoBytes = Base64.getDecoder().decode(claveCifradoBase64);
            SecretKeySpec claveCifrado = new SecretKeySpec(claveCifradoBytes, "AES");

            // Inicialización de la persistencia y el gestor
            GestionArchivos gestionArchivos = new GestionArchivos("usuarios.dat", claveCifrado);
            GestorContrasenias gestorContrasenias = new GestorContrasenias(gestionArchivos);

            // Cargar datos desde el archivo
            gestorContrasenias.cargarUsuarios();

            // Lanzar la ventana de inicio de sesión
            new GUIs.VentanaInicioSesion(gestorContrasenias).setVisible(true);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
