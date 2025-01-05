package GUIs;

import javax.swing.*;
import java.awt.*;

public class VentanaRegistroUsuario {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> crearVentana());
    }

    public static void crearVentana() {
        // Crear el marco principal
        JFrame frame = new JFrame("Geco Security - Registro");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);

        // Crear panel principal
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(210, 250, 210)); // Fondo verde claro
        frame.add(panel);

        // Logo e imagen
        JLabel logoLabel = new JLabel("Geco Security");
        logoLabel.setFont(new Font("Arial", Font.BOLD, 24));
        logoLabel.setBounds(40, 50, 200, 30);
        panel.add(logoLabel);

        JLabel geckoImage = new JLabel(new ImageIcon("ruta_de_tu_imagen.png")); // Ruta de la imagen del gecko
        geckoImage.setBounds(50, 90, 150, 150);
        panel.add(geckoImage);

        // Crear etiquetas y campos de texto
        JLabel correoLabel = new JLabel("Correo Electrónico");
        correoLabel.setBounds(250, 30, 200, 20);
        panel.add(correoLabel);

        JTextField correoField = new JTextField();
        correoField.setBounds(250, 50, 300, 30);
        panel.add(correoField);

        JLabel nombreLabel = new JLabel("Nombre Completo");
        nombreLabel.setBounds(250, 90, 200, 20);
        panel.add(nombreLabel);

        JTextField nombreField = new JTextField();
        nombreField.setBounds(250, 110, 300, 30);
        panel.add(nombreField);

        JLabel usuarioLabel = new JLabel("Nombre de Usuario");
        usuarioLabel.setBounds(250, 150, 200, 20);
        panel.add(usuarioLabel);

        JTextField usuarioField = new JTextField();
        usuarioField.setBounds(250, 170, 300, 30);
        panel.add(usuarioField);

        JLabel contrasenaLabel = new JLabel("Contraseña");
        contrasenaLabel.setBounds(250, 210, 200, 20);
        panel.add(contrasenaLabel);

        JPasswordField contrasenaField = new JPasswordField();
        contrasenaField.setBounds(250, 230, 300, 30);
        panel.add(contrasenaField);

        // Botón de registro
        JButton registrarButton = new JButton("Registrarte");
        registrarButton.setBounds(350, 280, 120, 30);
        registrarButton.setBackground(new Color(50, 150, 255)); // Azul
        registrarButton.setForeground(Color.WHITE);
        panel.add(registrarButton);

        // Hacer visible la ventana
        frame.setVisible(true);
    }
}

