package Principal;

import GUIs.VentanaClaveMaestra;

import javax.swing.*;
import java.io.File;

public class Main {
    public static void main(String[] args) {
        try {
            String rutaArchivo = "usuarios.json";
            File archivoUsuarios = new File(rutaArchivo);

            SwingUtilities.invokeLater(() -> {
                new VentanaClaveMaestra(archivoUsuarios).setVisible(true);
            });

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(
                    null,
                    "Error al inicializar el sistema: " + e.getMessage(),
                    "Error Crítico",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}





